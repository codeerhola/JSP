package kr.or.ddit.security;

import org.junit.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordEncoderTest {
	
	PasswordEncoder encoder =  PasswordEncoderFactories.createDelegatingPasswordEncoder();
	String password = "java-";
	String mem_pass = "{bcrypt}$2a$10$mDJNrnWvPdMesuw7qkK5uui5xIBK1vQivJbu9PWdTeGwivg24W7c.";
	//단방향이기때문에 얘를 다시 되돌릴수 없어 그래서 password를 다시 암호화 해서 mem_pass랑 비교 
	
	
	public void encodeTest() {
		String encoded = encoder.encode(password); //인코딩까지 다됨
		log.info("mem_pass :{}",encoded);
	}
	
	
	@Test
	public void matchTest() {
		
		//String encoded = encoder.encode(password); //결과값이 false임 이게 아닌 밑에코드
		log.info("match result :{}",encoder.matches(password, mem_pass));
		
	}
}

