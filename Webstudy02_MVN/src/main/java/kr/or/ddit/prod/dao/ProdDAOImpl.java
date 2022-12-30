package kr.or.ddit.prod.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.mybatis.MybatisUtils;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public class ProdDAOImpl implements ProdDAO{
	
	private SqlSessionFactory sqlSessionFactory = MybatisUtils.getSqlSessionFactory();
		
	@Override
		public ProdVO selectProd(String prodId) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession(); //2번
			){
			ProdDAO mapperProxy = sqlSession.getMapper(ProdDAO.class);//
				return mapperProxy.selectProd(prodId);
			}
		}
		//전체 갯수 
		@Override
		public int selectTotalRecord(PagingVO<ProdVO> pagingVO) {
			try(
					SqlSession sqlSession = sqlSessionFactory.openSession(); 
				){
				ProdDAO mapperProxy = sqlSession.getMapper(ProdDAO.class);
					return mapperProxy.selectTotalRecord(pagingVO);
			}
		}
		
		//상품리스트 
		@Override
		public List<ProdVO> selectProdList(PagingVO<ProdVO> pagingVO) {
			
			try(
					SqlSession sqlSession = sqlSessionFactory.openSession(); 
				){
				ProdDAO mapperProxy = sqlSession.getMapper(ProdDAO.class);
					return mapperProxy.selectProdList(pagingVO);
			}
		}

}
