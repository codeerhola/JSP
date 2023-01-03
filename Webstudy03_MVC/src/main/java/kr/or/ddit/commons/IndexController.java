package kr.or.ddit.commons;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;


@Controller
public class IndexController {
	
	@RequestMapping("/index.do")
	public String index(HttpServletRequest req) {
		req.setAttribute("contentPage","/WEB-INF/views/index.jsp" ); //특정 jsp 페이지 
		return "/layout";
		
	}
}
 