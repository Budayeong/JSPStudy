package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
서블릿 클래스를 만들기 위한 절차
1. HttpServlet 클래스를 상속
2. 클라이언트 요청을 받아 처리하기 위한 doGet()/doPost()를 오버라이딩
3. 서블릿 필수 패키지 임포트/예외처리(자동완성)
4. request내장객체와 response내장객체는 매개변수를 통해 서블릿에 이미 포함되어 즉시 사용 가능
*/
public class HelloServlet extends HttpServlet {
//	서블릿 작성시 발생되는 경고(Warning)을 위해 추가 (없어도 실행에 영향 X)
	private static final long serialVersionUID = 1L;

//	get방식의 요청을 처리하기 위한 doGet() 메서드 오버라이딩
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		request영역에 속성을 저장
		req.setAttribute("message", "Hello Servlet..!");
//		View에 해당하는 JSP페이지로 포워드
		req.getRequestDispatcher("/12Servlet/HelloServlet.jsp").forward(req, resp);
//		리퀘스트 영역은 포워드 된 페이지까지 공유되므로 서블릿에서 저장한 속성은 JSP에서 사용가능
	}
}
