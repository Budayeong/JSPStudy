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
		String query = "SELECT COUNT(*) FROM board";
//		검색어가 있는 경우 where절을 추가하여 조건에 맞는 게시물만 select
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField")+ " " 
					+ " LIKE '%" + map.get("searchWord") + "%'"; 
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
			query += " WHERE " + map.get("searchField") + " " 
					+ " LIKE '%" + map.get("searchWord") + "%' ";
		}
		query += " ORDER BY num DESC";
		
		try {
//			쿼리 실행 및 결과셋 반환
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
//			2개 이상의 레코드가 반환될 수 있으므로 while문을 사용
			while(rs.next()) {
//				하나의 레코드를 저장할 수 있는 dto 객체 생성
				BoardDTO dto = new BoardDTO();
				
//				setter를 이용해 각 컬럼의 값을 멤버변수에 저장
				dto.setNum(rs.getString("num"));
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
		
//	게시물 입력
	public int insertWrite(BoardDTO dto) {
//		사용자가 작성한 내용은 DTO에서 저장한 후 인수로 전달
		int result = 0 ;
		
		try {
//			인파라미터가 있는 insert 쿼리문 작성
			String query = "INSERT INTO board ( "
					+ " num, title, content, id, visitcount) "
					+ " VALUES ( "
					+ " seq_board_num.NEXTVAL, ?, ?, ?, 0)";
			
//			일련번호의 경우 시퀀스를 통해 입력한다
			
//			prepared인스턴스 생성 및 인파라미터 설정
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getId());
			
//			쿼리문 실행
			result  = psmt.executeUpdate();
			
		}
		catch (Exception e) {
			System.out.println("게시물 입력 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}

//	매개변수로 전달된 게시물의 일련번호로 게시물을 인출함
	public BoardDTO selectView(String num) {
//		하나의 레코드를 저장하기 위한 DTO인스턴스 생성
		BoardDTO dto = new BoardDTO();
		
		/* 내부조인(inner join)을 통해 member테이블과 name 컬럼까지 select한다 */
		String query = "SELECT B.*, M.name"
				+ " FROM board B inner join member M"
				+ " ON B.id=M.id"
				+ " WHERE num=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num);
			rs = psmt.executeQuery();
			
			/*
			일련번호는 중복되지 않으므로 단 한개의 게시물만 인출하게된다.
			따라서 while문이 아닌 if문으로 처리한다.
			next()는 ResultSet으로 반환된 레코드를 확인해서 존재하면 true를 반환해준다.
			*/
			if(rs.next()) {
				/* 각 컬럼의 값을 추출할 때 1부터 시작하는 인덱스와 컬럼명
				 둘 다 사용할 수 있다. 날짜인 경우에는 getDate()로 인출할 수 있다. */
				dto.setNum(rs.getString(1));
				dto.setTitle(rs.getString(2));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				dto.setName(rs.getString("name"));
			}
		}
		catch (Exception e) {
			System.out.println("게시물 상세보기 중 예외발생");
			e.printStackTrace();
		}
		
		return dto;
	}

//	조회수 증가
//	게시물의 조회수를 1 증가시킨다
	public void updateVisitCount(String num) {
		/* 게시물의 일련번호를 통해 visitcount를 1 증가시킴.
		 이 컬럼은 number타입이므로 사칙연산이 가능하다. */
		String query =  "UPDATE board SET"
						+ " visitcount = visitcount + 1"
						+ " WHERE num=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num);
			rs = psmt.executeQuery();
		}
		catch (Exception e) {
			System.out.println("게시물 조회수 증가 중 예외발생");
			e.printStackTrace();
		}
		
	}
	
//	게시물 수정
	public int updateEdit(BoardDTO dto) {
		int result = 0;
		
		try {
//			일련번호와 일치하는 게시물의 제목과 내용을 수정하는 update 쿼리문
			String query = "UPDATE board "
					+ "SET title=?, content=? "
					+ "WHERE num=?";
			
//			쿼리문의 인파라미터 설정 및 실행
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getNum());
//			수정된 레코드의 개수를 반환
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 수정 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}

//	게시물 삭제
	public int deletePost(BoardDTO dto) {
		int result = 0;
		
		try {
//			게시물 삭제를 위한 delete 쿼리문 작성
			String query = "DELETE FROM board WHERE num=?";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getNum());
			
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 삭제 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
	
//	페이징 기능이 있는 서브쿼리문으로 변경한 메서드
	public List<BoardDTO> selectListPage(Map<String, Object> map) {
		List<BoardDTO> bbs = new Vector<BoardDTO>();
		
//		인파라미터가 있는 서브쿼리문 작성
		String query ="SELECT * FROM ( "
				+ "    SELECT Tb.*, ROWNUM rNum FROM ( "
				+ "        SELECT * FROM board ";
		
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField")
					+ " LIKE '%" + map.get("searchWord") + "%' ";
		}
		
		query += "		ORDER BY num DESC "
				+ "     ) Tb "
				+ "   ) "
//				+ "    WHERE rNum BETWEEN ? AND ?";
				+ "    WHERE rNum >= ? AND rNum <= ?";
		
		try {
//			prepared 인스턴스 생성 및 인파라미터 설정
			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString());
			rs = psmt.executeQuery();
//			2개 이상의 레코드가 반환될 수 있으므로 while문을 사용
			while(rs.next()) {
//				하나의 레코드를 저장할 수 있는 dto 객체 생성
				BoardDTO dto = new BoardDTO();
				
//				setter를 이용해 각 컬럼의 값을 멤버변수에 저장
				dto.setNum(rs.getString("num"));
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
