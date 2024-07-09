package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
어노테이션을 통한 요청명 매핑으로 *(와일드카드)를 통해
여러 요청명을 한번에 매핑한다. 즉, .one으로 끝나는 모든 요청에 대해 매핑처리.
*/
@WebServlet("*.one")
public class FrontController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		request 내장객체를 통해 현재 요청된 URL을 얻어옴. 전체경로명에서 HOST를
		제외한 나머지 경로를 얻어온다.
		*/
		String uri = req.getRequestURI();
//		URL에서 마지막 / 의 인덱스를 얻어옴
		int lastSlash = uri.lastIndexOf("/");
//		앞에서 얻은 인덱스를 통해 URL을 잘라냄. 즉 마지막의 요청명만 남게됨.
		String commandStr = uri.substring(lastSlash);
		
		/*
		마지막 요청명을 통해 요청을 판단한 후 해당 요청을 처리할 메서드를 호출.
		이때 사용자의 요청정보를 받은 request 객체를 인수로 전달.
		즉 사용자의 모든 요청을 메서드로 전달하는 것
		*/
		if(commandStr.equals("/regist.one"))
			registFunc(req);
		else if (commandStr.equals("/login.one"))
			loginFunc(req);
		else if (commandStr.equals("/freeboard.one"))
			freeboardFunc(req);
		
//		요청명에 관련된 값들을 request 영역에 저장
		req.setAttribute("uri", uri);
		req.setAttribute("commandStr", commandStr);
//		View로 포워드
		req.getRequestDispatcher("/12Servlet/FrontController.jsp").forward(req, resp);
		
	}
	
//	페이지별 처리 메서드
	void registFunc(HttpServletRequest req) {
		req.setAttribute("resultValue", "<h4>회원가입</h4>");
	}
	
	void loginFunc(HttpServletRequest req) {
		req.setAttribute("resultValue", "<h4>로그인</h4>");
	}
	
	void freeboardFunc(HttpServletRequest req) {
		req.setAttribute("resultValue", "<h4>자유게시판</h4>");
	}
}
