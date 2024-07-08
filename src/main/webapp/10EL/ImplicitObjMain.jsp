<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
// 4가지 영역에 동일한 속성명으로 각기 다른값 저장
pageContext.setAttribute("scopeValue", "페이지 영역");
request.setAttribute("scopeValue", "리퀘스트 영역");
session.setAttribute("scopeValue", "세션 영역");
application.setAttribute("scopeValue", "애플리케이션 영역");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>표현 언어(EL) -  내장객체</title>
</head>
<body>
	<h2>ImplicitObjMain 페이지</h2>
	
	<!-- 
	4가지 영역에 접근할때는 EL의 내장객체를 통해 내용을 출력함.
	모두 동일한 패턴으로 "영역명Scope"와 같은 형태로 되어있음.
	EL의 내장객체에 .을 추가해 속성명을 기술함.
	-->
	<h3>각 영역에 저장된 속성 읽기</h3>
	<ul>
		<li> 페이지 영역: ${ pageScope.scopeValue } </li>
		<li> 리퀘스트 영역 : ${ requestScope.scopeValue } </li>
		<li> 세션 영역: ${ sessionScope.scopeValue }</li>
		<li> 애플리케이션 영역 : ${ applicationScope.scopeValue }</li>
	</ul>
	
	<!-- 
	영역명 지정없이 속성을 읽을때는 가장 좁은 영역을 우선으로
	출력함. 즉, 여기서는 page영역의 속성을 출력.
	실무에서 동일한 속성명으로 저장하는 경우가 거의 없으므로 대부분 
	이와같이 영역명 없이 사용 가능
	 -->
	<h3> 영역 지정 없이 속성 읽기</h3>
	<ul>
		<li> ${ scopeValue }</li>
	</ul>

	<!-- 
	포워드르 사용할때는 JSP코드보다는 액션태그가 더 편리.
	포워드란 현재 페이지가 받은 요청을 그 다음 페이지로 전달하는 것.	
	즉 요청을 공유하는 개념이므로 request영역이 공유됨.
	 -->	
	<jsp:forward page="ImplicitForwardResult.jsp"></jsp:forward>
	<%--
	JSP를 통한 포워드 방법
	request.getRequestDispatcher("ImplicitForwawrdResult.jsp").forward(request, response);
	--%>
</body>
</html>