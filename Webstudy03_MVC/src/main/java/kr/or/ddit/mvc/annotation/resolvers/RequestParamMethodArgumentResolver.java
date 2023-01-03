package kr.or.ddit.mvc.annotation.resolvers;

import java.io.IOException;
import java.lang.reflect.Parameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;


/**
 * 전제조건 : 어노테이션 하나를 가지고 있어야 한다
 * @RequestParam을 가지고 있으며, 기본형 타입인 인자를 해결.  
 *
 */

public class RequestParamMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(Parameter parameter) {
		Class<?> parameterType = parameter.getType();
		RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
		boolean support = requestParam !=null
				&&
			(
				//기본형이거나 String 타입이면 
				parameterType.isPrimitive()
				||
				String.class.equals(parameterType)
				||
				parameterType.isArray() 
				&&
					(
					parameterType.getComponentType().isPrimitive() 
					||
					parameterType.getComponentType().equals(String.class)
					)
						
		);
		return support;
	}

	@Override
	public Object resolveArgument(Parameter parameter, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Class<?> parameterType = parameter.getType();
		RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
		
		String requestParameterName = requestParam.value();
		boolean required = requestParam.required();
		String defaultValue = requestParam.defaultValue();
		
		String[] requestparameterValues = req.getParameterValues(requestParameterName);
		if(required && (requestparameterValues==null || requestparameterValues.length==0
				|| StringUtils.isBlank(requestparameterValues[0])

				)) { //꼭있어야하는파라미터 
			throw new BadRequestException(requestParameterName + "필수 파라미터 누락"); //unchecked
		}
		
		if(requestparameterValues==null || requestparameterValues.length==0 
				|| StringUtils.isBlank(requestparameterValues[0])) {
			requestparameterValues = new String[] {defaultValue};
		}
		
		Object argumentObject = null;
		if(parameterType.isArray()) {
			Object[] argumentObjects = new Object[requestparameterValues.length];
			for(int i=0; i<argumentObjects.length; i++) {
				argumentObjects[i] = 
					singleValueGenerate(parameterType.getComponentType(), requestparameterValues[i]);
			}
			argumentObject = argumentObjects;
			
		}else {
			argumentObject = singleValueGenerate(parameterType, requestparameterValues[0]);
		}
		
		
		return argumentObject;
	}
	private Object singleValueGenerate(Class<?> singleValueType, String requestParameter ) {
		Object singleValue = null;
		
		if(int.class.equals(singleValueType)) {
			singleValue = Integer.parseInt(requestParameter);
			
		}else if(boolean.class.equals(singleValueType)) {
			singleValue = Boolean.parseBoolean(requestParameter);
		}else {
			singleValue = requestParameter;
		}
		return singleValue;
	}
	
	public static class BadRequestException extends RuntimeException{
		public BadRequestException(String message) {
			super(message);
		}
		
	}

}
