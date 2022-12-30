package kr.or.ddit.mybatis;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

//config mxl 역할 끝남 
public class MybatisUtils {
	private static SqlSessionFactory sqlSessionFactory;
	
	//딱한버만 실행 
	static {
		String configPath = "kr/or/ddit/mybatis/mybatis-config.xml"; //위치필요
		try(
			Reader reader = Resources.getResourceAsReader(configPath); //입력스트림 얘를통해 세션팩토리 빌트 
				
		) {
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader); //싱글톤으로 sqlSessionFactory 생성 
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
	}
	
	
	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

}
