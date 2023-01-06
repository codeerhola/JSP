package kr.or.ddit.mvc.annotation.resolvers;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * {@link HttpServletRequest},  {@link HttpSession}, {@link Principal} 타입의 핸들러 메소드 인자 해결 
 * 	컨트롤러에서 필요한 정보는 리퀘스트가 갖고 있어! 그래서  대부분 req에서 꺼내짐 
 */
public class ServletRequestMethodArgumentResolver implements HandlerMethodArgumentResolver {
	//1.타입결정
	@Override
	public boolean supportsParameter(Parameter parameter) {
		Class<?> parameterType = parameter.getType();
		boolean support = HttpServletRequest.class.equals(parameterType)// 내가 처리 할 수 있음 Reqeust임  
								||
						HttpSession.class.equals(parameterType)
						||
						Principal.class.isAssignableFrom(parameterType); //parameterType가 Principal의 하위타입인디? 
		return support;
	}
	
	//2.파라미터를 여기서 만듦! 리퀘스트 지원가능? 리턴값 리퀘스트  세션지원가능? 리턴값이 세션 
	@Override
	public Object resolveArgument(Parameter parameter, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Class<?> parameterType = parameter.getType(); //타입을 알아야해
		Object argumentObject = null;
		
		if(HttpServletRequest.class.equals(parameterType)) {
			argumentObject = req;
		}else if(HttpSession.class.equals(parameterType)){ 
			argumentObject = req.getSession();
			
		}else { //principal 꺼내옴 
			argumentObject = req.getUserPrincipal();
		}
		
		return argumentObject;
	}

}
