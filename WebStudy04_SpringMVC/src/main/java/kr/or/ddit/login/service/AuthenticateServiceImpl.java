package kr.or.ddit.login.service;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.vo.MemberVO;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {
	
	private MemberDAO memberDAO;
	
	@Inject
	public AuthenticateServiceImpl(MemberDAO memberDAO) {
		super();
		this.memberDAO = memberDAO;
	}
	
	@Resource(name="passwordEncoder")
	private PasswordEncoder encoder;
	
	@Override
	public ServiceResult authenticate(MemberVO member) {
		MemberVO savedMember = memberDAO.selectMember(member.getMemId());
		if(savedMember==null ||  savedMember.isMemDelete())
			throw new UserNotFoundException(String.format("%s 사용자 없음.", member.getMemId()));
		String inputPass = member.getMemPass();
		String savedPass = savedMember.getMemPass();
		ServiceResult result = null;
		if(encoder.matches(inputPass, savedPass)) {
			
//			member.setMemName(savedMember.getMemName());
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
