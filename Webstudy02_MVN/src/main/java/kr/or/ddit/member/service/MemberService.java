package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;

/**
 * 회원관리(CRUD)를 위한 Business Logic Layer
 */
public interface MemberService {
	
	/** 회원 가입 
	 * @param member
	 * @return 아이디 중복으로 인한 실패(PKDUPLICATED),가입 성공(OK), 가입 실패(FAIL) 가 발생 할수 있고 이걸 식별 할 수 있어야해 
	 */
	public ServiceResult createMember(MemberVO member);
	
	public List<MemberVO> retrieveMemberList(PagingVO<MemberVO> pagingVO);
	
	/** 회원 정보 상세 조회 
	 * @param memId
	 * @return 존재하지않은경우, {@link UserNotFoundException}
	 */
	public MemberVO retrieveMember(String memId);

	
	/** 정보 수정  
	 * @param member
	 * @return 존재 부 (NOTEXIST), 비번 인증 실패(INVALIDPASSWORD) , 성공(OK), 실패(FAIL) 
	 */
	public ServiceResult modifyMember(MemberVO member); 	//명확하게 상수로 반환 가능 ? 
	
	
	/** 회원 탈퇴 
	 * @param memId -> membervo
	 * @return 존재 여부, 비번 인증 (memid->membervo) 실패,성공, 실패 
	 */
	public ServiceResult removeMember(MemberVO member);

}
