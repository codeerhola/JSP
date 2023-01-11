<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" buffer="10kb"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>인덱스jsp</title>
<!-- flush : flase 이면 a에서 b로 가기전에 플러시 시키지마 => 안전   -->
<jsp:include page="/includee/preScript.jsp" flush="false"/>
</head>
<body>
<div class="container">
	<jsp:include page='${contentPage}'/>
<jsp:include page="/includee/postScript.jsp" flush="false"/>
</body>
</html>

