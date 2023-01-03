package kr.or.ddit.mvc.annotation;

import javax.servlet.http.HttpServletRequest;

//리턴타입
public interface HandlerMapping {
	/**
	 * 현재 요청의 조건(RequestMappingCondition)에 맞는 핸들러 정보(핸들러 객체 + 핸들러 메소드 :두개를 다 갖고 있는requestMappinginfo)를 검색.
	 * 실제로 이 객체화 시켜야햄  
	 * @param request
	 * @return
	 */
//동작하려면 RequestMappingCondition 이  식별자 인포가 식별자의 값이 되야해 그래서 맵으 구조가 어디 있어야해 
	public RequestMappingInfo findCommandHandler(HttpServletRequest request);
}
