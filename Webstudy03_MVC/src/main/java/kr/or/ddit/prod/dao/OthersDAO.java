package kr.or.ddit.prod.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.BuyerVO;

/**
 * 상품 분류 선택 UI 외 거래처 선택 UI 완성을 위한 데이터 조회.
 *
 */
public interface OthersDAO {
	
	
	public List<Map<String, Object>> selectLprodList(); //특정vo를 지칭하지 않음, 
	
	
	//특정분류에 해당하는것만 쪼개서
	public List<BuyerVO> selectBuyerList(@Param("buyerLgu") String buyerLgu); 

}
