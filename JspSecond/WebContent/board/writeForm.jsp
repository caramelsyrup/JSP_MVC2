<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>
<%
	// 새글 초기값
	int num = 0, ref=1,re_step=0,re_level=0;
	// 답글
	if(request.getParameter("num")!=null){	
		num = Integer.parseInt(request.getParameter("num"));
		ref = Integer.parseInt(request.getParameter("ref"));
		re_step = Integer.parseInt(request.getParameter("re_step"));
		re_level = Integer.parseInt(request.getParameter("re_level"));	
	}
%>
<style type="text/css">
	table tr td{
		border: 1px blue solid;
	}
</style>
</head>
<body>
	<form action="writePro.jsp" method="post">
	<input type="hidden" name="num" value="<%=num%>">
	<input type="hidden" name="ref" value="<%=ref%>">
	<input type="hidden" name="re_step" value="<%=re_step%>">
	<input type="hidden" name="re_level" value="<%=re_level%>">
		<table border="1">
			<tr>
				<td>이름</td>
				<td><input type="text" name="writer" id="writer" size="10"></td>
			</tr>
			<tr>
				<td>제목</td>
				<td>
<%
				if(request.getParameter("num")!=null){	// 답글
%>					
					<input type="text" name="subject" id="subject" size="40" value="[답글]">
<% 					
				} else{		// 새글
%>					
					<input type="text" name="subject" id="subject" size="40">
<%					
				}
%>
				</td>
			</tr>
			<tr>
				<td>이메일</td>
				<td><input type="email" name="email" id="email"> </td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea rows="30px" cols="50px" name="content" id="content"></textarea> </td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="passwd" id="passwd" size="10"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="글쓰기">
					<input type="reset" value="취소">
					<input type="button" value="목록" onclick="location.href='list.jsp'">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>