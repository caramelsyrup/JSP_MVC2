<%@page import="com.member.MemberVO"%>
<%@page import="com.member.MemberDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="vo" class="com.member.MemberVO"></jsp:useBean>
<jsp:setProperty property="*" name="vo"/>
<%
	MemberDAOImpl dao = MemberDAOImpl.getinstance();
	int flag = dao.memberUpdate(vo);
	if(flag==1){	// 수정 성공.
		//세션 정보는 없애주면서 다시 로그인 화면으로
		session.invalidate();
		response.sendRedirect("loginForm.jsp");
	}
	
%>