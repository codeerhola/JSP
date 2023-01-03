package kr.or.ddit.memo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.MemoVO;

//persistance layer 
//인퍼페이스의 컬러파이드 네임을 xml namespace  사용할 다오의 
public interface MemoDAO {
	//조회
	public List<MemoVO> selectMemoList();
	
	//작성
	public int insertMemo(MemoVO memo);   //리턴타입 의미 없어 파라미터 타입이 반영되어야 한다. 
	
	//수정
	public int updateMemo(MemoVO memo); // primary역할을 하는 키가 있어야함
	
	//삭제
	public int deleteMemo(@Param("code") int code); //시그니처가 xml를 결정한다. 이름 결정 가능 이 값을 이름이 없었어 이름이 존재하게 만듦(code) 

}
// 조회 빼고는 리턴타입 의미 없어 파라미터 타입이 반영되어야 한다.
//xml 에서 그래서 파라미터 타입으로 int를 받음 