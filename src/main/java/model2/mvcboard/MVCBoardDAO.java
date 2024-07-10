package model2.mvcboard;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.DBConnPool;
import common.JDBConnect;
import jakarta.servlet.ServletContext;
import model1.board.BoardDTO;

//커넥션풀을 통한 DB연결을 위해 클래스 상속
public class MVCBoardDAO extends DBConnPool {
	
	public MVCBoardDAO() {
		super();
	}
	
//	게시물 개수를 카운트. 검색어가 있는 경우 where절을 동적으로 추가.
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;
		
		String query = "SELECT COUNT(*) FROM mvcboard";
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField")+ " " 
					+ " LIKE '%" + map.get("searchWord") + "%'"; 
		}
		
		try {
//			인파라미터가 없는 정적쿼리문을 실행하므로 Statement인스턴스 생성
			stmt = con.createStatement();
//			쿼림누 실행 후 결과 반환
			rs = stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1);
		}
		catch (Exception e) {
			System.out.println("게시물 카운트 중 예외발생");
			e.printStackTrace();
		}
		return totalCount;
	}
		
//	목록에 출력할 게시물을 페이지 단위로 얻어오기 위한 메서드
	public List<MVCBoardDTO> selectListPage(Map<String, Object> map) {
//		mvcboard 테이블을 대상으로 하므로 타입매개변수 확인 필요
		List<MVCBoardDTO> board = new Vector<MVCBoardDTO>();
		
		/*
		페이징 처리를 위한 서브쿼리문 작성. 게시물의 순차적인 일련번호를 부여하는
		rownum을 통해 게시물을 구간에 맞게 인출.
		*/
		String query ="SELECT * FROM ( "
				+ "    SELECT Tb.*, ROWNUM rNum FROM ( "
				+ "        SELECT * FROM mvcboard ";
		
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField")
					+ " LIKE '%" + map.get("searchWord") + "%' ";
		}
		
		query += "		ORDER BY idx DESC "
				+ "     ) Tb "
				+ "   ) "
				+ "    WHERE rNum BETWEEN ? AND ?";
		
		try {
//			인파라미터가 있는 동적쿼리문 실행
			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString());
			rs = psmt.executeQuery();

			while(rs.next()) {
				MVCBoardDTO dto = new MVCBoardDTO();
				
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5)); //날짜타입이므로 getDate()
				dto.setOfile(rs.getString(6));
				dto.setSfile(rs.getString(7));
				dto.setDowncount(rs.getInt(8));
				dto.setPass(rs.getString(9));
				dto.setVisitcount(rs.getInt(10));
				
//				List에 추가
				board.add(dto);
			}
		}
		catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		return board;
	}
	
	
}
