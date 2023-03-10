package kr.or.ddit.mvc.annotation.streotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * Marker annotation 형태로 
 * Front controller 다음에서 동작한 command handler 객체 표현. 실제값은 없음  
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {

}
