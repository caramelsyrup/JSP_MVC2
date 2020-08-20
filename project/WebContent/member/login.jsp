<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<div class="container">
  <h2>LOGIN form</h2>
  <form action="login.me" method="post">
    <div class="form-group">
      <label for="userId">UserId:</label>
      <input type="text" class="form-control" id="userId" placeholder="userId" name="userId">
    </div>
    <div class="form-group">
      <label for="pwd">Password:</label>
      <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="pwd">
    </div>
    <div class="form-group form-check">
      <label class="form-check-label">
        <input class="form-check-input" type="checkbox" name="remember"> Remember me
      </label>
    </div>
    <button type="button" class="btn btn-primary" id="loginBtn">Submit</button>
    <button type="reset" class="btn btn-primary">Cancel</button>
  </form>
</div>
<script type="text/javascript">
$("#loginBtn").click(function(){
	if($("#userId").val()==""){
		alert("아이디를 입력하세요.");
		$("#userId").focus();
		return false;
	}
	if($("#pwd").val()==""){
		alert("암호를 입력하세요.");
		$("#pwd").focus();
		return false;
	}
	$.ajax({
		type:"post",
		url:"login.me",
		data:{"userId":$("#userId").val(),"pwd":$("#pwd").val()},
		success :function(value){
			if(value.trim()==-1){
				alert("회원아닙니다.");
				location.href="insert.me";
			}else if(value.trim()==0){
				alert("일반회원.");
				// 제이쿼리
//				$(location).attr("href","view.me");
				location.href="view.me";
			}else if(value.trim()==1){
				alert("관리자.");
				// 자바스크립트
				// get 방식으로 넘어감. get은 보통 조회 할때 쓰는 방식
				location.href="list.me";
			}else if(value.trim()==2){
				alert("암호가아닙니다.");
			}
		},
		error : function(e){
			alert("error : "+e);
		}
	});	// $.ajax
});	// loginBtn
</script>
<%@ include file="../include/footer.jsp" %>
