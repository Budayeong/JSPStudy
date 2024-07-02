<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.LocalDate" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 
HTML주석 (소스보기 했을 때 화면에 출력됨) 

보통의 경우 인클루드 되는 JSP파일은 HTML태그없이 순수한 JSP코드만 작성하는 것이 좋다
포함되었을때 HTML태그가 중복되기 때문이다
-->
<%-- 
JSP주석 (소스보기 했을 때 화면에 출력되지않음)

포함되는 페이지를 만들때 반드시 page 지시어가 있어야함
page지시어가 없는 jsp파일은 500에러 발생
 --%>
 
 
<%
// 오늘날짜
LocalDate today = LocalDate.now();
// 내일날짜
LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
%>
