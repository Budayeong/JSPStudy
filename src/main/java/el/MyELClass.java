package el;

public class MyELClass {
	
//	주민번호로 성별을 판단하는 메서드
	public String getGender(String jumin) {
		String returnStr= "";
//		매개변수로 전달된 값에서 -의 위치를 찾아 1을 더함
		int beginIdx = jumin.indexOf("-") +1;
//		주민번호에서 성별에 해당하는 문자를 잘라냄
		String genderStr= jumin.substring(beginIdx, beginIdx + 1);
//		문자열을 정수로 변환
		int genderInt = Integer.parseInt(genderStr);
		
//		성별 판단
		if (genderInt == 1 || genderInt == 3 ) 
			returnStr = "남자";
		else if (genderInt ==2 || genderInt == 4)
			returnStr = "여자";
		else
			returnStr = "주민번호 오류입니다.";
//		판단한 결과를 반환
		return returnStr;
	}

//	구구단 출력. static메서드
	public static String showGugudan(int limitDan) {
		/*
		반환할 문자열의 변화가 많은 경우엔 String보다 메모리를 절약할 수 있는 StringBuffer 사용.
		문자열이 추가될때 기존의 메모리를 확장하여 저장함.
		*/
		StringBuffer sb = new StringBuffer();
//		구구단을 limitdan까지 출력해 테이블로 만든 후 반환
		try {
			sb.append("<table border='1'>");
			for (int i=2 ; i<=limitDan ; i++) {
				sb.append("<tr>");
				for(int j=1 ; j<=9; j++) {
					sb.append("<td>" + i + "*" + j + "=" + (i*j) + "</tb>");
				}
				sb.append("</tr>");
			}
			sb.append("</table>");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
}
