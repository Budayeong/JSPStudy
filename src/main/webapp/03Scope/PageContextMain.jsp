<%@page import="common.Person"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
/* 
page영역
	: 하나의 페이지에서만 영역이 공유되고 페이지이동, 포워드 된 페이지에서는
	영역이 소멸되어 공유되지 않는다. 즉 해당 페이지에서만 사용할 수 있는 영역.
	JSP에서의 영역은 데이터를 저장하는 공간으로 메모리라고 생각하면 됨.
 */
 
// 정수형 데이터 (Integer)
pageContext.setAttribute("pageInteger", 1000);
// 문자열(String) 데이터
pageContext.setAttribute("pageString", "페이지 영역의 문자열");
// 개발자 지정 클래스 인스턴스
pageContext.setAttribute("pagePerson", new Person("한석봉",99));
/* 
	위에서는 3가지 형태의 인스턴스를 page영역에 저장함.
	저장시 setAttribute(속성명, 속성값) 메서드를 사용.
	영역은 Object 기반으로 데이터를 저장하므로 모든 인스턴스를 
	저장 할 수 있다.
*/
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>page 영역</title>
</head>
<body>
	<h2> page 영역의 속성값 읽기 </h2>
	<%
// 	정수 데이터를 읽을 때는 Integer를 통해 자동 언박싱
	int pInteger = (Integer)(pageContext.getAttribute("pageInteger"));
// 	문자열 데이터는 toString()을 통해 문자열 형태로 반환가능
	String pString = pageContext.getAttribute("pageString").toString();
// 	개발자 정의 클래스는 원래 타입으로 형변환
	Person pPerson = (Person)(pageContext.getAttribute("pagePerson"));
	/* 
 		영역에 저장된 데이터는 getAttribute(속성명) 으로 읽어올 수 있다.
 		Object형으로 자동형변환되어 저장되므로 값을 읽을 때는 원래의 타입으로
 		다운 캐스팅(강제형변환) 후 사용해야함
	*/
	%>
	<ul>
		<!-- Java의 기본클래스는 별도 처리 없이 즉시 내용 출력 가능 -->
		<li>Integer 객체 : <%= pInteger %> </li>
		<li>String 객체 : <%= pString %> </li>
		<!-- getter를 통해 내용 출력 -->
		<li>Person 객체 : <%= pPerson.getName() %>, <%= pPerson.getAge() %> </li>
	</ul>
	
	<!-- 
	지시어를 통한 include는 포함시킬 페이지의 원본소스를 그대로 현재 문서에 포함시킨 후 컴파일을 진행함.
	따라서 동일한 페이지로 취급하므로 page영역에 그대로 유지됨.
	즉, 인클루드 처리 페이지의 내용은 정상적 출력
	-->
	<h2> include된 파일에서 page 영역 읽어오기</h2>
	<%@ include file = "PageInclude.jsp" %>
	
	
	<!-- 
	페이지 이동은 새로운 페이지를 웹서버에 요청하는 것이므로 page영역은
	공유되지 않는다. 즉, 아래의 링크를 통해 이동한 페이지에서는 아무런 내용도 출력되지 않는다.
	-->
	<h2> 페이지 이동 후 page 영역 읽어오기 </h2>
	<a href="PageLocation.jsp">PageLocation.jsp 바로가기</a>

</body>
</html>