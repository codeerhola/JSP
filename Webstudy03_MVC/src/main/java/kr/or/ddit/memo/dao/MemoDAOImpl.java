package kr.or.ddit.memo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.MybatisUtils;
import kr.or.ddit.vo.MemoVO;

public class MemoDAOImpl implements MemoDAO {
	
	//마바를 통해서 사용할수 있어야헤
	private SqlSessionFactory sqlSessionFactory = MybatisUtils.getSqlSessionFactory();
	
	//마이바티스 스캐너 
	
	
	@Override
	public List<MemoVO> selectMemoList() {
		//1.세션 오픈
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class); //가짜 구현체인 proxy형태로 만듦
			return mapperProxy.selectMemoList();
			//return sqlSession.selectList("kr.or.ddit.memo.dao.MemoDAO.selectMemoList"); //어떤쿼리필요? id가 키가 됨 =>  memoDAO 이름 카피. 메서드명 
		}
	}
	@Override
	public int insertMemo(MemoVO memo) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession(); //트랜잭션 시작 
			){
				MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class); 
				int rowcnt = mapperProxy.insertMemo(memo); //memo 꼭 남겨야해   
				//int rowcnt = sqlSession.insert("kr.or.ddit.memo.dao.MemoDAO.insertMemo", memo); 
				sqlSession.commit(); //트랜잭션 종료 
				return rowcnt;
			}
	}
	@Override
	public int updateMemo(MemoVO memo) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
				MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class);//인터페이스 정보 제공
			    int rowcnt = mapperProxy.updateMemo(memo);
				//int rowcnt =  .update("kr.or.ddit.memo.dao.MemoDAO.updateMemo",memo);
				sqlSession.commit();
				return rowcnt;
		
		}
	}

	@Override
	public int deleteMemo(int code) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
				MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class);//인터페이스 정보 제공
				int rowcnt = mapperProxy.deleteMemo(code);
				//int rowcnt =  sqlSession.delete("kr.or.ddit.memo.dao.MemoDAO.deleteMemo",code);
				sqlSession.commit();
				return rowcnt;
		
		}
	}
}
