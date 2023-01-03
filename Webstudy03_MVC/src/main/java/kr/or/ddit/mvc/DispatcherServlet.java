package kr.or.ddit.mvc;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.commons.IndexController;
import kr.or.ddit.login.controller.LoginProcessController;
import kr.or.ddit.login.controller.LogoutController;
import kr.or.ddit.member.controller.MemberInsertController;
import kr.or.ddit.member.controller.MemberListController;
import kr.or.ddit.member.controller.MemberViewController;
import kr.or.ddit.mvc.annotation.HandlerAdapter;
import kr.or.ddit.mvc.annotation.HandlerMapping;
import kr.or.ddit.mvc.annotation.RequestMappingHandlerAdapter;
import kr.or.ddit.mvc.annotation.RequestMappingHandlerMapping;
import kr.or.ddit.mvc.annotation.RequestMappingInfo;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.mvc.view.ViewResolver;
import kr.or.ddit.prod.controller.ProdInsertController;
import kr.or.ddit.prod.controller.ProdListController;


//
public class DispatcherServlet extends HttpServlet{
	
	//실제 작업은 얘네가 함 
	private ViewResolver viewResolver;
	private HandlerMapping handlerMapping;
	private HandlerAdapter handlerAdapter;
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		viewResolver = new InternalResourceViewResolver("/WEB-INF/views/", ".jsp");
		handlerMapping = new RequestMappingHandlerMapping("kr.or.ddit");
		handlerAdapter = new RequestMappingHandlerAdapter();
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String requestURI = req.getServletPath();
		//아래 코드를 핸들러매핑의 핸들러 맵 1:1 관계임  핸들러매핑의 구현체로 넘김 
		//mappingInfo는 모든 정보를 갖고 있어.
		RequestMappingInfo mappingInfo = handlerMapping.findCommandHandler(req);//
		
		
		
		if(mappingInfo==null) {
			resp.sendError(404, requestURI+" 는 처리할 수 없는 자원임(Not found).");
			return;
		}
		
		//String viewName = controller.process(req, resp);
		String viewName = handlerAdapter.invokeHandler(mappingInfo, req, resp);
		
		if(viewName==null) {
			if(!resp.isCommitted())
				resp.sendError(500, "논리적인 뷰 네임은 null일 수 없음.");
		}else {
			viewResolver.resolveView(viewName, req, resp);
		}	
			
		
	}
}
