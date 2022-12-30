/*package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberDelete.do")
public class MemberDeleteControllerServlet extends HttpServlet{

	//1.서비스와 의존관계 -> 결합력 발생 
	private MemberService service = new MemberServiceImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//정보가져오기
		HttpSession session = req.getSession();
		MemberVO authMember = (MemberVO)session.getAttribute("authMember");
		
		//반드시 있어야해 비교사항
		String memId = authMember.getMemId();
		String memPass = req.getParameter("memPass");
		
		//MemberVO 2가지의 신원정보를 가지고 있어. 
		MemberVO inputData = new MemberVO();
		inputData.setMemId(memId);
		inputData.setMemPass(memPass);
		
		
		//검증의타겟  
		MemberVO member = new MemberVO();
		req.setAttribute("member", member);
		try {
			BeanUtils.populate(member, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new ServletException(e);
		}
		
		//검증하기  
		Map<String, List<String>> errors = new LinkedHashMap<>();
		boolean valid = ValidationUtils.validate(inputData, errors, DeleteGroup.class);
		
		
		
		//모든 이동구조 리다이렉트 
		
		if(valid) {  //누락 정상작동 여기서 탈퇴시킴 2.비지니스 로직을 통해 모델 확보  
			
			//로직을 가지고 있는 서비스  
			ServiceResult result = service.removeMember(inputData);
			
			//존재하지 않을땐 예외발생 
			
		
			case INVALIDPASSWORD: //비번오류 -> 마이페이지
				session.setAttribute("message", "비밀번호 오류"); //req가 아닌, 세션에 담아야해 
				viewName = "redirect/mypage.do";
				break;
			case FAIL:  //누락X 정상임 
				session.setAttribute("message", "서버 오류, 쫌따 다시.");
				viewName = "redirect/mypage.do";
				break;
				
			default:   
				session.invalidate(); //탈퇴 세션 종료후 웰컴페이지
				viewName = "redirect:/";
				break;
			}
			
		}else {
			session.setAttribute("message", "아이디,비번누락");
			viewName = "redirect/mypage.do";
		}
		
		new InternalResourceViewResolver("/WEB-INF/views/",).resolveView(viewName, req, resp);
	}
	
	

*/


package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;


@WebServlet("/member/memberDelete.do")
public class MemberDeleteControllerServlet extends HttpServlet {
   
   private static final Logger log = LoggerFactory.getLogger(MemberDeleteControllerServlet.class);
   
   private MemberService service = new MemberServiceImpl();
   
   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//      1.
      HttpSession session = req.getSession();
      MemberVO authMember = (MemberVO)session.getAttribute("authMember");
      String memId = authMember.getMemId();
      
      String memPass = req.getParameter("memPass");
      
      MemberVO inputDate = new MemberVO();
      inputDate.setMemId(memId);
      inputDate.setMemPass(memPass);
      
      Map<String, List<String>> errors = new LinkedHashMap<>();
      Boolean valid = ValidationUtils.validate(inputDate, errors, DeleteGroup.class);
      
      String viewName = null;
      if(valid) {
         ServiceResult result = service.removeMember(inputDate);
         
         switch (result) {
         case INVALIDPASSWORD:
            session.setAttribute("message", "비번 오류");
            viewName = "redirect://mypage.do";
            break;
         case FAIL:
            session.setAttribute("message", "서버 오류");
            viewName = "redirect://mypage.do";
            break;
         default:
            session.invalidate();
            viewName = "redirect:/";
            break;
         }
      }
      else {
         session.setAttribute("message", "아이디나 비밀번호 누락");
         viewName = "redirect:/mypage.do";
      }
      
      new InternalResourceViewResolver("/WEB-INF/views/",".jsp").resolveView(viewName, req, resp);
   }
}
      
      
      
      
   