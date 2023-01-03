package kr.or.ddit.mvc.annotation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



//invoker임. 컨트롤러의 정보가 없어 리시버와 직접적인 연결이 없어. 
//인보커와 리시버 사이의 결합력을 리플렉션을 사용하고 있어 
public interface HandlerAdapter {
	
	/**
	 * mappingInfo에 포함된 객체와 핸들러 메소드 정보를 기반으로 실제 핸들러를 호출하는 역할. 
	 * @param mappingInfo
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String invokeHandler(
			RequestMappingInfo mappingInfo, 
			HttpServletRequest req, 
			HttpServletResponse resp
		) throws ServletException, IOException;

}
