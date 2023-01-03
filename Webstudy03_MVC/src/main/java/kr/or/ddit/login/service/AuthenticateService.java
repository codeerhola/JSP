package kr.or.ddit.login.service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.MemberVO;

/**
 * 인증 처리를 위한 Business Logic Layer 
 */
public interface AuthenticateService {
	
	/**
	 * 인증 여부 판단
	 * @param member
	 * @return 성공, 비번오류, 존재하지 않은 경우 (UserNotFoundException) 
	 */
	public ServiceResult authenticate(MemberVO member);

}
