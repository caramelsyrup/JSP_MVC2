<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ include file="../include/header.jsp" %>
<div class="container">
  <h2>회원리스트</h2>
  <h3>회원 수 (<span id="cntSpan">${count}</span> 명)</h3>
	<table class="table" id="list">
		<thead class="thead-dark">
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>이메일</th>
				<th>전화번호</th>
				<th>구분</th>
				<th>삭제</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${members}" var="m">
				<tr>
					<td>${m.userID}</td>
					<td>${m.userName}</td>
					<td>${m.userEmail}</td>
					<td>${m.userTel}</td>
				<c:if test="${m.admin==0}">
					<td>일반회원</td>
					<td onclick="del('${m.userID}')">삭제</td>
				</c:if>
				<c:if test="${m.admin==1}">
					<td>관리자</td>
					<td>삭제아님</td>
				</c:if>
				</tr>	
			</c:forEach>
		</tbody>
	</table>
</div>

<%@ include file="../include/footer.jsp" %>

