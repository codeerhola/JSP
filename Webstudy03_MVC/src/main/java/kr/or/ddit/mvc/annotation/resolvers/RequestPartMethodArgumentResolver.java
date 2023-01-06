package kr.or.ddit.mvc.annotation.resolvers;

import java.io.IOException;
import java.lang.reflect.Parameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.mvc.multipart.MultipartHttpServletRequest;

public class RequestPartMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(Parameter parameter) {
		Class<?> parameterType = parameter.getType();
		RequestPart requestPart = parameter.getAnnotation(RequestPart.class);
		boolean support = requestPart!=null //어노테이션 있으면서 멀티파트 타입이면 
							&&
						MultipartFile.class.equals(parameterType);	
		return support;
	}

	@Override
	public Object resolveArgument(Parameter parameter, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Class<?> parameterType = parameter.getType();
		RequestPart requestPart = parameter.getAnnotation(RequestPart.class);
		//멀티파트 리퀘스트
		
		if(req instanceof MultipartHttpServletRequest) {
			//멀티파트 데이터를 꺼냄
			MultipartFile file = ((MultipartHttpServletRequest) req).getFile(requestPart.value());
			if(requestPart.required()&& (file==null|| file.isEmpty())) {
				throw new RequestParamMethodArgumentResolver.BadRequestException("필수 파일 누락");
			}
			return file;
		}else {
			throw new RequestParamMethodArgumentResolver.BadRequestException("멀티 파트 요청이 아님 ");
		}
		
	}

}
