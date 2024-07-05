<%@page import="oracle.net.aso.af"%>
<%@page import="model1.board.BoardDAO"%>
<%@page import="model1.board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 수정처리 전에도 로그인 되었는지 확인 -->
<%@ include file="./IsLoggedIn.jsp" %>
<%
// 폼값 받기
String num = request.getParameter("num");
String title = request.getParameter("title");
String content = request.getParameter("content");

// 폼값을 DTO인스턴스에 저장
BoardDTO dto = new BoardDTO();
dto.setNum(num);
dto.setTitle(title);
dto.setContent(content);

// DAO인스턴스 생성 및 쿼리문 실행
BoardDAO dao = new BoardDAO(application);
int affected = dao.updateEdit(dto);
// 수정이 완료되었으면 DB 연결해제
dao.close();

// 결과가 1이면 수정 성공
if(affected == 1)
// 	수정에 성공하면 수정된 내용을 보여주기위해 내용보기 페이지로 이동
	response.sendRedirect("View.jsp?num="+dto.getNum());
else 
// 	수정에 실패하면 뒤로 이동
	JSFunction.alertBack("수정하기에 실패하였습니다", out);
%>
