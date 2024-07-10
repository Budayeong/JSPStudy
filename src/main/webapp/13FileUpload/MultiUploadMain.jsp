<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FileUpload</title>
</head>
<script>
	/* 
	폼값을 submit할때 빈값에 대한 검증을 진행.
	만약 빈값이 발견되면 리스너쪽으로 false를 반환.
	*/
	function validateForm(form) {
		if (form.title.value == ""){
			alert("제목을 입력하세요.");
			form.title.focus();
			return false;
		}
		if (form.ofile.value == ""){
			alert("첨부파일은 필수 입력입니다.");
			return false;
		}
	}
</script>
<body>
	<h3>파일 업로드(multiple속성추가)</h3>
	<span style="color:red;">${ errormessage }</span>
	<form name="fileForm" method="post" enctype="multipart/form-data" action="MultipleProcess.do" onsubmit="return validateFrom(this);">
		<!-- text 타입: 한줄 입력상자 -->
		제목: <input type="text" name="title" /> <br />
		<!-- 
		file타입의 태그에 multiple 속성을 부여하면 2개 이상의 파일을 선택할 수 있다.
		Ctirl키나 Drang&Drop으로 선택한다.
		 -->
		<!-- file 타입: 파일 첨부, 파일선택을 위한 버튼 출력 -->
		첨부파일 : <input type="file" name="ofile" multiple/><br />
		<input type="submit" value="전송하기" />
	</form>
	

</body>
</html>