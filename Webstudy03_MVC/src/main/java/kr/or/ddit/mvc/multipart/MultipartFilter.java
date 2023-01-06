package kr.or.ddit.mvc.multipart;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 일반요청에는 할 일이 없더 
 * multipart request 를 request wrapper(MultipartHttpServletRequest)로 변경하는 필터 
 *
 */
public class MultipartFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//헤더에 대한 정보가 필요
		HttpServletRequest req = (HttpServletRequest) request;
		
		String contentType = req.getContentType();
		
		if(contentType!=null && contentType.startsWith("multipart/form-data")) {
			//wrapper로 변경  
			HttpServletRequest modifiedReq =   new MultipartHttpServletRequest(req); //어댑터 하나 만듦
			chain.doFilter(modifiedReq, response);
		}else {//원본을 그대로 넘김 
			chain.doFilter(request, response); 
		}
		
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}

 

