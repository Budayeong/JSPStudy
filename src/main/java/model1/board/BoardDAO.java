package model1.board;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.JDBConnect;
import jakarta.servlet.ServletContext;

//jdbc를 이용한 db연결을 위해 클래스 상속
public class BoardDAO extends JDBConnect {
	/*
	생성자 호출 시 application 내장객체를 매개변수로 받은 후 부모클래스의 생성자를 호출할때 재사용.
	부모클래스에서는 web.xml에 등록된 컨텍스트 초기화 파라미터를 얻어와서 DB에 연결함.
	*/
	public BoardDAO(ServletContext application) {
		super(application);
	}
	
//	게시물의 개수를 카운트해 int형으로 반환
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;
		
//		게시물 수를 얻어오기 위한 쿼리문 작성
		String query = "select count(*) from board";
//		검색어가 있는 경우 where절을 추가하여 조건에 맞는 게시물만 select
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField")+" " + " LIKE '%" + map.get("searhWrod") + "%'"; 
		}
		
		try {
//			정적쿼리문 실행을 위한 Statement 인스턴스 생성
			stmt = con.createStatement();
//			쿼리문 실행 후 결과는 ResultSet으로 반환
			rs = stmt.executeQuery(query);
			
//			커서를 첫번째 행으로 이동하여 레코드를 읽는다
			rs.next();
//			첫번째 컬럼(count함수)의 값을 가져와서 변수에 저장
			totalCount = rs.getInt(1);
		}
		catch (Exception e) {
			System.out.println("게시물 수를 구하는 중 예외발생");
			e.printStackTrace();
		}
		return totalCount;
	}
		
	/*
	작성된 게시물을 인출하여 반환. 특히 반환값은 여러개의 레코드를 
	반환할 수 있고, 순서를 보장해야하므로 List를 사용
	*/
	public List<BoardDTO> selectList(Map<String, Object> map) {
		/*
		List계열의 컬렉션 생성. 이때 타입 매개변수는 board테이블을
		대상으로 하므로 BoardDTO로 설정
		*/
		List<BoardDTO> bbs = new Vector<BoardDTO>();
		
		/*
		레코드 인출을 위한 select쿼리문 작성. 최근 게시물이 상단에 출력돼야 하므로
		일련번호의 내림차순으로 정렬함.
		*/
		String query ="SELECT * FROM board ";
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") +" " + " LIKE '% " + map.get("searchWord") + " % ' ";
		}
		query += " ORDER BY num DESC ";
		
		try {
//			쿼리 실행 및 결과셋 반환
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
//			2개 이상의 레코드가 반환될 수 있으므로 whle문을 사용
			while(rs.next()) {
//				하나의 레코드를 저장할 수 있는 dto 객체 생성
				BoardDTO dto = new BoardDTO();
				
//				setter를 이용해 각 컬럼의 값을 멤버변수에 저장
				dto.setName(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				
//				레코드 하나를 dto에 저장 후 List에 추가
				bbs.add(dto);
			}
		}
		catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
//		인출한 레코드를 저장한 List를 JSP로 반환
		return bbs;
	}
		
		
	
}
