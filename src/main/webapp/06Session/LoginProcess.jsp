<%@page import="membership.MemberDAO"%>
<%@page import="membership.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 로그인 폼에서 전송(submit)한 폼값을 받는다
String userId = request.getParameter("user_id");
String userPwd = request.getParameter("user_pw");

// appplication 내장객체를 이용해서 web.xml에 등록한 접속정보를 읽어옴
String oracleDriver = application.getInitParameter("OracleDriver");
String oracleURL = application.getInitParameter("OracleURL");
String oracleId = application.getInitParameter("OracleId");
String oraclePwd = application.getInitParameter("OraclePwd");

// 위 4개의 정보를 인수로 DAO 인스턴스를 생성. 여기서 DB연결이 완료됨
MemberDAO dao = new MemberDAO(oracleDriver, oracleURL, oracleId, oraclePwd);
// 입력받은 아이디, 패스워드를 인수로 인증을 위한 메서드 호출
// getMemberDTO에서 입력받은 아이디, 패스워드의 값이 DB에 존재하면 DTO객체에 set함. 
MemberDTO memberDTO = dao.getMemberDTO(userId, userPwd);
dao.close();

// 만약 DTO 객체에 아이디가 저장되어 있다면 로그인에 성공한 것
if(memberDTO.getId() != null){
// 	세션 영역에 회원의 아이디와 이름을 저장
	session.setAttribute("UserId", memberDTO.getId());
	session.setAttribute("UserName", memberDTO.getName());
	/* 
	세션영역에 저장된 속성값은 페이지를 이동하더라도 유지되므로 로그인 
	페이지로 이동함. 그리고 웹브라우저를 완전히 닫을때까지 저장된 정보는 유지됨
	*/
	response.sendRedirect("LoginForm.jsp");
}
else {
	/* 
	로그인에 실패한 경우에는 request영역에 에러메세지를 저장한 후
	로그인 페이지로 포워드함. request영역은 포워드 한 페이지까지
	데이터를 공유함.
	*/
	request.setAttribute("LoginErrMsg", "로그인 오류입니다");
	request.getRequestDispatcher("LoginForm.jsp").forward(request, response);
}

%>

