package kr.or.ddit.prod.service;

import java.util.List;

import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public interface ProdService {
	
	/**
	 * @param prodId
	 * @return 존재하지 않는 경우, RuntimeException 발생
	 */
	public ProdVO retriveProd(String prodId);

	/**
	 * call by reference 구조에 따라 totalRecord의 dataList 를 pagingVO에 넣어줌
	 * @param pagingVO
	 */
	public void retriveProdList(PagingVO<ProdVO> pagingVO);
}
