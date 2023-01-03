package kr.or.ddit.validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;


public class ValidationUtils {
	
	private static Validator validator;
	
	//클래스가 상수메모리 로딩순간 실행	
	static {
		String messgeBaseName = "kr.or.ddit.msgs.errorMessage";  //베이스네임
		
		ValidatorFactory factory  = Validation.byDefaultProvider()
		        .configure()
		        .messageInterpolator(
		                new ResourceBundleMessageInterpolator(
		                        new PlatformResourceBundleLocator( messgeBaseName)
		                )
		        )
		        .buildValidatorFactory();
				validator = factory.getValidator();
	}
	
	//검증대상 타겟 필요
	//t통과 f 실패
	public static <T> boolean validate(T target, Map<String, List<String>> errors, Class<?>...groups) { 
		
		boolean valid = true;
		
		//constraintViolations : 검증결과 에러메세지를 가지고 있어. 
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(target, groups);
		
		//벨리드한지 여부 결정  
		valid = constraintViolations.isEmpty(); //아무것도 없을때
		
		if(!valid) {
			constraintViolations.stream().forEach(single->{  //이 안에서 메세지 마들어줘
				String propertyName = single.getPropertyPath().toString(); //돌아오는 녀석을 키로 잡아야해
				String errorMessage = single.getMessage();
				
				//먼저 꺼내와 에러메시지가 있는지 없는지 
				List<String> errorMessages = Optional.ofNullable
						(errors.get(propertyName)).orElse(new ArrayList<>());
				errorMessages.add(errorMessage);
				
				errors.put(propertyName, errorMessages); //				
			});
		}
		return valid;
	}

}
