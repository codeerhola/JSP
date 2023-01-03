package kr.or.ddit.prod.service;

import java.util.List;

import kr.or.ddit.prod.dao.ProdDAO;
import kr.or.ddit.prod.dao.ProdDAOImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public class ProdServiceImpl implements ProdService {
	
	
	private ProdDAO prodDAO = new ProdDAOImpl();
	

	@Override
	public ProdVO retriveProd(String prodId) {
		ProdVO prod = prodDAO.selectProd(prodId);
		if(prod==null)
			throw new RuntimeException(String.format("%s 는 없는 상품", prodId));
		return prod;
	}
	
	//리스트출력 pagingVO에 모든 정보가 담겨있어 List로 반환 필요 없음
	//call by reference 
	
	@Override
	public void retriveProdList(PagingVO<ProdVO> pagingVO) {
		
		int totalRecord = prodDAO.selectTotalRecord(pagingVO);
		
		pagingVO.setTotalRecord(totalRecord);
		
		List<ProdVO> dataList = prodDAO.selectProdList(pagingVO);
		pagingVO.setDataList(dataList);
		
	}
}
