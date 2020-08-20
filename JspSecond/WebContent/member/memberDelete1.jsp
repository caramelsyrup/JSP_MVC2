<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="com.member.MemberVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.member.MemberDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String userID = request.getParameter("userID");
	MemberDAOImpl dao = MemberDAOImpl.getinstance();
	dao.memberDelete(userID);
	ArrayList<MemberVO> arr = dao.memberList();
	JSONArray jarr = new JSONArray();
	for(MemberVO vo : arr){
		String mode = vo.getAdmin()==0? "일반회원":"관리자";
		JSONObject obj = new JSONObject();
			obj.put("userName",vo.getUserName());
			obj.put("userID", vo.getUserID());
			obj.put("userPwd", vo.getUserPwd());
			obj.put("userEmail", vo.getUserEmail());
			obj.put("userTel", vo.getUserTel());
			obj.put("mode", mode);
			jarr.add(obj);
	}
	out.println(jarr.toString());
%>