package kr.or.ddit.mvc.multipart;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class StandardServletMultipartFile implements MultipartFile {
	
	
	private Part adatee; //2.X version은  part 대신 fileitem
	
	
	//기본생성자가 없기때문에 이 클래스가 랩퍼가 된느것. 실제 데이터는 adaptee가 갖고 있어 
	public StandardServletMultipartFile(Part adatee) {
		super();
		this.adatee = adatee;
	}

	@Override
	public byte[] getBytes()throws IOException {
//		ByteArrayOutputStream os = new ByteArrayOutputStream();
//		InputStream is = getInputSream();
//		os.toByteArray();
		return IOUtils.toByteArray(getInputSream());
	}

	@Override
	public String getContentType() {
		return adatee.getContentType();
	}

	@Override
	public InputStream getInputSream() throws IOException{
		return adatee.getInputStream();
	}

	@Override
	public String getName() {
		return adatee.getName();
	}

	@Override
	public String getOriginalFilename() {
		return adatee.getSubmittedFileName();
	}

	@Override
	public long getSize() {
		return adatee.getSize();
	}

	@Override
	public boolean isEmpty() {
		return StringUtils.isBlank(getOriginalFilename());
	}

	@Override
	public void transferTo(File dest)throws IOException {
		adatee.write(dest.getCanonicalPath());

	}

}
