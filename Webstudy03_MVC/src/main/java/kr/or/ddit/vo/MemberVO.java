package kr.or.ddit.vo;

import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;
import java.util.List;

import javax.annotation.Generated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;  //이걸선호 
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * VO (Value Objcet) , DTO(Data Transfer Object) , JavaBean, Model 
 * 
 * JavaBean 규약
 * 1. 값을 담을 수 있는 property 정의 
 * 2. property 캡슐화 
 * 3. 캡슐화된 프로퍼티에 접근 할 수 있는 인터페이스 제공 -> getter, setter 
 *  get(set) + 프로퍼티명의 첫문자를 대문자로 -> camel case
 * 4.객체의 상태 비교 방법 제공 : equals
 * 	 ==, equals 
 * 5. 객체의 상태 확인 방법 제공 : toString  pass는 빼고 
 * 6. 객체 직렬화 가능  객체의 상태를 전송이나 저장해야해 => 직렬화  pass는 transient 
 *   @JsonIgnore직렬화,마샬링 할때 제외되는 property
 *   
 *   회원관리를 위한 Domain Layer
 *   :한사람의 회원 정보(구매기록 포함)를 담기 위한 객체. 
 *   MEMBER(1) :  PROD(N) 한사람이 여러개의 상품을 구매할 수 있다. ->  HAS MANY
 *   1:1 관계 -> HAS A 
 *   
 *   **데이터매퍼나 ORM 을 이용한 테이블 조인방법
 *   ex) 회원 정보 상세 조회시 구매 상품 기록을 함께 조회함
 *   1. 대상 테이블 선택 
 *   	member, cart (cart_member, cart_prod),prod(상품) cartvo형성 불필요 
 *   	구매기록 조호할때 언제구매했는지, 총액 을 조회해라 한다면 그 정보를 cart에서 가져와야해 이땐 vo 만들어야해 
 *   2. 각 테이블로부터 데이터를 바인딩할 vo 설계 
 *   	-MEMBERVO, PRODVO 
 *   3. 각 테이블의 관계성(Realation을 vo 사이의 has 관계로 반영)
 *   	1(main):N -> has many -> MEMBERVO has many prodVO(Collection) 
 *   	1(main):1 -> has a -> PRODBO has a BUYERVO 
 *   4. 단순바인딩이 아니기 때문에 resultType  대신 resultMap 으로 바인딩
 *   	has many : collection 
 *   	has a    :association
 *     
 */


@Data
@ToString(exclude= {"memPass","memRegno1","memRegno2"})
@EqualsAndHashCode(of="memId")
@NoArgsConstructor
public class MemberVO implements Serializable{
	
	
	public MemberVO(@NotBlank(groups = { Default.class, DeleteGroup.class }) String memId,
			@NotBlank(groups = { Default.class, DeleteGroup.class }) 
			@Size(min = 4, max = 8, groups = { Default.class, DeleteGroup.class }) String memPass) 
	{
		super();
		this.memId = memId;
		this.memPass = memPass;
	}
	
	
	private int rnum;
	
	//@NotBlank(groups= {Default.class, DeleteGroup.class})
	private String memId; //가입, 수정, 삭제할때 검증 
	
	@JsonIgnore
	@NotBlank(groups= {Default.class, DeleteGroup.class})
	@Size(min=4, max=8 ,groups= {Default.class, DeleteGroup.class})
	private transient String memPass;
	
	@NotBlank
	private String memName;
	@JsonIgnore
	private transient String memRegno1;
	@JsonIgnore
	private transient String memRegno2;
	
	@NotBlank(groups=InsertGroup.class)
	@Pattern(regexp="\\d{4}-\\d{2}-\\d{2}",groups=InsertGroup.class) //정규표현식 형식제한하는 구조 가입할때만 검증함 
	private String memBir; //date타입
	
	@NotBlank
	private String memZip;
	
	@NotBlank
	private String memAdd1;
	@NotBlank
	private String memAdd2;
	private String memHometel;
	private String memComtel;
	private String memHp;
	@Email
	private String memMail;
	private String memJob;
	private String memLike;
	private String memMemorial;
	@Pattern(regexp="\\d{4}-\\d{2}-\\d{2}") 
	private String memMemorialday;
	@Min(0)
	private Integer memMileage;
	private boolean memDelete;
	private int cartCount;
	
	private List<ProdVO> prodList;//has many 관계(1:N)
	
	private String memRole;
	
	private byte[] memImg; //db저장용
	private MultipartFile memImage;
	
	
	public void setMemImage(MultipartFile memImage) throws IOException {
		if(memImage!=null && !memImage.isEmpty()) {
			this.memImage = memImage;
			this.memImg = memImage.getBytes();
		}
	}
	
	public String getBase64MemImg() {
		if(memImg!=null)
			return Base64.getEncoder().encodeToString(memImg);
		else 
			return null;
	}
}
