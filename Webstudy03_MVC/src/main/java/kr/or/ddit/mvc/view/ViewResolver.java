package kr.or.ddit.mvc.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ViewResolver {
	
	public void resolveView(String veiwName, HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException;
	

}
