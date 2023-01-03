<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>500에러페이지</h4>

	<%
		ErrorData ed = pageContext.getErrorData();
		out.println(ed.getStatusCode()); //에러 상태 코드 
		out.println(ed.getRequestURI()); //어떤요청에서 에러가 발생했는지?
		out.println(ed.getThrowable()); //원인정보 ex)java.io.IOException: java.io.IOException: 강제발생예외
	%>


</body>
</html>