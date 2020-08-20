<%@page import="com.member.MemberDAOImpl"%>
<%@page import="com.member.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="vo" class="com.member.MemberVO"></jsp:useBean>
<jsp:setProperty property="*" name="vo"/>
<%
	request.setCharacterEncoding("UTF-8");
	MemberDAOImpl dao = MemberDAOImpl.getinstance();
	String uid = request.getParameter("uid");
	vo.setUserID(uid);
	dao.memberInsert(vo);
	response.sendRedirect("loginForm.jsp");
%>