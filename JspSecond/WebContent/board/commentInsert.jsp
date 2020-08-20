<%@page import="com.board.BoardDAO"%>
<%@page import="com.board.commentboardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String msg = request.getParameter("msg");
	int num = Integer.parseInt(request.getParameter("num"));
	String userid = (String)session.getAttribute("USERID");
	if(userid==null){
		out.println("1");
	}else{
	
	commentboardVO comment = new commentboardVO();
	comment.setMsg(msg);
	comment.setBnum(num);
	comment.setUserid(userid);
	
	BoardDAO dao = BoardDAO.getinstance();
	dao.commentInsert(comment);
	
	}
	
%>