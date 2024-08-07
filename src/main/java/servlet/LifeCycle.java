package servlet;

import java.io.IOException;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/12Servlet/LifeCycle.do")
public class LifeCycle extends HttpServlet{
	
	/*
	서블릿 수명주기에서 최초로 호출되는 메서드는 어노테이션을 통해 생성함.
	따라서 메서드명은 개발자가 결정하면됨. init() 메서드가 호출되기 전에
	전처리를 위해 주로 사용.
	*/
	@PostConstruct
	public void myPostConstrut() {
		System.out.println("myPostConnstruct() 호출");
	}
	
	/*
	서블릿 객체 생성 후 딱 한번만 호출되는 메서드로, 
	보통 서블릿을 초기화하는 역할
	*/
	@Override
	public void init() throws ServletException {
		System.out.println("init() 호출");
	}
	
	/*
	클라이언트의 요청을 분석하기 위해 호출됨.
	전송방식에 상관없이 먼저 호출된 후 doGet() or doPost()를 호출함
	*/
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("service() 호출");
		/* service() 메서드에서 요청방식을 분석한 후 
		각 메서드를 호출할 때 별도의 호출문장을 기술하지 않는다. 아래 문장으로 가능 */
		super.service(req, resp);
	}
	
//	get방식의 요청을 처리
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doGet() 호출");
		req.getRequestDispatcher("/12Servlet/LifeCycle.jsp").forward(req, resp);
	}
	
//	post방식의 요청을 처리
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doPost()호출");
		req.getRequestDispatcher("/12Servlet/LifeCycle.jsp").forward(req, resp);
	}

	/*
	서블릿이 새롭게 컴파일되거나, 서버가 종료될 때 호출.
	이때 서블릿 객체는 메모리에서 소멸됨.
	*/
	@Override
	public void destroy() {
		System.out.println("destroy() 호출");
	}
	
	/*
	destroy() 호출 후 후처리를 위해 사용됨. 어노테이션을 사용하므로 메서드명은 직접 결정.
	*/
	@PreDestroy
	public void myPreDestroy() {
		System.out.println("myPreDestroy() 호출");
	}
	
}
