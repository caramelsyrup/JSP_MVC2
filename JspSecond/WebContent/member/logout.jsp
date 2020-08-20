
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	// 모든 세션 지우기.
	session.invalidate();
	// 일부 지우기.
//	session.removeAttribute("USERID");
	response.sendRedirect("loginForm.jsp");
%>