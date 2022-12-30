package kr.or.ddit.login.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.memo.controller.MemoControllerServlet;
import kr.or.ddit.vo.MemberVO;

public class AuthenticateServiceImpl implements AuthenticateService {
	
	private MemberDAO memberDAO = new MemberDAOImpl();
	
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
		
		if(savedPass.equals(inputPass)) {
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

