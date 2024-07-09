<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- JSTL 최신버전 지시어  -->
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!-- JSTL 이전 버전 지시어 -->
<%-- <%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<!--
국제화 태그 (Formatting) 태그
: 국가별로 다양한 언어, 날짜, 시간, 숫자형식을 설정할 때 사용.
접두어는 fmt로 사용.
-->
<body>
	<h4>숫자 포맷 설정</h4>
	<!-- 변수 생성 -->
	<c:set var="number1" value="12345"/>
	<!-- 
	groupingUsed: 세자리마다 콤마를 출력. 디폴트값이 true이므로 콤마를
		출력하고 싶지 않다면 false를 지정
	-->
	콤마 O : <fmt:formatNumber value="${ number1 }"/><br />
	콤마 X : <fmt:formatNumber value="${ number1 }" groupingUsed="false"/><br />
	
	<!-- 
	type: currency인 경우 현재 통화기호로 출력.
	var속성 지정 시 즉시 출력되지 않고 원하는 위치에 EL을 통해 출력됨.
	currencySymbol 속성으로 통화기호 변경가능.
	 -->
	 <fmt:formatNumber value="${number1 }" type="currency" var="printNum2"/>
	 통화기호: ${ printNum2 }
	 <!-- value 속성값을 %단위로 변환해 출력. 즉 100을 곱한 결과에 %기호까지 붙여줌 -->
	<fmt:formatNumber value="0.03" type="percent" var="printNum2"/>
	퍼센트 : ${ printNum2 }
	
	<!-- 
	Integer.parseInt() 와 동일한 기능으로 문자열을 숫자로 변경.
	pattern: 반환할 문자열의 패턴을 지정해 파싱
	integerOnly: 소수점 이하를 절삭해 정수부만 출력
	 -->
	<h4>문자열을 숫자로 변경</h4>
	<!-- 
	pattern으로 주어진 부분을 분석해 문자열을 숫자형식으로 파싱함.
	해당 속성값으로
		000,000.00 사용 시 정상
		0,0.0 사용시 정상
		,. 사용 시 에러발생
	 -->
	<c:set var="number2" value="6,789.01"/>
	<fmt:parseNumber value="${ number2 }" pattern="00,000.00" var="printNum3"/>
	소수점까지 : ${printNum3} <br />
	<fmt:parseNumber value="${ number2 }" integerOnly="true" var="printNum4"/>
	정수 부분만 : ${ printNum4 }


</body>
</html>