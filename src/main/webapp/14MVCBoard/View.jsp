<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 첨부형 게시판</title>
</head>
<body>
<h2>파일 첨부형 게시판 - 상세 보기(View)</h2>

<table border="1" width="90%">
    <colgroup>
        <col width="15%"/> <col width="35%"/>
        <col width="15%"/> <col width="*"/>
    </colgroup> 
    <tr>
        <td>번호</td> <td>${ dto.idx }</td>
        <td>작성자</td> <td>${ dto.name }</td>
    </tr>
    <tr>
        <td>작성일</td> <td>${ dto.postdate }</td>
        <td>조회수</td> <td>${ dto.visitcount }</td>
    </tr>
    <tr>
        <td>제목</td>
        <td colspan="3">${ dto.title }</td>
    </tr>
    <tr>
        <td>내용</td>
        <td colspan="3" height="100">
        	${ dto.content }
         	<c:if test="${ not empty dto.ofile }">
	         	<c:set var="ext" value="${ dto.ofile.substring(dto.ofile.lastIndexOf('.')+1) }"></c:set>
	         	<c:choose>
					<c:when test="${ ext eq 'png' or ext eq 'gif' or ext eq 'jpg' }">
						<br />
	         			<img src="../Uploads/${ dto.sfile }" style="max-width:600px;" />
	         		</c:when>
	         		<c:when test="${ ext eq 'mp3' or ext eq 'wav' }">
		         		<br />
		         		<audio src="../Uploads/${ dto.sfile }" controls style="max-width:600px;"></audio>
					</c:when>
					<c:when test="${ ext eq 'mp4'}">
		         		<br />
		         		<video src="../Uploads/${ dto.sfile }" controls></video>
					</c:when>
					<c:otherwise>
						<a href="../mvcboard/download.do?ofile=${ dto.ofile }&sfile=${ dto.sfile }&idx=${ dto.idx }">[다운로드]</a>
					</c:otherwise> 
	         	</c:choose>
   			</c:if>
        </td>
    </tr> 
    
    
    
    <tr>
        <td>첨부파일</td>
        <td>            
        	<!-- 글 작성 시 첨부파일은 필수사항이 아니므로 첨부한 경우에만 다운로드 링크를 화면에 출력 -->
        	<c:if test="${ not empty dto.ofile }">
        	${ dto.ofile }
        	<a href="../mvcboard/download.do?ofile=${ dto.ofile }&sfile=${ dto.sfile }&idx=${ dto.idx }">[다운로드]</a>
        	</c:if>
        </td>
         <td>다운로드수</td>
        <td>${ dto.downcount }</td>
    </tr> 
    <tr>
        <td colspan="4" align="center">
            <button type="button" onclick="location.href='../mvcboard/pass.do?mode=edit&idx=${ param.idx }';">
                수정하기
            </button>
            <button type="button" onclick="location.href='../mvcboard/pass.do?mode=delete&idx=${ param.idx }';">
                삭제하기
            </button>
            <button type="button" onclick="location.href='../mvcboard/list.do';">
                목록 바로가기
            </button>
        </td>
    </tr>
</table>
</body>
</html>

