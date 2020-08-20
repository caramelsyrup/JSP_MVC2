<%@page import="com.board.BoardVO"%>
<%@page import="com.board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글수정</title>
<%
int num = Integer.parseInt(request.getParameter("num"));
BoardDAO dao = BoardDAO.getinstance();
BoardVO board = dao.boardView(num);
%>
</head>
<body>
	<h1>글 수정하기</h1>
<form action="updatePro.jsp" method="post">
<input type="hidden" name="num" value=<%=num %>>
	<table>
		<tr>
			<td>이름</td>
			<td><%=board.getWriter() %></td>
		</tr>
		<tr>
			<td>제목</td>
			<td><input type="text" name="subject" id="subject" size="40" value="<%=board.getSubject()%>"></td>
		</tr>
		<tr>
			<td>이메일</td>
			<td><input type="email" name="email" id="email" value="<%=board.getEmail()%>"> </td>
		</tr>
		<tr>
			<td>내용</td>
			<td><textarea rows="30px" cols="50px" name="content" id="content"><%=board.getContent()%></textarea> </td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="passwd" id="passwd" size="10"></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" value="글수정">
				<input type="reset" value="취소">
			</td>
		</tr>
	</table>
</form>	
</body>
</html>