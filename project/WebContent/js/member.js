var exp = /^[0-9]{3}[0-9]{4}[0-9]{4}$/;

//id값이 있는 경우에는 document부터
$(document).ready(function(){
	$("#send").click(function(){
		
		if($("#userId").val()==""){
			alert('아이디를 입력하세요.');
			$("#userId").focus();
			return false;
		}
		if($("#pwd").val()==""){
			alert('비밀번호를 입력하세요.');
			$("#pwd").focus();
			return false;
		}
		if($("#name").val()==""){
			alert('이름을 입력하세요.');
			$("#name").focus();
			return false;
		}
		if($("#email").val()==""){
			alert('이메일을 입력하세요.');
			$("#email").focus();
			return false;
		}
		if(!$("#phone").val().match(exp)){
			alert('전화번호를 입력하세요.');
			$("#phone").focus();
			return false;
		}
		$("#frm").submit();
	});
	
	// 아이디 중복확인 페이지 이동
	$("#idChkBtn").click(function(){
		window.open("idCheckForm.me","","width=800 height=600");
	});
	
	// 아이디 중복확인 기능
	$("#idChkF").click(function(){
		if($("#usr").val()==""){
			alert("아이디를 입력하세요.");
			$("#usr").focus();
			false;
		}
		$.ajax({
			type:"post",
			url:"idCheckForm.me",
			// userId라는 이름으로 id가 usr인 곳의 데이터를 저장.
			data:{"userId":$("#usr").val()},
			success:function(val){
				// 데이터를 url에 저장되어 있는 메소드와 비교
				if(val.trim()=="yes"){
					alert("사용가능");
					$(opener.document).find("#userId").val($("#usr").val());
					$(opener.document).find("#uid").val($("#usr").val());
					self.close();
				}else if(val.trim()=="no"){
					alert("사용불가능");
					$("#userId").val("");
				}
			},
			error:function(e){
				alert("error : "+e);
			}
		});	// ajax
	});	// click
	
	
});	// document

function del(userId){
	
		$.getJSON("userDelete.me?userId="+userId,function(data){
			var htmlStr="";
			$.each(data.root,function(key,val){
				htmlStr+="<tr>";
				htmlStr+="<td>"+val.userid+"</td>";
				htmlStr+="<td>"+val.username+"</td>";
				htmlStr+="<td>"+val.useremail+"</td>";
				htmlStr+="<td>"+val.usertel+"</td>";
				htmlStr+="<td>"+val.mode+"</td>";
				if(val.mode=="일반회원"){
					htmlStr+="<td onclick=\"del('"+val.userid+"')\">삭제</td>";
				}else{
					htmlStr+="<td>삭제아님</td>";
				}
				htmlStr+="</tr>";
			});
			$("table tbody").html(htmlStr);
			$("#cntSpan").text(data.rootCount.count);
		});
	
}
