<%@page import="com.member.MemberVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.member.MemberDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가입회원 보기</title>
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script src="member.js"></script>
<%
	request.setCharacterEncoding("UTF-8");
	MemberDAOImpl dao = MemberDAOImpl.getinstance();
	ArrayList<MemberVO> arr = null;
	arr = dao.memberList();
	String loginID = (String)session.getAttribute("USERID");
	int count = dao.memberCount();
%>    
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" 
	  integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
</head>
<body>
<h1>회원관리명단</h1>
<div style="float: right;" >
	현재 회원 수는 <span id="cntSpan" style="font-size: large; color: orange;"><%=count %></span>명 입니다.
	<a href="memberView.jsp"><%=loginID %></a>님 반갑습니다.
	<a href="logout.jsp">로그아웃</a>
	<a href="memberForm.jsp">회원가입하기</a>
	<a href="../board/list.jsp">게시글 보기</a>
</div>
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th scope="col">아이디</th>
					<th scope="col">이름</th>
					<th scope="col">전화번호</th>
					<th scope="col">이메일</th>
					<th scope="col">관리자여부</th>
					<th scope="col">삭제</th>
				</tr>
			</thead>
			<tbody>
<%
				for(MemberVO vo : arr){
					String mode = vo.getAdmin()==0? "일반회원":"관리자";
%>
				<tr>
					<td><%=vo.getUserID()%></td>
					<td><%=vo.getUserName() %></td>
					<td><%=vo.getUserTel() %></td>
					<td><%=vo.getUserEmail() %></td>
					<td><%=mode %></td>
					<td><a href="javascript:del('<%=vo.getUserID()%>','<%=mode%>')"/>삭제</a></td>
				</tr>			
<%
				}
%>		
			</tbody>
			<tfoot>
				<tr>
				</tr>
			</tfoot>
		</table>
</body>
</html>