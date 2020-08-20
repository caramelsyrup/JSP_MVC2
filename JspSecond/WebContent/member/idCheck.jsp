<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 중복 체크</title>
<% request.setCharacterEncoding("UTF-8"); %> 
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script src="member.js"></script>
</head>
<body>
	<h2>아이디 중복확인</h2>
	아이디 <input type="text" name="userID" id="userID">
	<input type="button" value="사용" id="idCheckBtn">
</body>
</html>