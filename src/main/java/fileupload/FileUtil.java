package fileupload;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

//파일업로드와 관련된 기능을 메서드로 정의한 유틸리티 클래스
public class FileUtil {
	
	
//	파일 업로드 처리 (매개변수: request내장객체, 디렉터리경로)
	public static String uploadFile(HttpServletRequest req, String sDirectory) throws IOException, ServletException {
		
		/*
		파일 첨부를 위한 <input>태그의 name 속성값을 이용해 Part인스턴스 생성.
		이를 통해 파일을 서버에 저장할 수 있음.
		*/
		Part part = req.getPart("ofile");
		
		/*
		Part 인스턴스에서 헤더값을 통해 원본 파일명을 얻어옴. (차후 콘솔에서 확인)
		*/
		String partHeader = part.getHeader("content-disposition");
		System.out.println("partHeader=" + partHeader);
		
		/*
		헤더값을 통해 얻어온 문자열에서 "filename="을 구분자로 split함.
		그러면 String 타입의 배열로 반환됨. 
		*/
		String[] phArr = partHeader.split("filename=");
		/*
		위 결과 중 1번 인덱스의 값이 파일명이됨. 여기서 더블쿼테이션을 제거해야하므로
		아래와 같이 replace() 를 통해 제거.
		더블쿼테이션 제거 시 이스케이프 시퀀스 기호 추가하는 것 주의.
		*/
		String originalFileName = phArr[1].trim().replace("\"", "");
		
		/*
		전송된 파일이 있다면, 서버의 디렉터리에 파일을 저장.
		File.separator : OS마다 경로를 표시하는 기호가 다르므로 해당 OS에 맞는
			구분기호를 자동으로 추가.
		*/
		if (!originalFileName.isEmpty())
			part.write(sDirectory + File.separator + originalFileName);
		
//		원본파일명 반환
		return originalFileName;
	}
	
//	서버에 저장된 파일명을 변경(파일명이 한글인 경우 인코딩 문제 발생할수있으므로 영문/숫자 조합으로 안전하게 변경)
	public static String renameFile(String sDirectory, String fileName) {
		
		/*
		파일명에서 확장자를 잘라내기 위해 뒤에서부터 .이 있는 위치를 찾는다.
		파일명에는 2개 이상의 .을 사용할 수 있기때문.
		*/
		String ext = fileName.substring(fileName.lastIndexOf("."));
		/*
		날짜와 시간을 이용해 파일명으로 사용할 문자열을 생성.
		"년월일_시분초999"와 같은 형태가 됨.
		*/
		String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
//		파일명과 확장자를 결합한다
		String newFileName = now + ext;
		
//		원본파일명과 새로운파일명을 이용해서 File인스턴스 생성
		File oldFile = new File(sDirectory + File.separator + fileName);
		File newFile = new File(sDirectory + File.separator + newFileName);
//		파일명을 새로운 이름으로 변경
		oldFile.renameTo(newFile);
		
//		변경된 파일명을 반환
		return newFileName;
	}
	
//	두개 이상 파일 업로드 처리(multiple 속성 부여됨)
	public static ArrayList<String> multipleFile(HttpServletRequest req, String sDirectory) throws IOException, ServletException {
//		파일명 저장을 위한 List 계열의 컬렉션 생성
		ArrayList<String> listFileName = new ArrayList<String>();
		
//		getParts() 메서드로 Part인스턴스를 컬렉션의 형태로 생성
		Collection<Part> parts = req.getParts();
		
		for (Part part : parts) {
//			전송된 폼값중에 파일이 아니면 저장 대상이 아님 -> 반복문의 처음으로 이동
			if(!part.getName().equals("ofile"))
				continue;
			
//			폼값 중 파일이 맞다면 헤더값을 얻어옴
			String partHeader = part.getHeader("content-disposition");
			System.out.println("partHeader=" + partHeader);
		
//			헤더값에서 파일명을 잘라냄
			String[] phArr = partHeader.split("filename=");
			String originalFileName = phArr[1].trim().replace("\"", "");
		
//			write() 이용 전송된 파일을 서버 디렉터리에 저장
			if (!originalFileName.isEmpty())
				part.write(sDirectory + File.separator + originalFileName);
			
			listFileName.add(originalFileName);
		}
		return listFileName;
	}
	
	
}
