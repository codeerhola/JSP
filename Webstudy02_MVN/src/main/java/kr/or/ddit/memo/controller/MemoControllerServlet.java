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
import kr.or.ddit.vo.MemoVO;

// 컨트롤러 1개로 처리함 

@WebServlet("/memo")
public class MemoControllerServlet extends HttpServlet{
	
	//장점 : log메시지 일괄관리 가능 
	private static final Logger log = LoggerFactory.getLogger(MemoControllerServlet.class);//sysout대신  클래스 용하면 계층구조 활용 가능 
	
	//1.의존관계 dao 형성 
//	private MemoDAO dao = FileSystemMemoDAOImpl.getInstance();
	//private MemoDAO dao = DataBaseMemoDAOImpl.getInstance();
	private MemoDAO dao = new MemoDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//요청분석
		String accept = req.getHeader("Accept");
		log.info("accept header : {}", accept);
		if(accept.contains("xml")){
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return;
		}
		//모델 확보
		List<MemoVO> memoList = dao.selectMemoList();
		System.out.println(memoList);
		
		//그 모델 공유 setAttrivute
		req.setAttribute("memoList", memoList); 
		//5.뷰선택
		String viewName = "/jsonView.do";
		req.getRequestDispatcher(viewName).forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//post-Redirect-Get : PRG pattern
		MemoVO memo = geMemoFromRequest(req);
		dao.insertMemo(memo); //메모를 작성한후 처리가 끝남
		resp.sendRedirect(req.getContextPath()+"/memo");
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
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}
