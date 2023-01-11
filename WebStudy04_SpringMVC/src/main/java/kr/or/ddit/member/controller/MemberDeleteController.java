package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.vo.MemberVO;

@Controller
public class MemberDeleteController{
	@Inject
	private MemberService service;
	
	@RequestMapping(value="/member/memberDelete.do", method=RequestMethod.POST)
	public String memberDelete(
		@RequestParam(value="memPass", required=true) String memPass
		, @SessionAttribute(value="authMember", required=true) MemberVO authMember
		, HttpSession session
		, RedirectAttributes redirectAttributes
	) throws ServletException, IOException {
//		1.
		String memId = authMember.getMemId();
		
		MemberVO inputData = new MemberVO();
		inputData.setMemId(memId);
		inputData.setMemPass(memPass);
		
		String viewName = null;
		
		ServiceResult result = service.removeMember(inputData);
		switch (result) {
		case INVALIDPASSWORD:
			
			
		 //RedirectAttributes redirectAttributes 선언,  
		 //이거 대체session.setAttribute("message", "비번 오류");
			redirectAttributes.addFlashAttribute("message", "비번 오류");
			viewName = "redirect:/mypage.do";
			break;
		case FAIL:
			redirectAttributes.addFlashAttribute("message", "서버 오류");
			viewName = "redirect:/mypage.do";
			break;

		default:
			session.invalidate(); //이거 때문에 세션 선언 필요 
			viewName = "redirect:/";
			break;
		}
		
		return viewName;
	}
}

































