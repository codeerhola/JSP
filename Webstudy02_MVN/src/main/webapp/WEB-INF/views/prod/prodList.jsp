<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>prodList</title>
</head>
<body>
<h4>PROD 목록 조회</h4>
<table>
	<thead>
		<tr>
			<th>일련번호</th>
			<th>상품분류</th>
			<th>상품명</th>
			<th>거래처명</th>
		<!-- 	<th>구매가</th>
			<th>판매가</th>
			<th>상품구매자수</th> -->
		</tr>
	</thead>
	<tbody>
		<c:set var="list" value="${pagingVO.dataList}"/>
		<c:choose>
			<c:when test="${not empty list}">
				<c:forEach items="${list}" var="prod">
					<tr>
						<td>${prod.prodId}</td>
						<td>${prod.prodLgu}</td>
						<td>${prod.prodName}</td>
						<td>${prod.prodBuyer}</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<th colspan="7">조건에 맞는 회원이 없음.</th>
				</tr>
			</c:otherwise>
		</c:choose>	
	
	</tbody>
	<!-- <tfoot>
		<tr>
			<td colspan="7">
				<div id="serachUI">				
					<select name="searchType">
						<option value>전체</option>
						<option value="LprodNm">분류명</option>
						<option value="buyerName">거래처명</option>
						<option value="prodName">상품명</option>
					</select>
					<input type="text" name="searchWord">
				</div>
			</td>
		</tr>
	</tfoot>
</table>

<h4>Hidden Form</h4> 검색 
<form id="searchForm">
   <input type="text" name="page" />
   <input type="text" name="searchType" />
   <input type="text" name="searchWord" />
</form>
 -->

</body>
</html>