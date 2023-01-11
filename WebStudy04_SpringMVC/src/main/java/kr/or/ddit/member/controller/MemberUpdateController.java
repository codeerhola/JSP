package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.ProdVO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member/memberUpdate.do")
public class MemberUpdateController{
	private final MemberService service;
	
	@GetMapping
	public String updateForm(
		@SessionAttribute("authMember") MemberVO authMember
		, Model model
	) {
		
		MemberVO member = service.retrieveMember(authMember.getMemId());
		
		model.addAttribute("member", member);
		
		return "member/memberForm";
	}
	
	
	@PostMapping
	public String updateProcess(
		@Validated(UpdateGroup.class) @ModelAttribute("member") MemberVO member
		, BindingResult errors
		, @Valid ProdVO prod
		, Model model
		, HttpSession session  //세션에서 꺼내는건 세션이 필요없지만,  값을 넣는건 세션이 필요해 지우지마
	) throws IOException{
		String viewName = null;
		
		
		if(!errors.hasErrors()) {
			ServiceResult result = service.modifyMember(member);
			switch (result) {
			case INVALIDPASSWORD:
				model.addAttribute("message", "비밀번호 오류");
				viewName = "member/memberForm";
				break;
			case FAIL:
				model.addAttribute("message", "서버 오류, 쫌따 다시.");
				viewName = "member/memberForm";
				break;
				
			default: //등록성공
				
				MemberVO modifiedMember = service.retrieveMember(member.getMemId());
				//세션에서 값을 넣는건 세션이 필요해 
				session.setAttribute("authMember", modifiedMember);
				
				viewName = "redirect:/mypage.do";
				break;
			}
		}else {
			viewName = "member/memberForm";
		}
		
		return viewName;
	}
}























