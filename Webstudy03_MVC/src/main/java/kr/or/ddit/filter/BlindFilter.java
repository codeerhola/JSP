package kr.or.ddit.filter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlindFilter implements Filter{
	
	private Map<String, String> blindMap;
	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		log.info("{}초기화",this.getClass().getName());
		blindMap = new LinkedHashMap<>();
		
		blindMap.put("127.0.0.1", "나니까 블라인드");
		blindMap.put("0:0:0:0:0:0:0:1", "나니까 블라인드");
		blindMap.put("192.168.35.18", "나니까 블라인드");
		blindMap.put("192.168.35.37", "인수 블라인드");
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("blind filter 동작시작");
		
		String ipAddress = request.getRemoteAddr();
		
		if(blindMap.containsKey(ipAddress)) {
			String reason = blindMap.get(ipAddress);
			String message = String.format("당신은 %s 사유로 블라인드 되었음", reason);

			request.setAttribute("message", message);
			
			String viewName = "/WEB-INF/views/commons/messageView.jsp";
			request.getRequestDispatcher(viewName).forward(request, response);
			
		}else { //블라인드 대상자가 아닐때 
			chain.doFilter(request, response); 
			
			
		}
	log.info("blind filter 동작종료");
	}

	@Override
	public void destroy() {
		log.info("{}소멸",this.getClass().getName());
		
	}
}



