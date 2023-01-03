package kr.or.ddit.mvc.annotation.resolvers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 핸들러 메소드의 인자 하나를 command objcet(클라이언트가 보내는 모든 파라미터를 받기 위한 객체)로 받을때 사용 
 *    
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ModelAttribute {
	String value() default "";

}
