<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
<form action="updateAction.amy" method="get" name="update" id="update">
<input type="hidden" value="${view.num}" name="num" id="num">
	<table>
		<tr>
			<td colspan="2">Servlet_mybatis 주소록 상세보기</td>
		</tr>
		<tr>
			<td colspan="2"><a href="listAction.amy">목록</a></td>
		</tr>
		<tr>
			<th align="left">이름</th>
			<td><input type="text" name="name" id="name" value="${view.name}"></td>
		</tr>
		<tr>
			<th align="left">우편번호</th>
			<td>
				<input type="text" name="zipcode" id="zipCode" size="10" value="${view.zipcode}">
				<input type="button" value="검색"	 id="zipBtn">
			</td>
		</tr>
		<tr>
			<th align="left">주소</th>
			<td><input type="text" name="address" id="address" size="60" value="${view.address}"></td>
		</tr>
		<tr>
			<th align="left">전화번호</th>
			<td><input type="text" name="tel" id="tel" value="${view.tel}"></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="수정" id="btn" >
				<input type="reset" value="취소" >
				<input type="button" value="삭제" onclick="del('${view.num}')">
					<script type="text/javascript">
						function del(num){
							location.href="deleteAction.amy?num="+num;
						}
					</script>
			</td>
		</tr>
	</table>
</form>
</body>
</html>