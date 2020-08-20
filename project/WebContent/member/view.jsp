<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ include file="../include/header.jsp" %>

<h2>회원정보상세</h2>
<h3>${sessionScope.userId} 님 반갑습니다.</h3>
<form action="updatePro.jsp" method="post">
<input type="hidden" name="userid" value="">
	<fieldset name="userInfo">
		<legend><strong>회원 정보</strong></legend>
		<table>
			<tr>
				<th>이름</th>
				<td>
					<input type="text" name="userName" id="userName" value="${member.userName}">
				</td>
			</tr>
			<tr>
				<th>아이디</th>
				<td>
					<input type="text" name="userID" id="userID" value="${member.userID}" readonly="readonly">
				</td>
			</tr>
			<tr>
				<th>암호</th>
				<td><input type="password" name="userPwd" id="userPwd" value="${member.userPwd}" disabled="disabled"></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="email" name="userEmail" id="userEmail" value="${member.userEmail}"></td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td><input type="text" name="userTel" id="userTel" value="${member.userTel}"></td>
			</tr>
			<tr>
				<th>등급</th>
				<td>
					<input type="radio" name="admin" id="admin" value="0">일반회원
					<input type="radio" name="admin" id="admin" value="1">관리자
					<script type="text/javascript">
						if(${member.admin}==0){
							$("input:radio[value='0']").prop("checked",true);
						}else{
							$("input:radio[value='1']").prop("checked",true);
						}
					</script>
				</td>
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



<%@ include file="../include/footer.jsp" %>