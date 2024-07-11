package model2.mvcboard;

import java.io.IOException;

import fileupload.FileUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.JSFunction;

/*
글쓰기를 위한 서블릿 클래스는 doGet()과 doPost()를 둘 다 오벌아ㅣ딩.
글쓰기 페이지로 진입: get방식 요청
전송(submit)을 눌러 폼값을 서버로 전송: post방식 요청
*/
public class WriteController extends HttpServlet {
//	글쓰기 페이지로 진입할때는 다른 로직없이 포워드만 진행
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/14MVCBoard/Write.jsp").forward(req, resp);
	}
	
	/*
	글쓰기는 post방식의 전송이므로 doPost()에서 요청을 처리.
	*/
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		업로드 디렉터리의 물리적 경로 확인
		String saveDirectory = req.getServletContext().getRealPath("/Uploads");
		
//		파일 업로드
		String originalFileName = "";
		try {
//			업로드가 정상적으로 완료되면 원본파일명을 반환
			originalFileName = FileUtil.uploadFile(req, saveDirectory);
		}
		catch (Exception e) {
//			파일업로드 시 오류 발생하면 경고창 띄운 후 작성페이지로 이동
			JSFunction.alertLocation(resp, "파일 업로드 오류입니다.", "../mvcboard/write.do");
			return;
		}
		
//		파일 외 폼값을 DTO에 저장
		MVCBoardDTO dto = new MVCBoardDTO();
		dto.setName(req.getParameter("name"));
		dto.setTitle(req.getParameter("title"));
		dto.setContent(req.getParameter("content"));
		dto.setPass(req.getParameter("pass"));
		
//		오리지널 파일명이 빈값이 아니면 = 첨부파일 업로드가 정상적으로 완료됐으면
		if(originalFileName != "") {
//			파일명을 "날짜_시간.확장자" 형식으로 변경
			String savedFileName = FileUtil.renameFile(saveDirectory, originalFileName);
//			파일 확장자
			String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
			
//			원본파일명과 저장된파일명을 DTO에 저장
			dto.setOfile(originalFileName);
			dto.setSfile(savedFileName);
		}
		
//		DAO를 통해 DB에 값 입력
		MVCBoardDAO dao = new MVCBoardDAO();
		int result = dao.insertWrite(dto);
		dao.close();
		
//		insert에 성공하면 목록으로 이동, 실패하면 쓰기페이지로 이동
		if(result==1)
			resp.sendRedirect("../mvcboard/list.do");
		else
			JSFunction.alertLocation(resp, "글쓰기에 실패했습니다", "../mvcboard/write.do");
		
	}
	
	
}
