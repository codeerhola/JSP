package kr.or.ddit.login.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;

@Controller
public class LogoutController {
	
	@RequestMapping(value="/login/logout.do", method=RequestMethod.POST)
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/";
	}
		 
}
