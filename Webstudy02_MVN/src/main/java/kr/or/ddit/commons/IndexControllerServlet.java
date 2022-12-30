package kr.or.ddit.commons;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//인덱스 제일 처음 시작하는 서블릿. 뷰 이동
@WebServlet("/index.do")
public class IndexControllerServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("contentPage","/WEB-INF/views/index.jsp" ); //특정 jsp 페이지 
		
		String viewName = "/WEB-INF/views/layout.jsp";
		req.getRequestDispatcher(viewName).forward(req, resp);
	}
}
 