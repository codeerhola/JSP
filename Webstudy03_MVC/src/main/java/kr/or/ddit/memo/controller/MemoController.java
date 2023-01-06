package kr.or.ddit.memo.controller;

import java.awt.color.CMMException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import kr.or.ddit.memo.dao.MemoDAO;
import kr.or.ddit.memo.dao.MemoDAOImpl;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.vo.MemoVO;

// 컨트롤러 1개로 처리함 

@Controller
public class MemoController {
	
	//장점 : log메시지 일괄관리 가능 
	private static final Logger log = LoggerFactory.getLogger(MemoController.class);//sysout대신  클래스 용하면 계층구조 활용 가능 
	
	private MemoDAO dao = new MemoDAOImpl();

	//RequestHeaer("Accept") 
	@RequestMapping("/memo")
	public String doGet(
			//@RequestHeaer("Accept") String accept
			HttpServletRequest req, HttpServletResponse resp
			) throws ServletException, IOException {
		
		//요청분석
		String accept = req.getHeader("Accept");
		log.info("accept header : {}", accept);
		if(accept.contains("xml")){
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return null;
		}
		//모델 확보
		List<MemoVO> memoList = dao.selectMemoList();
		System.out.println(memoList);
		
		//그 모델 공유 setAttrivute
		req.setAttribute("memoList", memoList); 
		//5.뷰선택
		return "forward:/jsonView.do";
		
		
	}
	@RequestMapping(value="/memo",method=RequestMethod.POST)
	public String doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//post-Redirect-Get : PRG pattern
		MemoVO memo = geMemoFromRequest(req);
		dao.insertMemo(memo); //메모를 작성한후 처리가 끝남
		return "redirect:/";
	}

	private MemoVO geMemoFromRequest(HttpServletRequest req) throws IOException{
		String contentType = req.getContentType();
		MemoVO memo = null;
		
		
		if(contentType.contains("json")) {
			try(
					BufferedReader br = req.getReader(); //body content read용 입력 스트림
			){
				memo = new ObjectMapper().readValue(br, MemoVO.class);
			}
			
		}else if(contentType.contains("xml")) {
			try(
					BufferedReader br = req.getReader(); //body content read용 입력 스트림
			){
				memo = new XmlMapper().readValue(br, MemoVO.class);
			}
		}else {
			memo = new MemoVO();
			memo.setWriter(req.getParameter("writer"));
			memo.setDate(req.getParameter("date"));
			memo.setContent(req.getParameter("content"));
			
		}
		return memo;
		
	}

	//
	@RequestMapping(value="/memo",method=RequestMethod.PUT)
	public String doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
	
	@RequestMapping(value="/memo",method=RequestMethod.DELETE)
	public String doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null; 
	}

}
