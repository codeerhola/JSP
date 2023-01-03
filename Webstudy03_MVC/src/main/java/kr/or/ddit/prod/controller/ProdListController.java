package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;
import kr.or.ddit.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;

@Controller
public class ProdListController {

	private ProdService service = new ProdServiceImpl();
	private OthersDAO othersDAO = new OthersImpl();
	
	private void addAttribute(HttpServletRequest req) {
		req.setAttribute("lprodList", othersDAO.selectLprodList());
		req.setAttribute("buyerList", othersDAO.selectBuyerList(null)); //전체조회 
	}
	
	//1.UI 필요한 처리구조
	private String listUI(HttpServletRequest req) {
		addAttribute(req);
		return "prod/prodList";
	}
	//2. 데이터 필요한 처리구조 필요 
	private String listData(
			int currentPage  
			,HttpServletRequest req
	) throws ServletException {
		//파라미터 가져오기 
		//String pageParam = req.getParameter("page");
		
		ProdVO detailCondition = new ProdVO();
		req.setAttribute("detailCondition", detailCondition);
		
		/*detailCondition.setProdLgu(req.getParameter("prodLgu"));
		detailCondition.setProdBuyer(req.getParameter("prodBuyer"));
		detailCondition.setProdName(req.getParameter("prodName"));*/
		//prodbo가 다 가지고 있어  위 3줄 밑에 코드로  가능 
		
		try {
			BeanUtils.populate(detailCondition, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
				throw new ServletException(e);
		}
		
//		int currentPage = 1;
//		//파라미터 검증 
//		if(StringUtils.isNumeric(pageParam)) {
//			currentPage = Integer.parseInt(pageParam);
//		}
		
		// 페이징vo 만듦
		PagingVO<ProdVO> pagingVO = new PagingVO<>(5, 2);
		
		pagingVO.setCurrentPage(currentPage);
		//pagingVO.setSimpleCondition(simpleCondition); //단순검색 
		pagingVO.setDetailCondition(detailCondition); //상세검색

		service.retriveProdList(pagingVO);//currentPage 정보가 있는 pagingVO 3번
		
		req.setAttribute("pagingVO", pagingVO);
		return "forward:/jsonView.do";
	}
	
	//실제로 어댑터가 호출하는 곳 
	@RequestMapping("/prod/prodList.do")
	public String prodList(
			@RequestParam(value="page", required=false, defaultValue="1")int currentPage  
			,HttpServletRequest req) throws ServletException {
		

				String accept = req.getHeader("Accept");
				String viewName = null;
				
				
				if(accept.contains("json")) {
					viewName = listData(currentPage,req);
					
				}else {
					viewName = listUI(req);
				}
				return viewName;
	}

}

