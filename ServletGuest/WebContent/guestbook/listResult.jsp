<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.1/css/bootstrap.min.css" integrity="sha384-VCmXjywReHh4PwowAiWNagnWcLhlEJLA5buUprzK8rxFgeH0kww/aWY76TfkUoSX" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.1/js/bootstrap.min.js" integrity="sha384-XEerZL0cuoUbHE4nZReLT7nx9gQrQreJekYhJD9WNWhH8nEW+0c5qq7aIo2Wl30J" crossorigin="anonymous"></script>
<h1 class="display-4">현재 개수는 ${count}</h1>
	<table class="table table-dark">
		<thead>
			<tr>
				<th scope="col">seq</th>
				<th scope="col">번호</th>
				<th scope="col">작성자</th>
				<th scope="col">내용</th>
				<th scope="col">평가</th>
				<th scope="col">작성일</th>
				<th scope="col">아이피</th>
				<c:if test="${login!=null}">
					<th scope="col">삭제</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="guest" varStatus="st">
				<tr>
					<th scope="row">
						${guest.num}
					</th>
					<td>
						${rowNo-st.index}
					</td>
					<td>
						<a href="javascript:fview(${guest.num})">${guest.writter}</a>
					</td>
					<td>
						${guest.content}
					</td>
					<td>
						${guest.grade}
					</td>
					<td>
						${guest.created}
					</td>
					<td>
						${guest.ipaddr}
					</td>
					<c:if test="${sessionScope.login!=null}">
						<td>
							<a href="javascript:fdelete(${guest.num},'${guest.writter}')">삭제</a>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>	
	</table>
	<div align="center">
		<!-- 이전 -->
		<c:if test="${pu.startPage>pu.pageBlock}">
			<a href="javascript:getdata(${pu.startPage-pu.pageBlock},'${pu.field}','${pu.word}')">[이전]</a>
		</c:if>
		<!-- 페이지 출력 -->
		<c:forEach begin="${pu.startPage}" end="${pu.endPage}" var="i">
			<c:if test="${i==pu.currentPage}">
				<c:out value="${i}"/>
			</c:if>
			<c:if test="${i!=pu.currentPage}">
				<a href="javascript:getdata(${i},'${pu.field}','${pu.word}')">${i}</a>
			</c:if>
		</c:forEach>
		<!-- 다음 -->
		<c:if test="${pu.endPage<pu.totalpage}">
			<a href="javascript:getdata(${pu.endPage+1},'${pu.field}','${pu.word}')">[다음]</a>
		</c:if>
	</div>
	<hr>
	<div id="view">
	</div>