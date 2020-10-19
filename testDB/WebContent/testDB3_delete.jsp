<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="db.beans.* , java.util.* , java.sql.* , java.io.* " %>
<jsp:useBean id="QueryBean" scope="page" class="db.beans.QueryBean"/>
<jsp:setProperty property="*" name="QueryBean"/>
<%
	// 캐쉬제거
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
	// 인코딩 설정
	response.setCharacterEncoding("UTF-8");
	// 전송되는 파라미터 값 받기
	String id = request.getParameter("id") == null ? "":request.getParameter("id").trim();
	
	System.out.println("삭제할 ID : "+id);
	
	QueryBean.getConnection();
	
	int res = 0;
	
	try{
		res = QueryBean.deleteUserInfo(id);
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		QueryBean.closeConnection(); 
	}
	
	out.print("[");
	out.print("{");
	out.print("\"RESULT_OK\": \""+res+"\" ");
	out.print("}");
	out.print("]");
	
	System.out.println("res" + res);
	

%>