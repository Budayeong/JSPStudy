<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>한 번의 매핑으로 여러 가지 요청 처리하기</h3>
	${ resultValue }
	<ol>
		<li>URI: ${ uri }</li>
		<li>요청명: ${ commandStr }</li>
	</ol>
	<!-- 
	요청명은 일괄적으로 .one으로 끝나는 형태로 생성한 후 링크를 걸어줌
	이처럼 요청명(url-pattern)은 자유롭게 네이밍 가능
	 -->
	<ul>
		<li><a href="../12Servlet/regist.one">회원가입</a></li>
		<li><a href="../12Servlet/login.one">로그인</a></li>
		<li><a href="../12Servlet/freeboard.one">자유게시판</a></li>
	</ul>
	<!-- 
	해당 요청명은 web.xml을 사용하지 않고 어노테이션을 통해 매핑.
	 -->
	
</body>
</html>