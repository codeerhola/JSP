package kr.or.ddit.auth;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JacksonInject.Value;

import kr.or.ddit.mvc.DispatcherServlet;
import lombok.extern.slf4j.Slf4j;

/**
 * 보호자원에 대한 요청인 경우, 신원확인(session authMember 속성)을 한 사용자인지 판단
 * 인증 
 */
@Slf4j
public class AuthenticationFilter implements Filter {
   
   private Map<String, String[]> securedResources;
   
   public static final String SECUREDNAME = "securedResources";

   @Override
   public void init(FilterConfig filterConfig) throws ServletException {
	   
      securedResources = new LinkedHashMap<>();
      
      filterConfig.getServletContext().setAttribute(SECUREDNAME, securedResources);
//      propertie파일 열기
      String filePath = filterConfig.getInitParameter("filePath");
      try(
         InputStream is = this.getClass().getResourceAsStream(filePath);
      
      ){
         Properties props = new Properties();
         props.load(is);
         props.keySet().stream()
             .map(Object::toString)
//             .collect(Collectors.toList())
             .forEach(key->{
                String value = props.getProperty(key);
                securedResources.put(key, value.split(","));
                log.info("보호자원 [{} : {}]",key,securedResources.get(key));
             }); // map -> 타입을 하나하나 변환해주는 메소드 , collect(Collectors.toList()) -> 리스트를 따로 가지고옴
      }catch (IOException e) {
         throw new ServletException(e); // wrapper
      }
   }
   //실제 필터링
   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
         throws IOException, ServletException {
      HttpServletRequest req = (HttpServletRequest)request;
      HttpServletResponse resp = (HttpServletResponse)response;
      String uri = req.getServletPath();
      
      boolean pass = true;
      
      //인증여부판단 
      if(securedResources.containsKey(uri)) {
    	  Principal principal = req.getUserPrincipal(); //인증안되면 null 반환 
         
         if(principal == null) {
            pass = false;
         }   
      }
      
      if(pass) {
         chain.doFilter(request, response); //경계선 --------이전 요청 이후 응답 
      }
      else {
         // 보호 자원 요청으로 신원확인 거치지 않은 경우 
         // loginForm 으로 이동, redirect
         String viewName = req.getContextPath()+"/login/loginForm.jsp";
         resp.sendRedirect(viewName);
      }
      
   }

   @Override
   public void destroy() {
      
   }

}