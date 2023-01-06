package kr.or.ddit.login.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.memo.controller.MemoController;
import kr.or.ddit.vo.MemberVO;

public class AuthenticateServiceImpl implements AuthenticateService {
	
	private MemberDAO memberDAO = new MemberDAOImpl();
	PasswordEncoder encoder =  PasswordEncoderFactories.createDelegatingPasswordEncoder();//비밀번호인코딩
	
	private static final Logger log = LoggerFactory.getLogger(AuthenticateServiceImpl.class);//sysout대신  클래스 용하면 계층구조 활용 가능

	@Override
	public ServiceResult authenticate(MemberVO member) {
		
		MemberVO savedMember = memberDAO.selectMember(member.getMemId());
		
		if(savedMember==null || savedMember.isMemDelete())
			throw new UserNotFoundException(String.format("%s 사용자 없음.", member.getMemId()));
		
		String inputPass = member.getMemPass();
		String savedPass = savedMember.getMemPass();
		
		
		log.debug("inputPass : {}",inputPass);
		log.debug("savedPass : {}",savedPass);
		
		ServiceResult result = null;
		
		if(encoder.matches(inputPass, savedPass)) {
		//member.setMemName(savedMember.getMemName()); 대신에 밑에 코드
		//BeanUtils : Utility methods for populating JavaBeans properties via reflection.			
			try {
				BeanUtils.copyProperties(member, savedMember);
				result = ServiceResult.OK;
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}else {
			result = ServiceResult.INVALIDPASSWORD;
		}
		return result;
	}
}

//패스워드  
//input 값 : java 
//saved 값 : 암호  input -> saved로 바꿔주기?
