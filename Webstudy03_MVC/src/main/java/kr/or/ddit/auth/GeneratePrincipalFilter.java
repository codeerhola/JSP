package kr.or.ddit.auth;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.MemberVOWrapper;

// Principal필터객체를 만들어줌
//1. Principal 객체를 만듦. 언제 ? 세션에 authMember 가 있을때 (현재 사용자가 인증된 사용자 일때) 
public class GeneratePrincipalFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		
		//Principal 객체를 만들고 request 상태를 바꿔줌 인증될 경우 (요청은 변경 될 수 없어.랩퍼를 이용해 상태를 바꿔줌 ) 
		
		if(authMember!=null) {
			//request에 넣어줌 그래야 전부 영향받아 request는 setter없음
			HttpServletRequest modifiedReq = new HttpServletRequestWrapper(req) { //원본요청을 modifiedReq 에 넣어줌 
				
				@Override
				public Principal getUserPrincipal() {
					
					HttpServletRequest adaptee = (HttpServletRequest)getRequest();
					
					MemberVO realMember = (MemberVO) adaptee.getSession().getAttribute("authMember");
					
					return new MemberVOWrapper(realMember);  
				}
			};
			chain.doFilter(modifiedReq, response); //request가  modifiedReq로 변경됨
		}else {
			chain.doFilter(request, response); //아무것도 안함 
		}
		}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
}

	
	