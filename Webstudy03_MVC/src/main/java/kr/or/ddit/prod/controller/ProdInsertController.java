package kr.or.ddit.prod.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.mvc.multipart.MultipartHttpServletRequest;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.ProdVO;


@Controller
public class ProdInsertController{
	private ProdService service = new ProdServiceImpl();
	private OthersDAO othersDAO = new OthersImpl();
	
	private void addAttribute(HttpServletRequest req) {
		req.setAttribute("lprodList", othersDAO.selectLprodList());
		req.setAttribute("buyerList", othersDAO.selectBuyerList(null));
	}
	
	@RequestMapping("/prod/prodInsert.do")
	public String prodForm(HttpServletRequest req){
		addAttribute(req);
		return "prod/prodForm";
		
		
		
		
		
	}

	@RequestMapping(value="/prod/prodInsert.do", method=RequestMethod.POST)
	public String insertProcess(
		@ModelAttribute("prod") ProdVO prod	
		,@RequestPart("prodImage") MultipartFile prodImage
		,HttpServletRequest req
	) throws IOException {
		addAttribute(req);
			//prodImage -> prodImg
		
			//vo에 추가하고 추가한 코드
			prod.setProdImage(prodImage);
			
//1.저장				
			String saveFolderURL = "/resources/prodImages";
			ServletContext application = req.getServletContext();
			String saveFolderPath = application.getRealPath(saveFolderURL); //여기에 저장 
				
			File saveFolder = new File(saveFolderPath);//물리경로니까 path를 넣어 
			if(!saveFolder.exists()) 
				saveFolder.mkdirs();
				
			//저장, 메타데이터, db저장은 prodVO(도메인레이어)에서 다 해줌
			prod.saveTo(saveFolder);
			
		
		Map<String, List<String>> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		boolean valid = ValidationUtils.validate(prod, errors, InsertGroup.class);
		String viewName = null;
		if(valid) {
			ServiceResult result = service.createProd(prod);
			if(ServiceResult.OK == result) {
				viewName = "redirect:/prod/prodView.do?what="+prod.getProdId();
			}else {
				req.setAttribute("message", "서버 오류, 쫌따 다시");
				viewName = "prod/prodForm";
			}
		}else {
			viewName = "prod/prodForm";
		}
		return viewName;
	}
}

