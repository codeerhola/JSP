package kr.or.ddit.mvc.annotation;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.mvc.utils.ReflectionUtils;
import lombok.extern.log4j.Log4j2;


/*수집된 정보를 기반으로 현재 검색

리케스트 매핑 핸들러 매핑 의 역할
1. 핸들러 역할 수집해서 맵을 만듦
2. 맵에 있는 수집된 정보를 기반으로 현재 요청할수 있는~를 검색해주는 역할 */

@Log4j2
public class RequestMappingHandlerMapping implements HandlerMapping {

	private Map<RequestMappingCondition, RequestMappingInfo> handlerMap;

	// 생성자 안에서 최종적으로 만들어야 하는것이 Map<RequestMappingCondition, RequestMappingInfo>임
	// 트래이싱 코드 들어감
	public RequestMappingHandlerMapping(String... basePackages) {
		handlerMap = new LinkedHashMap<>();
		scanBasePackages(basePackages);

	}

	// 여기서 리플렉션
	private void scanBasePackages(String[] basePackages) {
	      ReflectionUtils.getClassesWithAnnotationAtBasePackages(Controller.class, basePackages).
	         forEach((handlerClass, controller)->{
	            try {               
	               Object commandHandler = handlerClass.newInstance();
	               
	               //
	               ReflectionUtils.getMethodsWithAnnotationAtClass(
	                     handlerClass, RequestMapping.class, String.class
	                  ).forEach((handlerMethod, requestMapping)->{
	                     // key
	                     RequestMappingCondition mappingCondition = 
	                           new RequestMappingCondition(requestMapping.value(), requestMapping.method());
	                     // value
	                     RequestMappingInfo mappingInfo = 
	                           new RequestMappingInfo(mappingCondition, commandHandler, handlerMethod);
	                     handlerMap.put(mappingCondition, mappingInfo);
	                     log.info("수집된 핸들러 정보 : {}", mappingInfo);
	                  });
	            }catch (Exception e) {
	               log.error("핸들러 클래스 스캔 중 문제 발생", e);
	            }
	         });      
	   }



	@Override
	public RequestMappingInfo findCommandHandler(HttpServletRequest request) {

		// request RequestMappingCondition -> 객체화 시킴
		// url과 method로 로 객체를 생성!! 이 모든 정보는 request가 갖고 있어 이걸 꺼내서 url,method 세팅

		String url = request.getServletPath();
		RequestMethod method = RequestMethod.valueOf(request.getMethod().toUpperCase()); // enum상수로 받아와

		RequestMappingCondition mappingCondition = new RequestMappingCondition(url, method);
		return handlerMap.get(mappingCondition);// 리턴값이 있을수도 있고 없을 수도 있어
		// 404표현 불가 response 없어
	}

}























