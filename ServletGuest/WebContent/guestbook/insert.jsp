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
		// 페이지 로드시 전체 리스트 페이징
		getdata(1,"","");
		
		// 검색버튼
		$("#btnSearch").on("click",function(){
			getdata(1,$("#field").val(),$("#word").val());
		});
		
		// 전송버튼
		$("#send").click(function(){
			if(${sessionScope.login==null}){
				alert("로그인을 하세요.");
				return false;
			}
			var writter = $("#writter").val();
			var content = $("#content").val();
			var grade = $("input:radio[name=grade]:checked").val();
			var postString = "writter="+writter+"&content="+content+"&grade="+grade;	// 쿼리형식
			$.ajax({
				type:"post",
				url:"create.gb",
				data:postString,	// {"name":$("#name").val()} 의 형식은 JSON 방식의 데이터
				success:function(d){
					$("#result").html(d);
				},
				
				beforeSend:showRequest,	// url에 가기전에 먼저 처리하게 하는 함수
				
				error:function(e){
					alert("error : "+e);
				}
			});	//$.ajax
		});	// click
	});	// document
	
	// 검색 수정
	function getdata(pageNum,field,word){
		$.get("list.gb",
				{"pageNum":pageNum,"field":field,"word":word},
					function(d){
				$("#result").html(d);
			}
		);
	}
	
	// 글 작성 추가시 조건 설정.
	function showRequest(){
		if($("#writter").val()==""){
			alert("이름을 입력하세요.");
			$("#writter").focus();
			return false;
		}
		if($("input:radio[name=grade]:checked").length==0){
			alert("평가를 해주세요.");
			return false;
		}
		return true;
	}	// function showRequest
	
	// 글 작성시, 글자수 체크
	function textCount(obj,target){
		var len = $("#"+obj.id).val().length;
		if(obj.size==len){
			alert("글자수 초과");
			return false;
		}
		$("#"+target).text(len);
	}
	
	// 상세보기 기능 메소드.
	function fview(num){
		$.getJSON("view.gb",
				  {"num":num},
					function(d){
						var htmlStr="";
						htmlStr+="<table border=1>";
						htmlStr+="<tr>";
						htmlStr+="<td>seq</td>";
						htmlStr+="<td>"+d.num+"</td>";
						htmlStr+="<td>작성자</td>";
						htmlStr+="<td>"+d.writter+"</td>";
						htmlStr+="<td>내용</td>";
						htmlStr+="<td>"+d.content+"</td>";
						htmlStr+="<td>평가</td>";
						htmlStr+="<td>"+d.grade+"</td>";
						htmlStr+="<td>작성일</td>";
						htmlStr+="<td>"+d.created+"</td>";
						htmlStr+="<td>주소</td>";
						htmlStr+="<td>"+d.ipaddr+"</td>";
						htmlStr+="</tr>";
						htmlStr+="</table>";
						$("#view").html(htmlStr);
					}
		);
	}
	// 삭제기능
	function fdelete(num,writter){
		if(confirm("["+writter+"]의 게시물을 삭제 할까요?")){
			$.get("delete.gb",
					{"num":num,"writter":writter},
					function(d){
						$("#result").html(d);	
					}
			)	// $.get
		}	//confirm
	}	// fdelete
</script>
</head>
<body>
	<div>
	<c:if test="${sessionScope.login!=null}">
		<ul style="list-style-type: none; display: inline-block;">
			<li>${sessionScope.login} 님 반갑습니다.</li>
			<li><a href="logout.gb">로그아웃</a></li>
		</ul>
	</c:if>
	<c:if test="${login==null}">
		<h4><a href="login.jsp">로그인</a></h4>
	</c:if>	
		<form action="create.gb" method="post">
			<table>
				<tr>
					<td>
						<label for="writter">글쓴이</label>	
						<input id="writter" name="writter" type="text" maxlength="20" onkeyup="textCount(this,'writterCount')">
						*20 글자 이내 (<span id="writterCount" style="color: red">0</span>)
					</td>
				</tr>
				<tr>
					<td>
						<label for="content">내 용</label>
						<input id="content" name="content" type="text" maxlength="70" onkeyup="textCount(this,'contentCount')">
						*70 글자 이내 (<span id="contentCount" style="color: red">0</span>)
					</td>
				</tr>
				<tr>
					<td>
						<label for="grade">평가</label>
						<input id="grade" name="grade" type="radio" value="good">잘함
						<input id="grade" name="grade" type="radio" value="normal">보통
						<input id="grade" name="grade" type="radio" value="fail">노력
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit" value="페이지 넘기기 전송">
						<input type="button" id="send" value="ajax방식 전송">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<br></br>
	<div align="right">
		<form name="search" id="search">
			<select name="field" id="field">
				<option value="writter">이름</option>
				<option value="content">내용</option>
			</select>
			<input type="text" name="word" id="word">
			<input type="button" value="찾기" id="btnSearch">
		</form>
	</div>
	<br></br>
	<div id="result">
	</div>
</body>
</html>