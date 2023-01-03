package kr.or.ddit.mvc.annotation;

import java.io.IOException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.annotation.resolvers.HandlerMethodArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttributeMethodProcessor;
import kr.or.ddit.mvc.annotation.resolvers.RequestParamMethodArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.RequestParamMethodArgumentResolver.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import kr.or.ddit.mvc.annotation.resolvers.ServletRequestMethodArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.ServletResponseMethodArgumentResolver;


//제일 중요한 역할 
@Slf4j
public class RequestMappingHandlerAdapter implements HandlerAdapter {
	
	private List<HandlerMethodArgumentResolver> argumentResolvers;
	//생성자 역할을 하는 코드블럭
	{
		argumentResolvers = new ArrayList<>(); // has many 관계 형성 
		
		argumentResolvers.add(new ServletRequestMethodArgumentResolver());
		argumentResolvers.add(new ServletResponseMethodArgumentResolver());
		argumentResolvers.add(new RequestParamMethodArgumentResolver());
		argumentResolvers.add(new ModelAttributeMethodProcessor());
		
	}
	//갖고있는 Resolvers가 여러개 일 경우 한개를 선택하는 작업 
	private HandlerMethodArgumentResolver findArgumentResolver(Parameter param) {
		HandlerMethodArgumentResolver finded = null;
		for(HandlerMethodArgumentResolver resolver :argumentResolvers) {
			if(resolver.supportsParameter(param)) { //트루이면 현재파라미터는 내가 처리 가능
				 finded = resolver;
				 break;
			}
		}
		return finded;
	}

	
   @Override
   public String invokeHandler(RequestMappingInfo mappingInfo, HttpServletRequest req, HttpServletResponse resp)
         throws ServletException, IOException {
	   
      Object handlerObject = mappingInfo.getCommandHandler();
      Method handlerMethod = mappingInfo.getHandlerMethod();
      
    //argument 개수찾기 
      int parameterCount = handlerMethod.getParameterCount();
      
      try {
         String viewName = null;
         if(parameterCount>0) {
        	 //몇개파라미터 어떤타입으로 넣어줘?
        	 Parameter[] parameters = handlerMethod.getParameters();
        	 Object[] arguments = new Object[parameterCount];
        	 //한개한개의 파라미터를 만듦 argumentResolvers 구현체를 사용
        	 for(int i=0; i<parameterCount; i++) {
        		 Parameter param = parameters[i];
        		 
        		 HandlerMethodArgumentResolver findedResolver = findArgumentResolver(param);
        		 if(findedResolver==null) {
        			 throw new RuntimeException(String.format("%s 타입의 메소드 인자는 현재 처리 가능한 resolver 가 없음",param.getType()));
        			 
        		 }else {
        			 arguments[i] = findedResolver.resolveArgument(param, req, resp); //필요한 인자를 가지고 있는arguments 
        		 }
        	 }
        	 viewName = (String) handlerMethod.invoke(handlerObject, arguments);
         }else {
        	  viewName =  (String) handlerMethod.invoke(handlerObject); //
         }
         return viewName;         
      }catch(BadRequestException e) {
    	  log.error("핸들러 어탭터가 handler method argument resolver 사용 중 문제 발생",e);
    	  resp.sendError(400, e.getMessage());
    	  return null; //뷰네임을 널로 반환시켜도 됨 
      }catch(Exception e) {
         throw new ServletException(e);
      }
   }

}

