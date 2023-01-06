package kr.or.ddit.prod.controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.ProdVO;

//prod/prodUpdate.do (GET->url제공, POST->processing)
//필수가 아님 

@Controller
public class ProdUpdateController {
	
	private ProdService service = new ProdServiceImpl(); 
	private OthersDAO othersDAO = new OthersImpl();
	
	
	private void addAttribute(HttpServletRequest req) {
		req.setAttribute("lprodList", othersDAO.selectLprodList());
		req.setAttribute("buyerList", othersDAO.selectBuyerList(null)); //전체조회 
	}
	
	
	@RequestMapping("/prod/prodUpdate.do")
	public String updateProdForm(
			HttpServletRequest req
			,@RequestParam("what") String prodId //파라미터명 what , 어떤상품 수정? 요청에 대한 전처리를 핸들러 어댑터가 해줌 
			) {
		addAttribute(req);
		
		ProdVO prod = service.retriveProd(prodId);
		req.setAttribute("prod", prod); //초기값 세팅 
		addAttribute(req);
		return "prod/prodForm";  

	}
	
	
	//제일먼저? 
	@RequestMapping(value = "/prod/prodUpdate.do", method = RequestMethod.POST)
	public String updateprocess(HttpServletRequest req
			,HttpServletResponse resp
			, @ModelAttribute("prod") ProdVO prod // command
			, @RequestParam("what") String prodId,
			@RequestPart(value = "prodImage", required = false) MultipartFile prodImage) throws IOException { // 있을수도 있고
																												// 없을 수도
		prod.setProdImage(prodImage);

		// 1.저장
		String saveFolderURL = "/resources/prodImages";
		ServletContext application = req.getServletContext();
		String saveFolderPath = application.getRealPath(saveFolderURL); // 여기에 저장

		File saveFolder = new File(saveFolderPath);// 물리경로니까 path를 넣어
		if (!saveFolder.exists())
			saveFolder.mkdirs();

		// 저장, 메타데이터, db저장은 prodVO(도메인레이어)에서 다 해줌
		prod.saveTo(saveFolder);

		String viewName = null;
		// 검증
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);

		boolean valid = ValidationUtils.validate(prod, errors, UpdateGroup.class);

		if (valid) {
			ServiceResult result = service.modifyProd(prod);
			if (ServiceResult.OK == result) {
				viewName = "redirect:/prod/prodView.do?what=" + prod.getProdId(); // 꼭갖고가야할 파라미터 이건 프로드에서 꺼냄
			} else {
				req.setAttribute("message", "서버 오류, 쫌따 다시");
				viewName = "prod/prodForm";
			}
		} else {
			viewName = "prod/prodForm";

		}

		return viewName;
	}
}






