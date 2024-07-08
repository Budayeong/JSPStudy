<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="common.Person"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>표현 언어(EL) - 컬렉션</title>
</head>
<body>
	<h2>List 컬렉션</h2>
	<%
// 	List를 Object 기반으로 생성
	List<Object> aList = new ArrayList<Object>();
// 	타입매개변수가 Obejct인 경우 아래와 같이 생략 가능
// 	List aList2 = new ArrayList();

// 	String 인스턴스 추가
	aList.add("청해진");
// 	Person 인스턴스 추가
	aList.add(new Person("장보고", 28));
// 	page영역에 List를 저장
	pageContext.setAttribute("Ocean", aList);
	%>
	
	<ul>
		<!-- 첫번째는 String 인스턴스가 출력됨. List는 배열의 특성을 가지므로
		인덱스로 접근할 수 있다.  -->
		<li>0번째 요소: ${ Ocean[0] }</li>
		<!-- Person 인스턴스를 출력. 멤버변수명을 통해 getter()를 호출하여 출력함.
		만약 getter()를 정의하지 않으면 에러가 발생함.  -->
		<li>1번째 요소: ${ Ocean[1].name }, ${ Ocean[1].age }</li>
		<!-- 2번 인덱스에는 아무런 값도 없으므로 출력되지 않는다. 
		Java라면 예외가 발생하지만 EL에서는 예외가 발생하지 않는다. -->
		<li>2번째 요소: ${ Ocean[2] }</li>
	</ul>
	<h2>Map 컬렉션</h2>
	<%
// 	Map을 생성한다. Key와 Value 모두 String으로 선언
	Map<String, String> map = new HashMap<String, String>();
// 	한글을 Key로 설정해 값 추가
	map.put("한글", "훈민정음");
// 	영어를 Key로 사용
	map.put("Eng", "English");
// 	page영역에 저장한다
	pageContext.setAttribute("King", map);
	%>
	
	<ul>
		<!-- key값이 영문인 경우에는 아래 3가지방법 모두를 사용가능  -->
		<li>영문 Key: ${ King["Eng"] }, ${ King['Eng'] }, ${ King.Eng }</li>
		<!-- key값이 한글인 경우에는 .을 사용불가 -> 파싱에러 -->
		<li>한글 Key: ${ King["한글"] }, ${ King['한글'] }, \${ King.한글 }</li>
		<!-- 
		EL 식 앞에 \ 을 붙이면 주석이 됨.
		단, 코드가 숨겨지는 것은 아니고 화면에 작성한 그대로 출력됨.
		코드가 실행되지 않는 것.
		 -->
	</ul>

</body>
</html>