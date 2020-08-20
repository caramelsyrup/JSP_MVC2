// 정규식 표현
var exp = /^[0-9]{3}[0-9]{4}[0-9]{4}$/;


$(document).ready(function(){
	$("#send").click(function(){
		if($("#userName").val()==""){
			alert("이름을 입력하세요");
			$("#userName").focus();
			return false;
		}
		//아이디,비번,전화번호
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
		if($("#userPwdchk").val()==""){
			alert("암호확인을 입력하세요.");
			$("#userPwdchk").focus();
			return false;
		}
		if($("#userPwd").val() != $("#userPwdchk").val()){
			alert("암호를 확인해 주세요.")
			return false;
		}
		if(!$("#userTel").val().match(exp)){
			alert("전화번호 양식을 맞춰주세요.");
			$("#userTel").focus();
			return false;
		}
		
		$("#send").submit();
	});
	
	$("#idChk").click(function(){
		window.open("idCheck.jsp","","width=800 height=600");
	});
	
	$("#idCheckBtn").click(function(){
		if($("#userID").val()==""){
			alert("아이디를 입력하세요.");
			$("#userID").focus();
			return false;
		}
		$.ajax({
			type:"post",
			url:"idCheckPro.jsp",
			data:{"userID":$("#userID").val()},
			// 성공시 idCheckPro파일의 실행 된 결과를 출력해냄.
			success:function(userID){
				if(userID.trim()=="yes"){
					alert("사용 가능한 아이디 입니다.");
					$(opener.document).find("#userID").val($("#userID").val());
					$(opener.document).find("#uid").val($("#userID").val());
					self.close();
				}
				else{
					alert("사용 불가능한 아이디 입니다.");
				}
			},
			error:function(e){
				alert("error:"+e);
			}
		});	// $.ajax
	});	// click
});	// document

function del(userID,mode){
	if(mode=="관리자"){
		alert("관리자는 삭제 불가능");
		return;
	}
	$.getJSON("memberDelete.jsp",{"userID":userID},
			function(data){
					var htmlStr="";
					$.each(data.jarr,function(key,val){
						htmlStr+="<tr>";
						htmlStr+="<td>"+val.userID+"</td>";
						htmlStr+="<td>"+val.userName+"</td>";
						htmlStr+="<td>"+val.userTel+"</td>";
						htmlStr+="<td>"+val.userEmail+"</td>";
						htmlStr+="<td>"+val.mode+"</td>";
	//					htmlStr+="<td><a href='userList.jsp'>삭제</a></td>";
						htmlStr+="<td><a href=javascript:del('"+val.userID+"','"+val.mode+"')>삭제2</a></td>";
						htmlStr+="</tr>";
					});
					$("table tbody").html(htmlStr);
					$("#cntSpan").text(data.cntObj.count);
			}	//콜백함수
	);	// getJSON
}	// del
