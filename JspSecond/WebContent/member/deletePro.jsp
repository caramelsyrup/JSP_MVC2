<%@page import="com.member.MemberVO"%>
<%@page import="com.member.MemberDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="user" class="com.member.MemberVO"></jsp:useBean>
<jsp:setProperty property="*" name="user"/>    
<%
	request.setCharacterEncoding("UTF-8");
	String userID = request.getParameter("userID");
	MemberDAOImpl dao = MemberDAOImpl.getinstance();
	dao.memberDelete(userID);
	response.sendRedirect("userList.jsp");
%>    