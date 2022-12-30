package kr.or.ddit.validate;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.vo.MemberVO;

public class BeanValidationTest {
	
	private static final Logger log = LoggerFactory.getLogger(BeanValidationTest.class);
	private static Validator validator;  
	
	//객체생성  before와다르게 beforclass 딱 한번만 실행 
	@BeforeClass
	public static void setUpBeforeClass() {
		//ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		
		String messgeBaseName = "kr.or.ddit.msgs.errorMessage";  //베이스네임
		
		//메세지 번들 파일 
		ValidatorFactory factory  = Validation.byDefaultProvider()
		        .configure()
		        .messageInterpolator(
		                new ResourceBundleMessageInterpolator(
		                        new PlatformResourceBundleLocator( messgeBaseName)
		                )
		        )
		        .buildValidatorFactory();
		        
				validator = factory.getValidator();//
	}
	//validator; //한번만들어짐
	
	@Test
	public void memberVOTest() {
		MemberVO member =new MemberVO();  //비어있는객체
		
//		member.setMemId("b001");
		member.setMemBir("2000/01/01");
//		member.setMemMail("aa@mail.com");
//		member.setMemPass("ab");
//		member.setMemMileage(-1000);
		
		
		Set<ConstraintViolation<MemberVO>> constraintViolations = 
				validator.validate(member, InsertGroup.class);//모든걸 다 검증,가변파라미터(validate그룹인증)그룹이 가지고 있는 룰만 검증을하겠다
		
		constraintViolations.stream().forEach(singleViolation->{
			Path propertyPath = singleViolation.getPropertyPath();
			String errorMessage = singleViolation.getMessage();
			log.error("{} : {}",propertyPath, errorMessage);
		});
		
	}

}
