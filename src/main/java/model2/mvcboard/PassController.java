package model2.mvcboard;

import java.io.IOException;

import fileupload.FileUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.JSFunction;

/*
비회원제 게시판은 인증절차 없이 누구나 들어올 수 있으므로 수정, 삭제를 위해서는 본인확인 절차 필요.
따라서 패스워드를 통해 검증 후 수정 혹은 삭제
*/
@WebServlet("/mvcboard/pass.do")
public class PassController extends HttpServlet {
	/*
	검증 페이지로 진입할때는 링크를 통해 하게되므로 get방식의 요청을 처리.
	진입시 파라미터로 전달되는 mode와 일련번호를 받아야함
	*/
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		첫번째 파라미터는 컨트롤러에서 받은 후 request영역에 저장해 JSP로 전달함.
		*/
		req.setAttribute("mode", req.getParameter("mode"));
		req.getRequestDispatcher("/14MVCBoard/Pass.jsp").forward(req, resp);
	}

//	패스워드 검증 페이지에서 전송한 값을 통해 레코드 검증
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		전송된 파라미터 받기
		String idx = req.getParameter("idx");
		String mode = req.getParameter("mode");
		String pass = req.getParameter("pass");
		
//		패스워드 검증
		MVCBoardDAO dao = new MVCBoardDAO();
//		일련번호와 비밀번호에 일치하는 게시물이 있는지 확인
		boolean confirmed = dao.confirmPassword(pass, idx);
		dao.close();
		
//		패스워드에 일치하는 게시물이 있으면
		if(confirmed) {
//			수정하기인 경우
			if(mode.equals("edit")) {
//				서블릿에서 session 내장객체를 얻어옴
				HttpSession session = req.getSession();
//				검증에 사용된 패스워드를 session영역에 저장
				session.setAttribute("pass", pass);
//				수정페이지로 이동
				resp.sendRedirect("../mvcboard/edit.do?idx="+idx);
				/*
				session영역에 저장된 속성값은 페이지를 이동하더라도
				공유되므로 이동한 페이지에서 참조할 수 있다.
				*/
			}
//			삭제하기인 경우
			else if(mode.equals("delete")) {
//				기존 게시물의 내용을 가져옴 
				dao = new MVCBoardDAO();
				MVCBoardDTO dto = dao.selectView(idx);
//				게시물 삭제처리
				int result = dao.deletePost(idx);
				dao.close();
//				게시물이 삭제되었다면
				if(result == 1) {
//					DB에 저장된 파일명을 인출한 후
					String saveFileName = dto.getSfile();
//					서버에서 파일을 삭제함
					FileUtil.deleteFile(req, "/Uploads", saveFileName);
				}
//				정상적으로 삭제되었다면 목록으로 이동
				JSFunction.alertLocation(resp, "삭제되었습니다", "../mvcboard/list.do");
			}
		}
		else {
//			비밀번호가 틀린 경우: 경고창을 띄우고 뒤로 이동
			JSFunction.alertBack(resp, "비밀번호 검증에 실패했습니다");
		}
	}
	
	
}
