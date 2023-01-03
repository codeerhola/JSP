package kr.or.ddit.mvc.annotation.resolvers;

import java.io.IOException;
import java.lang.reflect.Parameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Handler Adapter 가 핸들러 메소드를 호출할때 
 * 메소드 인자 하나하나를 따로 처리하기 위한 전략 객체    
 */
public interface HandlerMethodArgumentResolver {
	/**
	 * 현재 파라미터가 resolver에 의해 처리가능한 경우인지 판단. 
	 * @param parameter
	 * @return
	 */
	public boolean supportsParameter(Parameter parameter);
	
	
	
	//true 이면 처리할것임  
	//왜 Object?  어떤경우든 파라미터 한개를 만들어냄 그리고 그게 제한이 없기때문에
	/**
	 * 핸들러 메소드의 인자 하나를 처리(생성)하기 위한 메소드 
	 * @param parameter
	 * @param request
	 * @param resp
	 * @return
	 */
	public Object resolveArgument(Parameter parameter, HttpServletRequest request, HttpServletResponse resp)
		throws ServletException, IOException;

}
