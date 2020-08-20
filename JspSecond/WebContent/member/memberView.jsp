
<%@page import="com.member.MemberVO"%>
<%@page import="com.member.MemberDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반회원 로그인</title>
<%
	request.setCharacterEncoding("UTF-8");
	String userID = (String)session.getAttribute("USERID");
	MemberDAOImpl dao = MemberDAOImpl.getinstance();
	MemberVO memeber = dao.memberDetail(userID);
%>
</head>
<script>
	if(<%=memeber.getAdmin()%>==0){
		$("input:radio[value='0']").prop("checked",true);
	}else{
		$("input:radio[value='1']").prop("checked",true);
	}
</script>
<%=userID %>님 반갑습니다. / <a href="logout.jsp">로그아웃</a>
<h3>회원정보변경 / <a href="userDelete.jsp">회원탈퇴</a></h3>
<body>
	<form action="updatePro.jsp" method="post">
	<input type="hidden" name="userid" value="<%=userID %>">
		<fieldset name="userInfo">
			<legend><strong>회원 정보</strong></legend>
			<table>
				<tr>
					<th>이름</th>
					<td>
						<input type="text" name="userName" id="userName" value="<%=memeber.getUserName() %>">
					</td>
				</tr>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" name="userID" id="userID" value="<%=memeber.getUserID()%>">
					</td>
				</tr>
				<tr>
					<th>암호</th>
					<td><input type="password" name="userPwd" id="userPwd" value="<%=memeber.getUserPwd() %>" disabled="disabled"></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td><input type="email" name="userEmail" id="userEmail" value="<%=memeber.getUserEmail() %>"></td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td><input type="text" name="userTel" id="userTel" value="<%=memeber.getUserTel() %>"></td>
				</tr>
				<tr>
					<th>등급</th>
					<td>
						<input type="radio" name="admin" id="admin" value="0">일반회원
						<input type="radio" name="admin" id="admin" value="1">관리자</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="수정" id="send">
						<input type="reset" value="취소">
				    </td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>