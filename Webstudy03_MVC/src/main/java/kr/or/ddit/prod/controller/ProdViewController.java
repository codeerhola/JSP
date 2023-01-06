package kr.or.ddit.prod.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * 상품 상세 조회시, 해당 거래처의 모든 정보 함께 조회함.
 *  상품 상세 조회시, 구매자 리스트(회원아이디, 회원명, 휴대폰번호, 이메일, 마일리지) 함께 조회. 
 * 분류명도 함께 조회함. 
 */
@WebServlet("/prod/prodView.do")
public class ProdViewControllerServlet extends HttpServlet{

}
