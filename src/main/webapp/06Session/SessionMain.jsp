<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 날짜 서식 지정 (시:분:초)
SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");  

// 세션이 생성된 시간
long creationTime = session.getCreationTime();  
String creationTimeStr = dateFormat.format(new Date(creationTime));

// 마지막으로 세션에 접근한 시간
long lastTime = session.getLastAccessedTime(); 
String lastTimeStr = dateFormat.format(new Date(lastTime));
// 위 2개의 시간을 서식을 지정해서 문자열로 변경

// session 내장객체를 통해 세션 유지시간 설정 (초단위)
// session.setMaxInactiveInterval(60*20);
%>
<html>
<head><title>Session</title></head>
<body>
    <h2>Session 설정 확인</h2>
    <ul>
        <li>세션 유지 시간 : <%= session.getMaxInactiveInterval() %></li>
        <li>세션 아이디 : <%= session.getId() %></li>
        <li>최초 요청 시각 : <%= creationTimeStr %></li>
        <li>마지막 요청 시각 : <%= lastTimeStr %></li>
    </ul>
</body>
</html>

