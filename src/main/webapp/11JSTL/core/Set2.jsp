<%@page import="java.util.Map"%>
<%@page import="org.apache.tomcat.util.openssl.pem_password_cb"%>
<%@page import="java.util.HashMap"%>
<%@page import="common.Person"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL - set 2</title>
</head>
<body>
	<h4>List 컬렉션 이용하기</h4>
	<%
// 	Person 인스턴스를 저장할 수 있는 List를 생성
	ArrayList<Person> pList = new ArrayList<Person>();
// 	2개의 Person 인스턴스 저장
	pList.add(new Person("성삼문", 55));
	pList.add(new Person("박팽년", 60));
	%>
	
	<!-- reqeust영역에 List를 저장 -->
	<c:set var="personList" value="<%= pList %>" scope="request"/>
	<!-- 
	2개의 변수 중 0번 인덱스를 출력함. List이므로 인덱스로 접근할 수 있다.
	차후 <forEach> 태그를 사용하면 전체를 반복해서 출력할 수 있다.
	 -->
	<ul>
		<li>이름: ${ requestScope.personList[0].name }</li>
		<li>나이: ${ personList[0].age }</li>
	</ul>
	
	<h4>Map 컬렉션 이용하기</h4>
	<%
	/* 
	Map의 Key는 String, Value는 Person타입으로 정의한 후 2개의 인스턴스를 저장
	 */
	Map<String, Person> pMap = new HashMap<String, Person>();
	pMap.put("personArgs1", new Person("하위지", 65));
	pMap.put("personArgs2", new Person("이개", 67));
	%>
	<!-- request영역에 Map을 저장함 -->
	<c:set var="personMap" value="<%= pMap %>" scope="request"/>
	<!-- Map은 Key를 통해 접근하여 값을 출력함 -->
	<ul>
		<li>아이디: ${ requestScope.personMap.personArgs2.name }</li>
		<li>비번: ${ personMap.personArgs2.age }</li>
	</ul>

</body>
</html>