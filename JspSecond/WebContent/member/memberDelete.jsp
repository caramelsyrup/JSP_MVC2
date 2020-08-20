<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="com.member.MemberVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.member.MemberDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	MemberDAOImpl dao = MemberDAOImpl.getinstance();
	String userID = request.getParameter("userID");
	dao.memberDelete(userID);
	ArrayList<MemberVO>arr=dao.memberList();
	int count = dao.memberCount();
	// 실시간으로 카운트가 집계가 반영되도록 하려고, json을 사용하고, 오브젝트와 어레이를 하나 더 만들어줘야한다.
	// 기존의 것에 사용하기에는 다른 형태의 데이터이기 때문이다.
	// 먼저 회원 수 메서드를 담기 위해 json 오브젝트를 만든다.
	JSONObject countObj = new JSONObject();
	// 만든 객체countObj에 count이름으로 memberCount의 리턴 값을 넣은 변수를 value로 넣고, count라는 키로 설정.
	countObj.put("count", count);
	
	// 아래의 jarr의 배열 데이터 그리고 countObj의 데이터 , 두개를 다 담을 JSONObject를 만든다.
	JSONObject mainObj = new JSONObject();
	
	JSONArray jarr = new JSONArray();
	for(MemberVO vo: arr){
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
	// JSONArray의 객체 jarr을 mainObj에 넣는다.
	mainObj.put("jarr",jarr);
	// 그리고 mainObj에 countObj도 넣는다.
	mainObj.put("cntObj",countObj);
	// 마지막으로 mainObj을 출력.
	out.println(mainObj.toString());
	

%>