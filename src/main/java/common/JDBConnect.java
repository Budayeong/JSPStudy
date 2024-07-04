package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletContext;

public class JDBConnect {
	
//	DB 연결, 정적쿼리실행, 동적쿼리실행, select결과 반환
	public Connection con;
	public Statement stmt;
	public PreparedStatement psmt;
	public ResultSet rs;
	
//	기본생성자: 매개변수가 없는 생성자
	public JDBConnect() {
		try {
//			오라클 드라이버 로드
			Class.forName("oracle.jdbc.OracleDriver");
//			커네션 URL, 아이디, 패스워드 준비
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id = "musthave";
			String pwd = "1234";
//			데이터베이스 연결 시도
			con = DriverManager.getConnection(url, id, pwd);
//			Connection 인스턴스가 반환되면 연결성공
			System.out.println("DB 연결성공 (기본생성자)");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

//	인자생성자: 4개의 매개변수 선언
	public JDBConnect(String driver, String url, String id, String pwd) {
		try {
			/*
			기본생성자는 고정된 값을 통해 DB정보를 설정했지만 
			인자생성자는 매개변수를 통해 전달된 값으로 DB연결을 시도
			*/
			Class.forName(driver);
			con = DriverManager.getConnection(url, id, pwd);
			System.out.println("DB 연결 성공 (인수 생성자 1)");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//	인자생성자2: Application내장객체를 매개변수로 선언
	public JDBConnect(ServletContext application) {
		/*
		내장객체를 전달받았으므로 Java클래스 내에서 web.xml에 접근할 수 있음
		그러면 JSP에서 DB연결을 위해 반복적으로 사용했던 코드를 메서드로 정의하여
		중복된 코드를 줄일 수 있음 
		*/
		try {
			String driver = application.getInitParameter("OracleDriver");
			Class.forName(driver);
			String url = application.getInitParameter("OracleURL");
			String id = application.getInitParameter("OracleId");
			String pwd = application.getInitParameter("OraclePwd");
			con = DriverManager.getConnection(url, id, pwd);
			System.out.println("DB 연결 성공(인수 생성자 2)");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void close() {
		try {
			if(con!=null) con.close();
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(psmt!=null) psmt.close();
			System.out.println("JDBC 자원 반납");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
