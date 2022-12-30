package kr.or.ddit.vo;

import static org.junit.Assert.*;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PagingVOTest {

	@Test
	public void test() {
		PagingVO pagingVo = new PagingVO();
		
		pagingVo.setTotalRecord(108);
		pagingVo.setCurrentPage(3);
		log.info("paging: {}", pagingVo);
		
		pagingVo.setCurrentPage(7);
		log.info("paging: {}", pagingVo);
		
		
	}

}
