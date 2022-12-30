package kr.or.ddit.member.controller;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Path;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberInsert.do")
public class MemberInsertControllerServlet extends HttpServlet{
	
	
	private MemberService service = new MemberServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { //양식제공
		
		String viewName = "member/memberForm";
		new InternalResourceViewResolver("/WEB-INF/views/",".jsp").resolveView(viewName, req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { //폼을 보내
		
		req.setCharacterEncoding("UTF-8");
		
		//command object - 검증대상 
		MemberVO member = new MemberVO();
		req.setAttribute("member", member);
		//member.setMemId(req.getParameter("memId"));
		
		Map<String, String[]> parameterMap = req.getParameterMap();
		
		try {
			BeanUtils.populate(member, parameterMap);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new ServletException(e);
		} 
		
		String viewName = null;
		
		//여기에서 검증해야해 
		
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = ValidationUtils.validate(member, errors, InsertGroup.class);
		
		if(valid){
			ServiceResult result = service.createMember(member);
		
			switch (result) {
				case PKDUPLICATED:
					req.setAttribute("message", "아이디중복");
					viewName = "member/memberForm";
					break;
					
				case FAIL:
					req.setAttribute("message", "서버 문제 있음. 이따 다시하셈");
					viewName = "member/memberForm";
					break;
				default:
					viewName = "redirect:/";
					break;
			}
		}else {
			viewName = "member/memberForm";
		}
		new InternalResourceViewResolver("/WEB-INF/views/",".jsp").resolveView(viewName, req, resp);
		
	}
}
