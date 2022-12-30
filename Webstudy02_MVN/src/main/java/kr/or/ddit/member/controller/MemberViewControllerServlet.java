package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.vo.MemberVO;

//조회 한 건 출력하기 

@WebServlet("/member/memberView.do")
public class MemberViewControllerServlet extends HttpServlet{
	
	//요청발생 구조
	//컨트롤러 서비스 연결
	private MemberService service = new MemberServiceImpl();

	@Override

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1.
		String memId = req.getParameter("who");
		if(StringUtils.isBlank(memId)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
	
		//2. 모델확보
		MemberVO member = service.retrieveMember(memId);
		
		//3.
		req.setAttribute("member", member);
		
		
		//4. 뷰 이동 
		String viewName = "member/memberView";
		
		//5.
		new InternalResourceViewResolver("/WEB-INF/views/",".jsp").resolveView(viewName, req, resp);

	}
			

}
