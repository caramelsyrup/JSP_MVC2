<%@page import="com.board.BoardDAO"%>
<%@page import="com.board.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8"); %>    
<jsp:useBean id="board" class="com.board.BoardVO"></jsp:useBean>
<jsp:setProperty property="*" name="board"/> 
    
<%
	BoardDAO dao = BoardDAO.getinstance();
	int flag = dao.boardUpdate(board);
	if(flag == 1){
		response.sendRedirect("list.jsp");
	}else{
%>		
	<script>
		alert("비밀번호가 일치하지 않습니다.");
		history.back();	// history.go(-1); 이전페이지 이동
	</script>
<%		
	}
%>