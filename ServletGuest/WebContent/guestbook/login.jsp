<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="login.gb" method="posh">
		<table>
			<tr>
				<td><label for="id">ID</label></td>
				<td><input id="id" name="id" type="text" size="10"></td>
			</tr>
			<tr>
				<td><label for="pw">PW</label></td>
				<td><input id="pw" name="pw" type="password" size="10"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="로그인"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<span style="color: red;">${errMsg}</span>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>