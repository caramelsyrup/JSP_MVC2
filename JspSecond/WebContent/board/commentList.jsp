<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="com.board.commentboardVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
    int num = Integer.parseInt(request.getParameter("num"));
    BoardDAO dao =  BoardDAO.getinstance();
    ArrayList<commentboardVO> arr = dao.commentList(num);
    JSONArray jarr = new JSONArray();
    for(commentboardVO cb : arr){
    	JSONObject obj = new JSONObject();
    	obj.put("bnum",cb.getBnum());
    	obj.put("cnum",cb.getCnum());
    	obj.put("msg",cb.getMsg());
    	obj.put("userid",cb.getUserid());
    	obj.put("regdate",cb.getRegdate());
    	jarr.add(obj);
    }
    out.println(jarr.toString());
%>
