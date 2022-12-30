package kr.or.ddit.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.MybatisUtils;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;

public class MemberDAOImpl  implements MemberDAO {
	
	//1번 특정레이어는 또다른 레이어에 관계가 필요하다 
	private SqlSessionFactory sqlSessionFactory = MybatisUtils.getSqlSessionFactory();
	

	@Override
	public int insertMember(MemberVO member) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession(); //2번
			){
				MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);//
				int cnt = mapperProxy.insertMember(member);
				sqlSession.commit();
				 return cnt;
		}
	}
	
	@Override
	public int selectTotalRecord(PagingVO<MemberVO> pagingVO) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession(); //2번
			){
				MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);//
				return mapperProxy.selectTotalRecord(pagingVO);
		}
	}
	@Override
	public List<MemberVO> selectMemberList(PagingVO<MemberVO> pagingVO) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession(); //2번
			){
				MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);//
				return mapperProxy.selectMemberList(pagingVO);
		}
	}


	@Override
	public MemberVO selectMember(String memId) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
			MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);//
			return mapperProxy.selectMember(memId);
		}
	}

	@Override
	public int updateMember(MemberVO member) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
				MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
				int cnt = mapperProxy.updateMember(member);
				sqlSession.commit();
				return cnt;
		}
	}

	@Override
	public int deleteMember(String memId) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
				MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class); //MemberDAO의 구현체 mapperProxy 
				int cnt = mapperProxy.deleteMember(memId);
				sqlSession.commit();
				return cnt;
		}
	}




}
