package kr.or.ddit.login.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.login.service.AuthenticateService;
import kr.or.ddit.login.service.AuthenticateServiceImpl;
import kr.or.ddit.vo.MemberVO;

/**
 * 1. 검증에 통과하지 못했을 경우, 다시 로그인 폼으로 이동함.
 * 2. 인증에 통과하지 못했을 경우, 다시 로그인 폼으로 이동함.
 *   - 비밀번호 오류 상태를 가정하고, 메시지 전달 -> alert 함수로 메시지 출력.
 *   - 이전에 입력받은 아이디의 상태를 유지함.
 * 3. 인증 완료시에 웰컴 페이지로 이동함.
 */
@WebServlet("/login/loginProcess.do")
public class LoginProcessControllerServlet extends HttpServlet{
	
   private AuthenticateService service = new AuthenticateServiceImpl();
   
   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   
      HttpSession session = req.getSession();
      if(session.isNew()) {
         resp.sendError(400, "로그인 폼이 없는데 어떻게 로그인을 하지??");
         return;
      }
      String memId = req.getParameter("memId");
      String memPass = req.getParameter("memPass");
      String saveId = req.getParameter("saveId");

      // 파라미터에 들어오는 값(memId)이랑 setMemId와가 너무 비슷하다.
      // BeanUtils (자바빈을 뜻한다.) : 자바빈의 규약을 지킨 것만 이 라이브러리를 사용 가능했다. 중프때 쓴거.
      MemberVO member = new MemberVO();
      member.setMemId(memId);
      member.setMemPass(memPass);
      
      boolean valid = validate(member);
      String viewName = null;
      
      if(valid) {
         try {
            ServiceResult result = service.authenticate(member);
            if(ServiceResult.OK.equals(result)) {
               Cookie saveIdCookie = new Cookie("savedId", member.getMemId());
               saveIdCookie.setDomain("localhost");
               saveIdCookie.setPath(req.getContextPath());
               int maxAge = 0;
               if(StringUtils.isNotBlank(saveId)) {
                  maxAge = 60*60*24*5;
               }
               saveIdCookie.setMaxAge(maxAge);
               resp.addCookie(saveIdCookie);
               session.setAttribute("authMember", member);
               viewName = "redirect:/";            
            }else {            
               session.setAttribute("validId", memId);
               session.setAttribute("message", "비밀번호 오류");
               viewName = "redirect:/login/loginForm.jsp";
            }
         }catch (UserNotFoundException e) {
            session.setAttribute("message", "존재하지 않는 회원입니다.");
            viewName = "redirect:/login/loginForm.jsp";
         }
         
      }else {
         session.setAttribute("message", "아이디나 비밀번호 누락");
         viewName = "redirect:/login/loginForm.jsp";
      }
      
      // 5. view 이동
      if(viewName.startsWith("redirect:")) {
         viewName = viewName.substring("redirect:".length());
         resp.sendRedirect(req.getContextPath() + viewName);
      }else {
         req.getRequestDispatcher(viewName).forward(req, resp);
      }
   }
   // 검증
   private boolean validate(MemberVO member) {
      boolean valid = true;
      if(StringUtils.isBlank(member.getMemId())) {
         valid = false;
      }
      if(StringUtils.isBlank(member.getMemPass())) {
         valid = false;
      }
      return valid;
   }

}