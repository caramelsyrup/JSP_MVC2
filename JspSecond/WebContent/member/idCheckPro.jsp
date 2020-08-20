<%@page import="com.member.MemberDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	MemberDAOImpl dao = MemberDAOImpl.getinstance();
	String userID = request.getParameter("userID");
	String flag = dao.idCheck(userID);
	out.println(flag);

%>