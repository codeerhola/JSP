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
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.vo.MemberVO;

//조회 한 건 출력하기 
@Controller
public class MemberViewController {
	
	//요청발생 구조
	//컨트롤러 서비스 연결
	private MemberService service = new MemberServiceImpl();

	@RequestMapping("/member/memberView.do")
//	
	public String memberView(
			
			
			@RequestParam(value="who",required=true) String memId
			,HttpServletRequest req
			
			) {
		
//		String memId = req.getParameter("who");
//		
//		if(StringUtils.isBlank(memId)) {
//			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//			return null;
//		}
//	
		MemberVO member = service.retrieveMember(memId);
		
		req.setAttribute("member", member);
		
		
		String viewName = "member/memberView";
		return viewName;
		

	}
			

}
