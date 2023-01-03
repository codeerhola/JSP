package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 상품하나의 정보(분류명,거래처정보포함)를 담기위한 객체
 *  엔터티의 릴레이션 반영 
 *  PROD 
 * relation관계 반영 PROD(1) : BUYER(1) -> has a :    
 */

@Data
@EqualsAndHashCode(of="prodId")
@ToString(exclude="prodDetail")
public class ProdVO implements Serializable{
	
	//rownum 
	private int rnum;
	

	private String prodId;
	private String prodName;
	
	private String prodLgu; //
	private String lprodNm; //lprodNm만 넣어줄수 있는 property 추가 
	
	private String prodBuyer;
	
	private BuyerVO buyer; //has a -> 값만 가져오는게 아니야 
	
	private Integer prodCost;
	private Integer prodPrice;
	private Integer prodSale;
	private String prodOutline;
	private String prodDetail;
	private String prodImg;
	private Integer prodTotalstock;
	private String prodInsdate;
	private Integer prodProperstock;
	private String prodSize;
	private String prodColor;
	private String prodDelivery;
	private String prodUnit;
	private Integer prodQtyin;
	private Integer prodQtysale;
	private Integer prodMileage;
	
	
	private Set<MemberVO> memberSet; // has many 관계 
	
	private int memCount; //스칼라 커리 사용 has관계 형성 불필요 memCount 값만 가져올거야  

}
