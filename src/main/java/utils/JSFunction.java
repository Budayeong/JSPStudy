package utils;

import jakarta.servlet.jsp.JspWriter;

//자주 사용하는 JS 함수를 클래스로 정의
public class JSFunction {
//	static메서드로 정의해 인스턴스 생성없이 클래스명으로 호출
	public static void alertLocation(String msg, String url, JspWriter out) {
		/*
		Java 클래스에서는 JSP의 내장객체를 즉시 사용할 수 없으므로
		반드시 매개변수로 전달받아 사용해야함.
		여기서는 웹 브라우저에 문자열을 출력하기 위해 out내장객체를 
		JSPWriter 타입으로 받은 후 사용하고 있음
		*/
		try {
//			Javascript를 하나의 문자열로 정의
			String script = "" 
					+ "<script>"
					+ "		alert('" + msg + "');"
					+ "		location.href='"+url+"';"
					+"</script>";
//			해당 문자열을 웹브라우저에 출력
			out.print(script);
		}
		catch (Exception e) {}
	}

	
	public static void alertBack(String msg, JspWriter out) {
		try {
			String script = ""
					+ "<script>"
					+ "		alert('" + msg + "');"
					+ "		history.back();"
					+ "</script>";
			out.print(script);
		}
		catch (Exception e) {}
	}
	
	
}
