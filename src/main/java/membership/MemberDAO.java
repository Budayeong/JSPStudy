package membership;

import common.JDBConnect;
import jakarta.servlet.ServletContext;

/*
DAO(Data Access Object)
: 실제 데이터베이스에 접근해 기본적이 CRUD 작업을 하기위한 객체.
DB접속 및 select와 같은 쿼리문을 실행한 후 결과를 반환받는 기능을 수행.
*/

//JDBC를 위한 클래스를 상속해 DB에 연결
public class MemberDAO extends JDBConnect {
	
//	생성자1. 드라이버, 커넥션URL 등 4개의 매개변수로 정의
	public MemberDAO(String drv, String url, String id, String pw) {
		super(drv, url, id, pw);
	}
	
//	생성자2. application 내장객체를 매개변수로 정의
	public MemberDAO(ServletContext application) {
		super(application);
	}
	
	/*
	사용자가 입력한 아이디, 패스워드를 통해 회원테이블을 select한 후 존재하는
	회원정보인 경우 DTO객체에 레코드를 저장한 후 DTO 인스턴스 반환
	*/
	public MemberDTO getMemberDTO(String uid, String upass)	{
//		회원인증을 위한 쿼리문 실행 후 회원정보 저장을 위한 인스턴스 생성
		MemberDTO dto = new MemberDTO();
//		로그인 폼에서 입력한 아이디, 패스워드를 통해 인파라미터를 설정할 수 있도록 쿼리문 작성
		String query = "select * from member where id=? and pass=?";
		
		try {
//			쿼리문 실행을 위한 prepared 인스턴스 생성
			psmt = con.prepareStatement(query);
//			쿼리문의 인파라미터 설정 (아이디, 비밀번호)
			psmt.setString(1, uid);
			psmt.setString(2, upass);
//			쿼리문 실행 후 결과는 ResultSet으로 반환
			rs = psmt.executeQuery();
			
//			반환된 ResultSet 객체에 정보가 저장되어있는지 확인
			if(rs.next()) {
//				회원정보가 있다면 DTO객체에 저장한다
				dto.setId(rs.getString("id"));
				dto.setPass(rs.getString("pass"));
				dto.setName(rs.getString(3));
				dto.setRegidate(rs.getString(4));
			}
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
//		회원정보를 저장한 DTO객체를 반환
		return dto;
	}
	
}
