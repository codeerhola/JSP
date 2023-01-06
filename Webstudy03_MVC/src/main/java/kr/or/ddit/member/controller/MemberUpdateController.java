package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;


@Controller
public class MemberUpdateController{
	
	private MemberService service = new MemberServiceImpl();
	
	@RequestMapping("/member/memberUpdate.do")
	public String updateForm(HttpSession session, 
//	    	, @SessionAttribute("authMember") MemberVO authMember			
			HttpServletRequest req) {
		
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		MemberVO member = service.retrieveMember(authMember.getMemId());
		
		req.setAttribute("member", member);
		return "member/memberForm";
		
	}
	
	@RequestMapping(value="/member/memberUpdate.do",method=RequestMethod.POST)
	public String updateProcess(
				@ModelAttribute("member") MemberVO member  
				,HttpServletRequest req
				,@RequestPart(value="memImgage", required=false) MultipartFile memImage
				,HttpSession session
			) throws IOException{
		
		member.setMemImage(memImage);
		
		//@ModelAttribute("member") MemberVO member 밑에 주석을 이 한줄로 가능  
		
/*		MemberVO member = new MemberVO();
		req.setAttribute("member", member);
		try {
			BeanUtils.populate(member, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new ServletException(e);
		}
*/		
		String viewName = null;
		
		
		//검증
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = ValidationUtils.validate(member, errors, UpdateGroup.class);
		
		if(valid) {
			ServiceResult result = service.modifyMember(member);
			switch (result) {
			case INVALIDPASSWORD:
				req.setAttribute("message", "비밀번호 오류");
				viewName = "member/memberForm";
				break;
			case FAIL:
				req.setAttribute("message", "서버 오류, 쫌따 다시.");
				viewName = "member/memberForm";
				break;
				
			default: //등록성공
				
				MemberVO modifiedMember = service.retrieveMember(member.getMemId());
				session.setAttribute("authMember", modifiedMember);
				viewName = "redirect:/mypage.do";
				break;
			}
		}else {
			viewName = "member/memberForm";
		}
		return viewName;
	}
}



