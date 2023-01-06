package kr.or.ddit.mvc.multipart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Part -> MultipartFile
 * 
 */

public class MultipartHttpServletRequest extends HttpServletRequestWrapper{
	
	//멀티파트만 관리하는 맵  이름은 하나인데 List 구조로 여러개를 관리 할 수 있어 
	private Map<String, List<MultipartFile>> fileMap; //모든 파트데이터는 맵을 가지고 있어! 파트네임이 키값임 
	
	public MultipartHttpServletRequest(HttpServletRequest request) throws IOException, ServletException {
		super(request);
		parseRequest(request); //가장 먼저 할 일 Map 만들기 
	}
	
	//맵을 만들어줌
	private void parseRequest(HttpServletRequest request) throws IOException, ServletException {
		fileMap = new LinkedHashMap<>();
		
		request.getParts().stream()//전체 파트정보 꺼내옴
				.filter((p)->p.getContentType()!=null)
				.forEach((p)->{    //파트하나를 멀티파트로 만듦 이걸 맵에 넣어줌 하나하나 독립적으로 접근할수 있는 forreach 필요 
					
					String partName = p.getName(); //input태그의 네임 속성값
					MultipartFile file = new StandardServletMultipartFile(p);
					
					//있는지 없는지 확인?
					List<MultipartFile> fileList = Optional.ofNullable(fileMap.get(partName))
													.orElse(new ArrayList<>());
					fileList.add(file);
					fileMap.put(partName, fileList);  //키 : partName 값: fileList
				});
	}

	//getparameter map 처럼  fileMap을 통채로 반환 가능
	public Map<String, List<MultipartFile>> getFileMap() {
		return fileMap;
	}
	
	//하나꺼내기 
	public MultipartFile getFile(String name) {
		List<MultipartFile> files = fileMap.get(name);
		if(files!=null && !files.isEmpty())
			return files.get(0);
		else return null;
	}
	public List<MultipartFile> getFiles(String name){
		return fileMap.get(name);
	}
	
	public Enumeration<String> getFileNames(){
		Iterator<String> naems = fileMap.keySet().iterator();
		return new Enumeration<String>() {
			
			@Override
			public boolean hasMoreElements() {
				return naems.hasNext();
			}

			@Override
			public String nextElement() {
				return naems.next();
			}
		};
	}
}

