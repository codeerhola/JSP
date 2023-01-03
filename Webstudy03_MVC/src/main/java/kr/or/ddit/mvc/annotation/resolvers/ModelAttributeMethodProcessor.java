package kr.or.ddit.mvc.annotation.resolvers;

import java.io.IOException;
import java.lang.reflect.Parameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;


/**
 * @ModelAttribute 어노테이션을 가진 command objcet(not 기본형) 인자 하나를 배포.
 * ex) @ModelAttribute MembeVO member로 사용하고 싶어 
 * 	  @ModelAttribute   int cp; 로 쓰려고 하는거 아냐 걸러 내야해 			  
 *
 */
public class ModelAttributeMethodProcessor implements HandlerMethodArgumentResolver {

	
	//1. 어노테이션 갖고 있어? 2.기본형인지 아닌지
	@Override
	public boolean supportsParameter(Parameter parameter) {
	
		Class<?> parameterType = parameter.getType();
		
		ModelAttribute modelAttribute = parameter.getAnnotation(ModelAttribute.class);
		boolean support = modelAttribute!=null
				&&
				!(
				
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
		ModelAttribute modelAttribute = parameter.getAnnotation(ModelAttribute.class);
		
		try {
			Object commandObject = parameterType.newInstance();
			
			
//			req.setAttribute("member", member);
			String attrName = modelAttribute.value();
			//COc(Convention over Configuration)
			if(StringUtils.isBlank(attrName)) {
				attrName = CaseUtils.toCamelCase(parameterType.getSimpleName(), true, ' ');
				
			}
			req.setAttribute(attrName, commandObject);
			
			
//			MemberVO member = new MemberVO();

			
//			Map<String, String[]> parameterMap = req.getParameterMap();
//			
//			try {
//				BeanUtils.populate(member, parameterMap);
//			} catch (IllegalAccessException | InvocationTargetException e) {
//				throw new ServletException(e);
//			}
			BeanUtils.populate(commandObject, req.getParameterMap());
			return commandObject;
		}catch (Exception e) {
			throw new RuntimeException(e);
			
		}
	}
}


