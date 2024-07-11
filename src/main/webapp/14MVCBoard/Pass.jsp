<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 첨부형 게시판</title>
<script type="text/javascript">	
// 	패스워드 검증만 필요
	function validateForm(form) {
		if(form.pass.value == ""){
			alert("비밀번호를 입력하세요.");
			form.pass.focus();
			return false;
		}	
	}

</script>
</head>
<h2>파일 첨부형 게시판 - 비밀번호 검증(Pass)</h2>
<!-- 
패스워드 검증 페이지는 글쓰기 페이지를 복사해 제작.
단, 패스워드 검증만 하면 되므로 enctype은 삭제
 -->
<form name="writeFrm" method="post" action="../mvcboard/pass.do" onsubmit="return validateForm(this);">
<!-- 패스워드 검증을 위해 게시물의 일련번호와 mode를 전송해야하므로 hidden 박스로 값 저장 필요.
파라미터 idx의 경우 컨트롤러에서 받지않고 뷰에서 직접 EL을 통해 받아서 사용 -->
<input type="hid-den" name="idx" value="${ param.idx }" />
<input type="hid-den" name="mode" value="${ mode }" />
<table border="1" width="90%">
    <tr>
        <td>비밀번호</td>
        <td>
            <input type="password" name="pass" style="width:100px;" />
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <button type="submit">검증하기</button>
            <button type="reset">RESET</button>
            <button type="button" onclick="location.href='../mvcboard/list.do';">
                목록 바로가기
            </button>
        </td>
    </tr>
</table>    
</form>
</body>
</html>

