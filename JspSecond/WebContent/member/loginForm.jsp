<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인화면</title>
<%request.setCharacterEncoding("UTF-8"); %>
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script type="text/javascript">
	$(function(){
		$("#loginBtn").click(function(){
			if($("#userID").val()==""){
				alert("아이디를 입력하세요.");
				$("#userID").focus();
				return false;
			}
			if($("#userPwd").val()==""){
				alert("암호를 입력하세요.");
				$("#userPwd").focus();
				return false;
			}
			$.ajax({
				type:"post",
				url:"loginPro.jsp",
				data:{"userID":$("#userID").val(),"userPwd":$("#userPwd").val()},
				success:function(value){
//					alert(value.trim());
					if(value.trim()==-1){
						alert("회원아닙니다.");
					}else if(value.trim()==0){
						alert("일반회원.");
						// 제이쿼리
						$(location).attr("href","memberView.jsp");
						
					}else if(value.trim()==1){
						alert("관리자.");
						// 자바스크립트
						location.href="userList.jsp"
					}else if(value.trim()==2){
						alert("암호가아닙니다.");
					}
				},
				error:function(e){
					alert("error : "+e);
				}
			});	// $.ajax
		});	// click
	});	// document
</script>
</head>
<body>
	<h2>로그인</h2>
	<form action="loginPro.jsp" method="post" id="frm">
		<table>
			<tr>
				<td>
					<label for="userID">아이디</label>
				</td>
				<td>
					<input type="text" name="userID" id="userID">
				</td>
			</tr>
				<td>
					<label for="userPwd">암호</label>
				</td>
				<td>
					<input type="password" name="userPwd" id="userPwd">
				</td>
			<tr>
				<td colspan="2">
					<input type="button" value="로그인" id="loginBtn">
					<input type="reset" value="취소">
					<input type="button" id="join" onclick="location.href='memberForm.jsp'" value="회원가입">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>