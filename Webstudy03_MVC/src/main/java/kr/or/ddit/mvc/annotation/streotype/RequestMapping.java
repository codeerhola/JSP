package kr.or.ddit.mvc.annotation.streotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import kr.or.ddit.mvc.annotation.RequestMethod;

/**
 * single value(GET handler), multi value
 * commandler handler 내의 핸들러 메소드에
 * 어떤 요청(주소,메소드)에 대해 동작하는지를 표현.  
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface RequestMapping {
	String value(); //속성임 
	RequestMethod method() default RequestMethod.GET;  

}
