<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입페이지</title>
<% request.setCharacterEncoding("UTF-8"); %> 
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script src="member.js"></script>
<style type="text/css">
	
</style>
</head>
<body>
<form action="memberPro.jsp" id="frm" method="get">
<input type="hidden" name="uid" id="uid">
	<fieldset name="userCreate">
		<legend><strong>회원 가입</strong></legend>
		<table>
			<tr>
				<th>이름</th>
				<td>
					<input type="text" name="userName" id="userName" >*
				</td>
			</tr>
			<tr>
				<th>아이디</th>
				<td>
					<input type="text" name="userID" id="userID" disabled="disabled">*
					<input type="button" name="idChk" id="idChk" value="중복체크">
				</td>
			</tr>
			<tr>
				<th>암호</th>
				<td><input type="password" name="userPwd" id="userPwd" >*</td>
			</tr>
			<tr>
				<th>암호 확인</th>
				<td><input type="password" name="userPwdchk" id="userPwdchk" >*</td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="email" name="userEmail" id="userEmail"></td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td><input type="text" name="userTel" id="userTel"></td>
			</tr>
			<tr>
				<th>등급</th>
				<td>
					<input type="radio" name="admin" id="admin" value="0" >일반회원
					<input type="radio" name="admin" id="admin" value="1">관리자
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="확인" id="send">
					<input type="reset" value="취소">
			    </td>
			</tr>
		</table>
	</fieldset>
</form>
</body>
</html>