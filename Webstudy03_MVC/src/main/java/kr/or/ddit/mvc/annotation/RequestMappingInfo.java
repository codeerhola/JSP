package kr.or.ddit.mvc.annotation;

import java.lang.reflect.Method;

import org.apache.ibatis.annotations.Mapper;

import lombok.Getter;
import lombok.ToString;

//수정불가 객체 setter없음 
//equals 없음 이건 나중에 request mappnig handler Mapping의 벨류값이야 키값이 아냐! 그래서 이퀄스 불필요 

 
@Getter
@ToString
public class RequestMappingInfo {
	//핸들러 메소드에 대한 하나의 정보를 가지고 있어
	private RequestMappingCondition mappingCondition;
	private Object commandHandler; //objcet 타입 
	private Method handlerMethod; //메소드 정보
	
	public RequestMappingInfo(RequestMappingCondition mappingCondition, Object commandHandler, Method handlerMethod) {
		super();
		this.mappingCondition = mappingCondition;
		this.commandHandler = commandHandler;
		this.handlerMethod = handlerMethod;
	}
}
