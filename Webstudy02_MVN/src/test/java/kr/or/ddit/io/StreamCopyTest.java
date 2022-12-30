package kr.or.ddit.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class StreamCopyTest {
	
	
		private File targetFile;
		private File destFile;
		
		@Before
		public void setUp() throws IOException {
			targetFile = new File("D:/contents/movie/mv.mp4");
			destFile = new File("d:/target1.mp4");
			
		}
	
			/*try {
					//파일의 내용을 읽어오기위한 준비
					FileInputStream fis = new FileInputStream(targetFile);
					//파일의 내용을 쓰기 위한 준비
					FileOutputStream fos = new FileOutputStream(destFile);

					//파일을 읽고 쓰기를 합니다. 
					int nRealByte = 0;
					
					while ((nRealByte = fis.read()) != -1) {
						fos.write(nRealByte);
					}
					
					//파일스트림을 닫아줍니다.
					fis.close();
					fos.close();

				} catch (Exception e) {
					//파일 처리 실패시 -1를 리턴합니다.
					System.out.println(e.getLocalizedMessage());
					
				}
				//성공시에 메세지 출력후 1을 리턴합니다.
				System.out.println("copy succeed !!");
			}
		
*/		
	@Test
	public void copytest1() { //4초소요

		try {
			FileInputStream fis = new FileInputStream(targetFile);
			FileOutputStream fos = new FileOutputStream(destFile);

			int tmp = -1;
			while ((tmp = fis.read()) != -1) {
				fos.write(tmp);
			}
		} catch (IOException e) {

		}

	}
	
	
	@Test
	public void copytest2() throws IOException {
	

		try( 
			FileInputStream fis = new FileInputStream(targetFile);
			FileOutputStream fos = new FileOutputStream(destFile);
		){	
			byte[] buffer = new byte[1024]; //버퍼사용 
			int length = -1;
			int count = 1;
			while ((length = fis.read(buffer)) != -1) {
				if(count++==1) {
					Arrays.fill(buffer, (byte)0);
				}
				fos.write(buffer,0,length); //첫번째는 모두 2번째는 나머지 부분만
			}
		}
	}

	
	@Test
	public void copytest3() throws IOException { //2차스트림 

		try( 
			FileInputStream fis = new FileInputStream(targetFile);
			FileOutputStream fos = new FileOutputStream(destFile);
				
			BufferedInputStream bis = new BufferedInputStream(fis);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
		){
			
			int tmp = -1; //읽어들인 1 바이트 
			while ((tmp = bis.read()) != -1) 
				bos.write(tmp); //멀넘겨?
		}
	}
	
	
	
	
	
	

}
