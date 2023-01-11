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

public class GeneratePrincipalFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);
		MemberVO authMember = null;
		if(session!=null) {
			authMember = (MemberVO) session.getAttribute("authMember");
		}
		if(authMember!=null) {
			HttpServletRequest modifiedReq = new HttpServletRequestWrapper(req) {
				@Override
				public Principal getUserPrincipal() {
					HttpServletRequest adaptee = (HttpServletRequest) getRequest();
					HttpSession session = adaptee.getSession(false);
					if(session!=null) {
						MemberVO realMember = (MemberVO) session.getAttribute("authMember");
						return new MemberVOWrapper(realMember);
					}else {
						return super.getUserPrincipal();
					}
				}
			};
			chain.doFilter(modifiedReq, response);
		}else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}








