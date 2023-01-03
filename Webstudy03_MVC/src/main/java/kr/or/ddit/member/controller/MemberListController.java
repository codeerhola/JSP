package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.MemoVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberListController {
	
	private MemberService service = new MemberServiceImpl();
	
	//실제 핸들러  싱글 어노테이션 메소드 겟으로 숨어있어 
	
	@RequestMapping("/member/memberList.do")
	public String memberList(
			@RequestParam(value="page",required=false, defaultValue="1")int currentPage
			//,@RequestParam(value="searchType", required=false) String searchType
			//,@RequestParam(value="searchWord", required=false) String searchWord
			,@ModelAttribute SearchVO simpleCondition
			,HttpServletRequest req
			) {
		
		
		//검색이 없을 수도 있어!! 
		//SearchVO simpleCondition = new SearchVO(searchType, searchWord);
		
		//페이징vo 만듦
		PagingVO<MemberVO> pagingVO = new PagingVO<>(4,2);//스크린사이즈, 블럭사이즈 
		//3번의 세터를 호출해야해 요청데이터 안에 포함되어 있는 유일한 프로퍼티는?
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setSimpleCondition(simpleCondition);
		
		List<MemberVO> memberList = service.retrieveMemberList(pagingVO);
		
		req.setAttribute("pagingVO", pagingVO);
		
		log.info("pagingdata : {}", pagingVO);
		
		String viewName = "member/memberList";
		return viewName;
	}
}
