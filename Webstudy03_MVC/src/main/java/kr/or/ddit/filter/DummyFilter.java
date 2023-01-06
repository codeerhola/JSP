package kr.or.ddit.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import lombok.extern.slf4j.Slf4j;


/*컨테이너에 의해 관리가 된다.*/

@Slf4j
public class DummyFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("{]초기화",this.getClass().getName());
		
	}
	//왜 역순으로 가? 메소드의 복귀주소는 스택에 쌓임 선입호출이기때문에 
	//그래서 요청과 응답의 필터링 순서가 역순임 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//요청 필터링
		log.info("==============요청 필터링 ==============");
		
		chain.doFilter(request, response);//------------------경계선 다음필터에세 제어권이 이동

		//응답 필터링 
		log.info("==============응답 필터링 ==============");
	}

	@Override
	public void destroy() {
		log.info("{]초기화",this.getClass().getName());
		
	}

}
