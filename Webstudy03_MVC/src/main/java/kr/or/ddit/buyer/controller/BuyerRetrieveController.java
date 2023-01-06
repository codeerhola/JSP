package kr.or.ddit.buyer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.buyer.service.BuyerService;
import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BuyerRetrieveController{
	private BuyerService service = new BuyerServiceImpl();
	
	@RequestMapping("/buyer/buyerList.do")
	public String buyerList(
			@RequestParam(value="page", required=false, defaultValue="1") int currentPage
			, @ModelAttribute("simpleCondition") SearchVO simpleCondition
			, HttpServletRequest req
			, HttpServletResponse resp
	){
		PagingVO<BuyerVO> pagingVO = new PagingVO<>(4,2); 
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setSimpleCondition(simpleCondition);
		
		service.retrieveBuyerList(pagingVO);
		
		req.setAttribute("pagingVO" , pagingVO);
		
		log.info("paging data : {}", pagingVO);
		
		return "buyer/buyerList";
	}
	
	@RequestMapping("/buyer/buyerView.do")
	public String buyerView(
		@RequestParam("what") String buyerId
		, HttpServletRequest req
	) {
		BuyerVO buyer = service.retrieveBuyer(buyerId);
		req.setAttribute("buyer", buyer);
		return "buyer/buyerView";
	}
}











