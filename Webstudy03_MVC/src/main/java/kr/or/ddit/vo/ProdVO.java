package kr.or.ddit.vo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.Part;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Insert;

import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 상품 하나의 정보(분류명, 거래처 정보 포함)를 담기위한 객체
 *  PROD(1) : BUYER(1) -> has a
 *  
 */
@Data
@EqualsAndHashCode(of="prodId")
@ToString(exclude="prodDetail")
public class ProdVO implements Serializable{
	private int rnum;
	@NotBlank(groups=UpdateGroup.class) //업데이트그룹이 디폴트 상속 
	private String prodId;
	
	@NotBlank(groups=InsertGroup.class)
	private String prodName;
	
	@NotBlank@NotBlank(groups=InsertGroup.class)
	private String prodLgu;
	private String lprodNm; //lprodNm만 넣어줄수 있는 property 추가 
	
	@NotBlank@NotBlank(groups=InsertGroup.class)
	private String prodBuyer;
	private BuyerVO buyer; // //has a -> 값만 가져오는게 아니야 
	
	@NotNull
	@Min(0)
	private Integer prodCost;
	@NotNull
	@Min(0)
	private Integer prodPrice;
	@NotNull
	@Min(0)
	private Integer prodSale;
	@NotBlank
	private String prodOutline;
	private String prodDetail;
	
	@NotBlank(groups=InsertGroup.class)
	private String prodImg; //prod table 조회용 프로퍼티 
	
	private MultipartFile prodImage; //클라리언트가 보내주는 이미지를 받기 위한 프로퍼디2.x , 3.x part 둘다 받을 수 있는 Interface MultipartFile 
	
	//상품의 이미지가 업로드 되는 순간 메타데이터로 만들어 버림 
	public void setProdImage(MultipartFile prodImage) {
		//상품이 들어온다
		if(prodImage!=null && !prodImage.isEmpty()
				&& prodImage.getContentType().startsWith("image/")) {
			this.prodImage = prodImage;
			this.prodImg = UUID.randomUUID().toString();//db와커뮤니케이션
		}
	}
	//저장할 이름, 
	public void saveTo(File saveFolder) throws IOException {
		if(prodImage!=null&& prodImg==null) return;
		
		//저장함
		File saveFile = new File(saveFolder, prodImg);
		prodImage.transferTo(saveFile);
	}
	
	
	@NotNull
	@Min(0)
	private Integer prodTotalstock;
	
	private String prodInsdate;
	@NotNull
	@Min(0)
	private Integer prodProperstock;
	
	private String prodSize;
	private String prodColor;
	private String prodDelivery;
	private String prodUnit;
	private Integer prodQtyin;
	private Integer prodQtysale;
	private Integer prodMileage;
	
	private Set<MemberVO> memberSet; // has many
	
	private int memCount; //스칼라 커리 사용 has관계 형성 불필요 memCount 값만 가져올거야  
}


