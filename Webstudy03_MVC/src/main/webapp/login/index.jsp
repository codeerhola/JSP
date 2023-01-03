<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<title>Insert title here</title>

<c:choose>
	<c:when test="${not empty sessionScope.authMember }">
		<form name="logoutForm" action= "<c:url value='/login/logout.do'/>" method="post"></form>
		<a  href="#" class="logoutBtn">${authMember.memId}님 로그아웃</a>
	
		 <script>
				$(".logoutBtn").on("click",function(evnet){
					event.preventDefault();
					document.logoutForm.submit();
					return false;
				});
		</script>	
	</c:when>
</c:choose>

