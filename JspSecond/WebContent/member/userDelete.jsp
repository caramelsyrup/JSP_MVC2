<%@page import="com.member.MemberVO"%>
<%@page import="com.member.MemberDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String userID = (String)session.getAttribute("USERID");
	request.setCharacterEncoding("UTF-8");
	MemberDAOImpl dao = MemberDAOImpl.getinstance();
	dao.memberDelete(userID);
	session.invalidate();
	response.sendRedirect("loginForm.jsp");
%>