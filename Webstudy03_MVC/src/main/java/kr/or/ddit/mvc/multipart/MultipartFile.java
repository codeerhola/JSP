package kr.or.ddit.mvc.multipart;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface MultipartFile {
	
	public byte[] getBytes()throws IOException; 
	public String getContentType();
	public InputStream getInputSream() throws IOException;
	public String getName();
	public String getOriginalFilename();
	public long getSize(); 
	//비어있는지 여부 확인(아무것도 입력을 안해도 파트는 형성이 된다)  
	public boolean isEmpty();
	public void transferTo(File dest)throws IOException;

}
