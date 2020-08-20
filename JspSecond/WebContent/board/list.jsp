<%@page import="com.board.BoardVO"%>
<%@page import="com.board.BoardDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>	    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<%
	//페이지 번호 설정
	String pageNum = request.getParameter("pageNum");
	//초기페이지 1로 설정
	if(pageNum==null){
		pageNum = "1";
	}
	// 문자열을 숫자화
	int currentPage = Integer.parseInt(pageNum);
	// 한페이지에서 나올 최대 수
	int pageSize = 5;
	BoardDAO dao = BoardDAO.getinstance();
	int startRow = (currentPage - 1)*pageSize+1;
	int endRow = currentPage*pageSize;
	String field = "", word="";
	ArrayList<BoardVO> arr = null;
	int count =0;
	if(request.getParameter("word")!=null&&!request.getParameter("word").equals("")){
		field = request.getParameter("field");
		word = request.getParameter("word");
		arr = dao.boardList(field,word,startRow,endRow);
		count = dao.boardCount(field, word);
	}else{
		// 모든 게시글 수치가 다 세어진다. 하지만 페이지를 나눠 버리면 의미가?
		arr = dao.boardList(startRow,endRow);
		count = dao.boardCount();	
	}
	String userid = (String)session.getAttribute("USERID");
%>    
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" 
	  integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
</head>
<body>
<h1>게시판</h1>
<div align="left">
	현재 게시글 수는 <span id="cntSpan"><%=count %></span>개 입니다.
	<%
		if(userid!=null){
	%>
	<%=userid %>님 반갑습니다.
	<a href="../member/logout.jsp">로그아웃</a>
	<%
		}
	%>
	<a href="writeForm.jsp">글쓰기</a>
</div>
	<form action="list.jsp" name="search" method="get">
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th scope="col">번호</th>
					<th scope="col">제목</th>
					<th scope="col">작성자</th>
					<th scope="col">작성일</th>
					<th scope="col">조회수</th>
					<th scope="col">IP주소</th>
				</tr>
			</thead>
			<tbody>
<%
			for(BoardVO vo : arr){
%>
				<tr>
					<td><%=vo.getNum() %></td>
					<td><a href="boardView.jsp?num=<%=vo.getNum() %>"><%=vo.getSubject() %></a></td>
					<td><%=vo.getWriter() %></td>
					<td><%=vo.getReg_date() %></td>
					<td><%=vo.getreadcount() %></td>
					<td><%=vo.getIp() %></td>
				</tr>			
<%
				}
%>		
			</tbody>
			<tfoot>
				<tr>
					<td colspan="6" align="center">
						<select name="field" size="1">
							<option value="subject">제목</option>
							<option value="writer">작성자</option>
						</select>
						<input type="text" size="15" name="word">
						<input type="submit" value="검색">
					</td>
				</tr>
			</tfoot>
		</table>
	</form>
<div align="center">
<%
	// 페이지 나누기, 한번에 5개의 페이지가 뜨고 블록은 3개씩 잡는다.
	if(count>0){
		int pagecount = (count/pageSize)+(count%pageSize==0?0:1);
		int pageBlock = 3;
		int startPage = (int)((currentPage-1)/pageBlock)*pageBlock+1;
		int endPage = startPage+pageBlock-1;
		if(endPage > pagecount ){
			endPage = pagecount;
		}
	
		//이전
		if(startPage>pageBlock){
%>
			<a href="list.jsp?pageNum=<%=startPage-pageBlock %>&field=<%=field %>&word=<%=word %>">[이전]</a>
<% 			
		}

		// for문
		for(int i=startPage; i<=endPage;i++){
%>	
			<a href="list.jsp?pageNum=<%=i %>&field=<%=field %>&word=<%=word %>"><%=i %></a>
<% 					
		}
	//다음
		if(endPage<pagecount){		
%>	
		<a href="list.jsp?pageNum=<%=endPage+pageBlock %>&field=<%=field %>&word=<%=word %>">[다음]</a>		
<%
		}
	}
%>	
</div>
</body>
</html>