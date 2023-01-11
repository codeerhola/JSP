package kr.or.ddit.login.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController{
	@PostMapping("/login/logout.do")
	public String process(HttpSession session){
//		session.removeAttribute("authMember");
		session.invalidate();
		
		return "redirect:/";
	}
}
