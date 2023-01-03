package kr.or.ddit.mvc.annotation.resolvers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 요청파라미터(request parameter) 중 특징 파라미터(value) 하나의 값을 획득하기 위한 설정. 
 * ex) @RequestParam("who") 서로 같다 request.getParameter("who")
 */
//메서드의 파라미터만 사용 
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)

public @interface RequestParam {
	
	//싱글,멀티밸류 어노테이션 사용가능 
	String value();// 요청 파라미터 값을 받음
	boolean required() default true;//필수인지 옵션인지
	String defaultValue() default "";// null값일 경우 ?

}
