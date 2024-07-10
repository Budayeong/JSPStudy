package fileupload;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
어노테이션을 통해 매핑.
파일업로드 폼에서 submit하면 요청을 받아 처리
*/
@WebServlet("/13FileUpload/UploadProcess.do")
/*
maxFileSize : 개별 파일의 최대 용량을 1Mb로 설정
maxRequestSize : 전체 파일의 용량을 10Mb로 설정
*/
@MultipartConfig(
		maxFileSize = 1024 * 1024* 1,
		maxRequestSize =  1024 * 1024 *10
)
public class UploadProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
//	파일업로드 -> post만 가능
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
//			파일이 저장될 디렉터리의 물리적경로를 얻어옴
			String saveDirectory = getServletContext().getRealPath("/Uploads");
//			파일 업로드 처리 
			String originalFileName = FileUtil.uploadFile(req, saveDirectory);
//			서버에 저장된 파일명을 변경
			String saveFileName = FileUtil.renameFile(saveDirectory, originalFileName);
//			업로드가 완료되면 파일 목록 페이지로 이동
			resp.sendRedirect("FileList.jsp");
		}
		catch (Exception e) {
			e.printStackTrace();
//			만약 업로드에 실패하면 request영역에 메시지를 저장한 후 포워드
			req.setAttribute("errorMessage", "파일 업로드 오류");
			req.getRequestDispatcher("FileUploadMain.jsp").forward(req, resp);
		}
	}
	
}