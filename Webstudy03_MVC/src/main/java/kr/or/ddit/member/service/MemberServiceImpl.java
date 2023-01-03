package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.login.service.AuthenticateService;
import kr.or.ddit.login.service.AuthenticateServiceImpl;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
//비즈니스로직 
public class MemberServiceImpl implements MemberService {
	
	
	//1.의존관계형성 결합력 최상 
	private MemberDAO memberDAO = new MemberDAOImpl();
	
	private AuthenticateService authService = new AuthenticateServiceImpl();
	

	@Override
	public ServiceResult createMember(MemberVO member) {
		ServiceResult result = null;
		try {
			retrieveMember(member.getMemId());
			result = ServiceResult.PKDUPLICATED;
			//1. 
		}catch (UserNotFoundException e) {  //가입할수있어
			int rowcnt = memberDAO.insertMember(member);
			result = rowcnt > 0 ? ServiceResult.OK : ServiceResult.FAIL; 
		}
		return result;
	}

	//리스트출력
	@Override
	public List<MemberVO> retrieveMemberList(PagingVO<MemberVO> pagingVO) {
		pagingVO.setTotalRecord(memberDAO.selectTotalRecord(pagingVO));
		
		
		List<MemberVO> list = memberDAO.selectMemberList(pagingVO);
		
		pagingVO.setDataList(list); //필요한 모든걸 갖고 있어 
		return list;
	}
	
	//상세보기
	@Override
	public MemberVO retrieveMember(String memId) {
		MemberVO member = memberDAO.selectMember(memId);
		
		if(member==null)
			throw new UserNotFoundException(String.format(memId+"에 해당하는 사용자 없음."));
		return member;
	}

	//회원 정보 수정 	
	@Override
	public ServiceResult modifyMember(MemberVO member) {
		//안에 유지를 하되, id , password만 넘길 수 있어야해 
		MemberVO inputData = new MemberVO();
		inputData.setMemId(member.getMemId());
		inputData.setMemPass(member.getMemPass());
		
		//member와 분리 
		ServiceResult result = authService.authenticate(inputData);
			if(ServiceResult.OK.equals(result)) {
			int rowcnt = memberDAO.updateMember(member);
			result = rowcnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
		}
		return result;
	}

	
	@Override
	public ServiceResult removeMember(MemberVO member) {
		
		//있나없나 비번인증 통과후 삭제 처리 
		//인증과 관련된 코드를 따로 빼놔서 여기는 수정을 안해도됨. 
		ServiceResult result = authService.authenticate(member);
		
		if(ServiceResult.OK.equals(result)) {
			int rowcnt = memberDAO.deleteMember(member.getMemId());
			result = rowcnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
		}
		return result;
	}
}
















