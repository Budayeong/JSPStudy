<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>표현 언어(EL) - 내장 객체</title>
</head>
<body>
	<h2> ImplicitForwardResult 페이지</h2>
	<h3> 각 영역에 저장된 속성 읽기</h3>
	<!--
	포워드하면 page영역의 데이터는 소멸,
	reqeust 영역은 공유됨.
	아래에서 page영역 부분에 아무값도 출력되지않음
	 -->
	<ul>
		<li> 페이지 영역 : ${ pageScope.scopeValue }</li>
		<li> 리퀘스트 영역 : ${requestScope.scopeValue}</li>
		<li> 세션 영역 : ${ sessionScope.scopeValue }</li>
		<li> 애플리케이션 영역 : ${applicationScope.scopeValue }</li>
	</ul>
	
	<!-- 표현식으로 출력하는 경우에는 null이 화면에 그대로 출력됨  -->
	<h3> JSP 코드로 출력하기 (표현식)</h3>
	<%= pageContext.getAttribute("scopeValue")%>
	
	<!-- 
	포워드 된 페이지에서는 page영역이 소멸되므로 reqeust영역이 가장 좁은 영역이 됨.
	아래에선 request영역의 값이 출력된다.
	 -->
	<h3> 영역 지정 없이 속성 읽기</h3>
	<ul>
		<li> ${scopeValue} </li>
	</ul>
</body>
</html>