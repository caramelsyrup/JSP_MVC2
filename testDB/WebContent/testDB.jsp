<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="db.beans.*,java.util.*,java.io.*" %>
<jsp:useBean id="QueryBean" scope="page" class="db.beans.QueryBean"/> 
<jsp:setProperty property="*" name="QueryBean"/>
<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	
	request.setCharacterEncoding("UTF-8");
	
	String id = request.getParameter("id") == null ? "":request.getParameter("id").trim();
	
	// DB에 연결
	QueryBean.getConnection();
	
	ArrayList resArr = new ArrayList();
	
	try{
		if(id.isEmpty()==true){
			resArr = QueryBean.getUserInfo();
		}else{
			resArr = QueryBean.searchUserInfo(id);
		}
		
	}catch(Exception e){
		out.print(e.toString());
	}finally{
		QueryBean.closeConnection();
	}
	// 동적으로 json 객체 구문을 작성, 객체 안에는 JSON ARRAY 가 있고, 이 배열의 원소는 json 객체들 이다.
	out.print("{");
	out.print("\"datas\":[");
	
	if(resArr.size()==0){
		out.print("]");
		out.print("}");
	}else{
		out.print("{");
		out.print("\"ID\": \""	+	(String)resArr.get(0)+"\",");
		out.print("\"NAME\": \""+	(String)resArr.get(1)+"\",");
		out.print("\"PHONE\": \""+	(String)resArr.get(2)+"\",");
		out.print("\"GRADE\": \""+	(String)resArr.get(3)+"\",");
		out.print("\"WRITE_TIME\": \""+	(String)resArr.get(4)+"\" ");
		out.print("} ");
		
		for(int i=5; i<resArr.size(); i+=5){
			out.print(",");
			out.print("{");
			out.print("		\"ID\": \""	+	(String)resArr.get(i)+"\",");
			out.print("		\"NAME\": \""+	(String)resArr.get(i+1)+"\",");
			out.print("		\"PHONE\": \""+	(String)resArr.get(i+2)+"\",");
			out.print("		\"GRADE\": \""+	(String)resArr.get(i+3)+"\",");
			out.print("		\"WRITE_TIME\": \""+	(String)resArr.get(i+4)+"\" ");
			out.print("} ");
		}
		out.print("]");
		out.print("}");
		
	}
	
%>