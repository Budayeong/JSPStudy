package model2.mvcboard;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//어노테이션으로 매핑
@WebServlet("/mvcboard/view.do")
public class ViewController extends HttpServlet {
	
	/*
	서블릿 수명주기 메서드 중 전송방식에 상관없이 요청을 처리하고 싶을때는 
	service() 오버라이딩.
	service() : 요청 분석 후 doGet() 혹은 doPost() 호출
	*/
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MVCBoardDAO dao = new MVCBoardDAO();
//		파라미터로 전달된 일련번호 받기
		String idx = req.getParameter("idx");
//		조회수 증가
		dao.updateVisitCount(idx);
//		게시물 인출
		MVCBoardDTO dto = dao.selectView(idx);
		dao.close();
		
		/*
		내용의 경우 Enter를 통해 줄바꿈을 하게되므로 웹브라우저 출력시
		<br> 태그로 변경해야함.
		*/
		dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
		
//		게시물이 저장된 DTO 객체를 request영역에 저장, JSP로 포워드
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("/14MVCBoard/View.jsp").forward(req, resp);
	}
}
