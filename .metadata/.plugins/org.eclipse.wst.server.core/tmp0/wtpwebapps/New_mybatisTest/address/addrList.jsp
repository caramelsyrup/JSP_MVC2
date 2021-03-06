<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script type="text/javascript">

$(document).ready(function(){
	$("#btnSearch").click(function(){
		$.getJSON("searchAction.amy",
					{"field":$("#field").val(),"word":$("#word").val()},
					function(data){
						$("#count").html("검색 된 총 게시물 수 : "+data.count);
						var htmlStr="";
						$.each(data.sarr,function(key,val){
							htmlStr+="<tr>";
							htmlStr+="<td>"+val.name+"</td>";
							htmlStr+="<td>"+val.zipcode+"</td>";
							htmlStr+="<td>"+val.address+"</td>";
							htmlStr+="<td>"+val.tel+"</td>";
							htmlStr+="<td>"+val.num+"</td>";
							htmlStr+="</tr>";
						}) // each
						$("table tbody").html(htmlStr);
					} // callback
					
		); // getJSON
	}); // click
}); //document

function fdelete(num){
	$.getJSON("deleteAjaxAction.amy",
				{"num":num},
				function(d){
					var htmlStr ="";
					$.each(d.list,function(key,val){
						var num = val.num;
						htmlStr+="<tr>";
						htmlStr+="<td><a href='viewAction.amy?num="+num+"'>"+val.name+"</a></td>";
						htmlStr+="<td>"+val.zipcode+"</td>";
						htmlStr+="<td>"+val.address+"</td>";
						htmlStr+="<td>"+val.tel+"</td>";
						htmlStr+="<td onclick='fdelete("+num+")'>삭제</td>";
						htmlStr+="</tr>";
					})
					$("table tbody").html(htmlStr);
					$("#count").html("총 게시물 수는 "+d.count+"입니다.");
				}
	);
} 

</script>
</head>
<body>
	<h2>주소록 목록</h2>
	<div><span id="count">전체 게시물 수는 ${count} 개입니다.</span></div>
	<table>
	<thead>
		<tr>
			<th colspan="4"><a href="addressInsert.jsp">주소 추가하기</a></th>
		</tr>
		<tr>
			<th>이름</th>
			<th>우편번호</th>
			<th>주소</th>
			<th>전화번호</th>
			<th>삭제</th>
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${arr}" var="a">
		<input type="hidden" value="${a.num}" name="num"/>
		<tr>
			<td>
				<a href="viewAction.amy?num=${a.num}">${a.name}</a>
			</td>
			<td>
				${a.zipcode}
			</td>
			<td>
				${a.address}
			</td>
			<td>
				${a.tel}
			</td>
			<td onclick="fdelete(${a.num})">
				삭제
			</td>
		</tr>
		</c:forEach>
	</tbody>	
	</table>
	<div>
		<form action="searchAction.amy" method="get" name ="search" id="search">
			<select name="field" id="field">
				<option value="name">이름</option>
				<option value="tel">전화번호</option>
			</select>
			<input type="text" name="word" id="word">
			<input type="button" value="찾기" id="btnSearch">
		</form>
	</div>
	
</body>
</html>