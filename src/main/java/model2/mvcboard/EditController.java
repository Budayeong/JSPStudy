package model2.mvcboard;

import java.io.IOException;

import fileupload.FileUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.JSFunction;

/*
요청명
*/
@WebServlet("/mvcboard/edit.do")
@MultipartConfig(
		maxFileSize = 1024 * 1024 *10,
		maxRequestSize = 1024 * 1024 * 100
		)
public class EditController extends HttpServlet {
//	수정페이지로 진입하면 기존 게시물의 내용을 가져와 작성폼에 설정.
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		파라미터로 전달된 일련번호를 통해 기존의 게시물 인출.
		상세보기에서 사용한 메서드 그대로 사용
		*/
		String idx = req.getParameter("idx");
		MVCBoardDAO dao = new MVCBoardDAO();
		MVCBoardDTO dto = dao.selectView(idx);
//		DTO를 request영역에 저장한 후 수정페이지로 포워드
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("/14MVCBoard/Edit.jsp").forward(req, resp);
	}

//	수정할 내용을 입력한 후 전송된 폼값을 update 쿼리문으로 실행
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		업로드 디렉터리의 물리적 경로 얻기
		String saveDirectory = req.getServletContext().getRealPath("/Uploads");
		
//		파일 업로드
		String originalFileName = "";
		try {
			originalFileName = FileUtil.uploadFile(req, saveDirectory);
		}
		catch (Exception e) {
			JSFunction.alertLocation(resp, "파일 업로드 오류입니다.", "../mvcboard/write.do");
			return;
		}
		
//		파일 외 폼값을 받아 변수에 저장
		/*
		수정을 위해 hidden 입력상자에 저장된 내용도 같이 받아옴.
		게시물의 일련번호와 기존 등록된 파일명이 함께 전송된다.
		*/
		String idx = req.getParameter("idx");
		String prevOfile = req.getParameter("prevOfile");
		String prevSfile = req.getParameter("prevSfile");
		
		String name = req.getParameter("name");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		/*
		패스워드 검증에 성공한 경우 session영역에 저장된 패스워드를 가져옴
		영역에 저장 시 Object타입으로 자동형변환되므로 기존 타입으로 다운캐스팅
		후 사용한다.
		*/
		HttpSession session = req.getSession();
		String pass = (String)session.getAttribute("pass");
		
//		DTO에 저장
		MVCBoardDTO dto = new MVCBoardDTO();
		dto.setIdx(idx);
		dto.setName(name);
		dto.setTitle(title);
		dto.setContent(content);
		dto.setPass(pass);
		
		/*
		수정페이지에서는 첨부파일을 등록하지 않을수도 있으므로 파일이 있을때와
		없을때를 구분해서 DTO를 설정해야함
		*/
		if(originalFileName != "") {
//			첨부파일이 있는 경우에는 저장된 파일명을 새롭게 변경
			String savedFileName = FileUtil.renameFile(saveDirectory, originalFileName);
			
//			새롭게 등록한 파일의 정보를 DTO에 추가
			dto.setOfile(originalFileName);
			dto.setSfile(savedFileName);
			
//			기존 파일 삭제
			FileUtil.deleteFile(req, "/Uploads", prevSfile);
		}
		else {
//			첨부파일이 없으면 기존 파일명 유지
			dto.setOfile(prevOfile);
			dto.setSfile(prevSfile);
		}
		
//		DB에 수정 내용 적용
		MVCBoardDAO dao = new MVCBoardDAO();
		int result = dao.updatePost(dto);
		dao.close();
		
		if(result==1) {
//			수정에 성공하면 session 영역에 저장된 패스워드를 삭제
			session.removeAttribute("pass");
			resp.sendRedirect("../mvcboard/view.do?idx=" + idx);
		}
		else
//			실패하면 경고창을 띄우고 이동
			JSFunction.alertLocation(resp, "비밀번호 검증을 다시 진행해주세요.", "../mvcboard/view.do?idx=" +idx);
	}
}
