<%@page import="oracle.net.aso.c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%
/* 
하루동안 열지않음 체크박스를 체크한 후
닫기버튼을 누르면 값 1이 파라미터로 전송됨.
*/
String chkVal = request.getParameter("inactiveToday");

// 파라미터가 1이라면...
if (chkVal != null && chkVal.equals("1")){
// 	생성자를 통해 쿠키 생성
	Cookie cookie = new Cookie("PopupClose", "off");
// 	경로(컨텍스트루트)와 유지시간(하루) 설정
	cookie.setPath(request.getContextPath());
	cookie.setMaxAge(60*60*24);
// 	응답헤더를 통해 생성된 쿠키를 클라이언트로 전송
	response.addCookie(cookie);
// 	콜백 데이터 웹브라우저에 출력
	out.println("쿠키: 하루 동안 열지 않음");
}
%>