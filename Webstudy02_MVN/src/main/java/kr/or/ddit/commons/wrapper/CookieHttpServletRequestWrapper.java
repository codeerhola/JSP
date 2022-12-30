package kr.or.ddit.commons.wrapper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CookieHttpServletRequestWrapper extends HttpServletRequestWrapper{
	
	private Map<String, Cookie> cookieMap;  //
	

	public CookieHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		cookieMap = new HashMap<>();
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for(Cookie tmp:cookies) {
				cookieMap.put(tmp.getName(), tmp); //이름주면 쿠기 가져와 
			}
		}
	}
	
	public Cookie getCookie(String name) {
		return cookieMap.get(name);
	}
	public String getCookieValue(String name) {
		Cookie finded = getCookie(name);//널일수도 있자나 있으면 쿠키값 반환 없으면 널 
		return Optional.ofNullable(finded)
				.map(cookie->{
			   try {
				return URLDecoder.decode(cookie.getValue(),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}).orElse(null); 			 
	}

}
