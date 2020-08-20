<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Bootstrap Example</title>
  <style>
  h2{
  	color: orange;
  }
  h3{
  	color: purple;
  }
  #list thead tr {
	  text-align: center;
  }
  #list tbody tr {
  	text-align: center;
  }
  #del {
  	color: rgb(200,50,50);
  }
  </style>
  <% request.setCharacterEncoding("utf-8"); %>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
  <script src="/project/js/member.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-sm bg-light">
  <ul class="navbar-nav">
<c:choose>
	<c:when test="${empty sessionScope.userId}">
	    <li class="nav-item">
	  	  <a class="nav-link" href="/project/member/login.me">로그인</a>
	    </li>
		<li class="nav-item">
      		<a class="nav-link" href="/project/member/insert.me">회원가입</a>
    	</li>
	</c:when>	    
  <c:otherwise>
	<li class="nav-item">
      <a class="nav-link" href="/project/member/logout.me">로그아웃</a>
    </li>
   	<li class="nav-item">
      <a class="nav-link" href="/project/member/view.me">정보변경</a>
    </li>
   	<li class="nav-item">
      <a class="nav-link" href="/project/member/delete.me">회원탈퇴</a>
    </li>
  </c:otherwise>
</c:choose>
    <li class="nav-item">
      <a class="nav-link" href="/project/board/board.me">게시판</a>
    </li>
  </ul>
 <div>
   	<c:if test="${not empty seesionScope.userId}">
		<span class="navbar-text" >
			${sessionScope.userId}님 반갑습니다.
		</span>
	</c:if>
</div>
</nav>

