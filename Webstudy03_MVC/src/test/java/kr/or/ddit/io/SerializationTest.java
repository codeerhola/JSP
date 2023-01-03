package kr.or.ddit.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.vo.MemoVO;

/**
 *	Serialization(직렬화)
 *	: 전송이나 저장을 위해 객체의 상태를 바이트배열(스트림상태)로 변환하는 과정. 
 */
public class SerializationTest {
	
	//private Map<String, Object> writeData;
	private MemoVO wirteData;
	private File writeFile;
	
	
	@Before
	public void setUp() {
//		writeData = new HashMap<>();
//		writeData.put("code1",new Integer(23));
//		writeData.put("code2","TEXT");
//		writeData.put("code3",new Boolean(true));
		
		wirteData = new MemoVO();
		wirteData.setCode(1);
		wirteData.setWriter("작성자");
		wirteData.setContent("내용");
		
		String data = String.format("%1$ty-%1$tm-%1$td %1$tH:%1$tM:%1$tS",LocalDateTime.now()); //2022-12-14 17:20:00
		wirteData.setDate(data);
		
		writeFile = new File("d:/sample.dat");
	}
	//map이 가지고 있는 데이터를 wirteFile에 1,2차스트림
	//map기록할 수 있는 형태 
	
	
	
	//serialize  결과 : 
	/*@Test
	public void serializeTest() throws IOException {
		//1차, 2차 스트림 개방
		try(
				FileOutputStream fos = new FileOutputStream(writeFile);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
		){
			oos.writeObject(wirteData);
			
		}
	}*/
	
	@Test
	public void deSerializeTest() throws IOException, ClassNotFoundException{
		try(
				//1차스트림 개방 
				FileInputStream fis = new FileInputStream(writeFile);
				//2차스트림 개방
				ObjectInputStream ois = new ObjectInputStream(fis);
		){
			//Map<String, Object> map = (Map<String, Object>) ois.readObject();
			MemoVO memo = (MemoVO) ois.readObject();
			System.out.println(memo);
			
		}
		
	}
	//원래의 상태로 돌리는 역직렬화 필요
	
	

}
