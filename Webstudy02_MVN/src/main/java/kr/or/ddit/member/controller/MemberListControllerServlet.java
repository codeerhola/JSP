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
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.MemoVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/member/memberList.do")
public class MemberListControllerServlet extends HttpServlet{
	
	//서비스와 컨트롤러 연결 하기 
	private MemberService service = new MemberServiceImpl();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		//없으면 1페이지 있으면 해당하는 페이지 3개의 파라미터
		//검증의 데이터가 아님 검색을 할수도 있고 안할수도 있음 
		String pageParam = req.getParameter("page");
		String searchType =req.getParameter("searchType");
		String searchWord =req.getParameter("searchWord");
		
		//검색이 없을 수도 있어!! 
		SearchVO simpleCondition = new SearchVO(searchType, searchWord);
		
		int currentPage = 1;
		
		//파라미터 검증 
		if(StringUtils.isNumeric(pageParam)) {
			currentPage = Integer.parseInt(pageParam);
		}
		
		//페이징vo 만듦
		PagingVO<MemberVO> pagingVO = new PagingVO<>(4,2);//스크린사이즈, 블럭사이즈 
		//3번의 세터를 호출해야해 요청데이터 안에 포함되어 있는 유일한 프로퍼티는?
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setSimpleCondition(simpleCondition);
			
		
		List<MemberVO> memberList = service.retrieveMemberList(pagingVO);
		
		
		req.setAttribute("pagingVO", pagingVO);
		
		
		log.info("pagingdata : {}", pagingVO);
		
		String viewName = "member/memberList";
		
		new InternalResourceViewResolver("/WEB-INF/views/",".jsp").resolveView(viewName, req, resp);
		
	}

}
