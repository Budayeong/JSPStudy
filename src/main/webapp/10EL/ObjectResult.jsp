<%@page import="common.Person"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!-- 
	영역에 저장된 속성에 EL로 접근할 때는 속성명이 중복되지 않는다면
	EL내장객체 없이 속성명 만으로 출력할 수 있다.
	즉 requestScope.personObj.name 형태로 기술하지 않아도 됨.
	
	"속성명.멤버변수" 형태로 기술하면 클래스에 선언된 getter()를 자동으로 호출하여
	값을 출력함. 즉 아래 문장은 getter()를 호출하므로
	Person클래스에서 getName()을 주석처리하면 에러가 발생함.
	또한 출력 시 형변한이 자동으로 되므로 더욱 편리함.
	-->
	<h2>영역을 통해 전달된 객체 읽기</h2>
	<ul>
		<li>Person 객체 => 이름: ${ personObj.name },
							나이: ${ personObj.age }
		</li>
		<li>String 객체 => ${ requestScope.stringObj }</li>
		<li>Integer 객체 => ${ integerObj }</li>
	</ul>
	
	<h3>JSP 내장객체를 통해 영역의 값 읽어오기</h3>
	<%
// 	영역에는 모든 인스턴스를 저장할 수 있으므로 Obejct로 저장됨
	Object object = request.getAttribute("personObj");
// 	따라서 영역에서 꺼낸 후 원래의 타입으로 형변환 필요
	Person person = (Person) object;
// 	출력시에는 멤버변수에 직접 접근할 수 없으므로 getter 사용
	out.println("이름: " + person.getName());
// 	위 이런 모든 절차를 EL은 자동 처리해줌
	%>
	<!-- 
	파라미터로 전달된 값을 읽을 때 아래 3가지 방법을 사용할 수 있음
		param.파라미터명
		param['파라미터명']
		param["파라미터명"]
	 -->
	<h2>매개변수로 전달된 값 읽기</h2>
	<ul>
		<!-- 파라미터로 전달되는 값은 항상 String타입으로 반환됨.
		따라서 산술연산을 위해서는 반드시 숫자형식으로 변환 후 진행해야함
		하지만 EL에서는 자동으로 숫자형식으로 변환되므로 아래와 같이 즉시 연산가능  -->
		<li>${ param.firstNum + param['secondNum'] }</li>
		<!-- 파라미터를 문자형식으로 출력했으므로 "10+20"이 된다 -->
		<li>${ param.firstNum } + ${ param["secondNum"] }</li>
	</ul>
</body>
</html>