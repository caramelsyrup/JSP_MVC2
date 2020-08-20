<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주소록예제</title>
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script type="text/javascript">
	function zipfinder(){
		window.open("zipAction.amy","","width=700 height=400");
	}
</script>
</head>
<body>
<h1>주소록 등록하기</h1>
<a href="listAction.amy">주소록가기</a>
<form action="insertAction.amy" method="post" name="enroll" id="enroll">
	<table>
		<tr>
			<td colspan="2">Servlet_mybatis 주소록 등록</td>
		</tr>
		<tr>
			<th align="left">이름</th>
			<td><input type="text" name="name" id="name"></td>
		</tr>
		<tr>
			<th align="left">우편번호</th>
			<td>
				<input type="text" name="zipcode" id="zipCode" size="10">
				<input type="button" value="검색"	 id="zipBtn" onclick="zipfinder()">
			</td>
		</tr>
		<tr>
			<th align="left">주소</th>
			<td><input type="text" name="address" id="address" size="60"></td>
		</tr>
		<tr>
			<th align="left">전화번호</th>
			<td><input type="text" name="tel" id="tel"></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="등록" id="btn" >
				<input type="reset" value="취소" >
			</td>
		</tr>
	</table>
</form>
</body>
</html>