package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

/**
 *  Backend controller(command handler) --> Plain Old Java Object
 */
@Slf4j
@Controller
@RequestMapping("/member/memberInsert.do")
public class MemberInsertController{
	
	@Inject
	private MemberService service;
	
	

	//이메서드에서 반환값을 만들어지는 객체를 req에 넣어줘 ! 
	//핸들러어탭터는 핸들러 메서드 호출하기전에 
	//1번 메서드 modelAttri 호출함 membervo가 만들어지고 
	//차례대로 @ModelAttribute 가 붙어있는곳에 값을  차례대로 넣어줌
	//1번 무조건 member라는 속성데이터를 가지고 다님 -> 객체재활용 가능  
	
	@ModelAttribute("member")
	public MemberVO member() {
		log.info("@ModelAttribute 메소드 실행 -> member 속성 생성");
		return new MemberVO();
	}
	
	@GetMapping
	public String memberForm(){
		return "member/memberForm";
	}
	
	@PostMapping
	public String memberInsert(
		HttpServletRequest req 
		, @Validated(InsertGroup.class) @ModelAttribute("member") MemberVO member	
		, Errors errors //객체임, 검증의 결과를 가지고 있어 
	)throws ServletException, IOException {
		
		
		//여기에서 검증해야해  스프링제공 인증 
		//1. h/v 2.h/a에게 검증 해서 넣어줘 명령을 내림 (@형태로->@Validated)
		//@Validated(InsertGroup.class) 
		
		boolean valid = !errors.hasErrors();
		
		String viewName = null;
		
		if(valid) {
			ServiceResult result = service.createMember(member);
			switch (result) {
			case PKDUPLICATED:
				req.setAttribute("message", "아이디 중복");
				viewName = "member/memberForm";
				break;
			case FAIL:
				req.setAttribute("message", "서버에 문제 있음. 쫌따 다시 하셈.");
				viewName = "member/memberForm";
				break;

			default:
				viewName = "redirect:/";
				break;
			}
		}else {
			viewName = "member/memberForm";
		}
		return viewName;
	}
}

