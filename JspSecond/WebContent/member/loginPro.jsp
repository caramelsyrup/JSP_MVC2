<%@page import="com.member.MemberDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String userID = request.getParameter("userID");
	String userPwd	=	request.getParameter("userPwd");
	MemberDAOImpl dao = MemberDAOImpl.getinstance();
	int flag = dao.loginCheck(userID,userPwd);	// DB와 연동하여 이미 회원 인증 됨.
	if(flag==0 || flag==1){
		// session에는 특정 값을 넣어도 되지만, 객체를 넣어도 된다.
		// ("구체적인 명칭",특정값(객체))
		session.setAttribute("USERID", userID);
	}
	
	out.println(flag);	// 결과를 출력해서 넘김.
%>