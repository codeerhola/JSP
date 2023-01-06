package kr.or.ddit.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FileUploadController {
	
	@RequestMapping(value="/file/upload.do",method=RequestMethod.POST)
//	@PostMaping("/file/upload.do")
	
	public String upload(HttpServletRequest req, HttpSession session) throws IOException, ServletException {
		//3개 
		String textPart = req.getParameter("textPart");
		String numPart = req.getParameter("numPart");
		String datePart = req.getParameter("datePart");
		log.info("textPart:{}",textPart);
		log.info("numPart:{}",numPart);
		log.info("datePart:{}",datePart);
		
		session.setAttribute("textPart", textPart);
		session.setAttribute("numPart", numPart);
		session.setAttribute("datePart", datePart);
		
		String saveFolderURL = "/resources/prodImages";
		ServletContext application = req.getServletContext();
		String saveFolderPath = application.getRealPath(saveFolderURL); //여기에 저장 
		
		File saveFolder = new File(saveFolderPath);//물리경로니까 path를 넣어 
		if(!saveFolder.exists()) 
			saveFolder.mkdirs();
		
		//파트를 두개로 쪼갬?
		List<String> metadata = req.getParts().stream()
			.filter((p)->p.getContentType()!=null && p.getContentType().startsWith("image/")) //하나의 파트가 업로드 된느 모든 정보를 갖고 있음 p는 타입 하나씩의 정보를 갖고 있음  
			.map((p)->{ //
				
				String originalFilename = p.getSubmittedFileName(); //원본파일명
				String saveFilename = UUID.randomUUID().toString(); //겹치지 않는 파일명을 만듦 저장명
				
				//실제저장
				File saveFile = new File(saveFolder, saveFilename);
				
				try {
					p.write(saveFile.getCanonicalPath()); //바로사용하기 쉽게 url로만들꺼야
					String saveFileURL = saveFolderURL+"/"+saveFilename;
					return saveFileURL; //문자열이 반환됨 이걸 묶어서 배열이나 리스트로 만들어 
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}).collect(Collectors.toList());
		
		session.setAttribute("fileMetadata", metadata);
		
		return "redirect:/fileupload/uploadForm.jsp";
	}
}
