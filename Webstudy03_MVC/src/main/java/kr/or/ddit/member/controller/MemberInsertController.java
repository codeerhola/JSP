package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

/**
 * Backend controller(command handler)--> Plain Old Java Object
 * 
 *  command handler 제약이 없어짐
 *  뒤에서 행동하는 핸들러 객체이다. 요청주소와 처리할수있는 핸들러가 하나로 합쳐짐 
 *   
 */
@Controller
public class MemberInsertController {
	
	private MemberService service = new MemberServiceImpl();
	
	@RequestMapping("/member/memberInsert.do")
	public String memberForm()  { 
		
		return  "member/memberForm";
	}
	
	@RequestMapping(value="/member/memberInsert.do",method=RequestMethod.POST)
	public String memberinsert(
			
			HttpServletRequest req
			,@ModelAttribute("member") MemberVO member
			) throws ServletException{ 
	
	
		//여기에서 검증해야해 
		
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = ValidationUtils.validate(member, errors, InsertGroup.class);
		
		String viewName = null;
		
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
		return viewName;
		
	}
}
