<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.content{
	margin-bottom: 10px;
}	
.content table{
		border: 2px solid red;
	}
.content2{
	margin: 10px auto;
}
.content2 talbe{
	border: 3px solid blue;
}	
</style>
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#send").click(function(){
		if($("#dong").val()==""){
			alert('동이름을 입력하세요.');
			return false;
		}
		$.post("zipAction.amy",				// 해당 주소로 간다
				{"dong":$("#dong").val()},	// 지정한 데이터를 가지고
				function(data){				// 그리고 처리된 데이터를 가지고 돌아오는데(zipAction.amy에서 데이터 처리를 한번 함), 현재 페이지에서 무슨 기능을 수행?
					// data는 parse가 필요하다. Json으로 처리되었다면, 굳이 필요가 없는 단계
					var res = JSON.parse(data);
					// 화면 출력을 위해 html 태그들을 저장한다.
					var htmlStr="";
					htmlStr +="<table>";
					// each에서 대상 데이터는 콜백함수의 매개변수인  data(이전페이지에서 처리된)들인데, 
					// 여기서 우리가 원하는 데이터는 hashmap으로 담은 key는 zipcode이다.
					// 그래서 res.zipcode사용.
					$.each(res.zipcode,function(key,val){
						var bunji=val.bunji==null?"":val.bunji;
						htmlStr +="<tr>";
						htmlStr +="<td>"+val.zipcode+"</td>";
						htmlStr +="<td>"+val.sido+"</td>";
						htmlStr +="<td>"+val.gugun+"</td>";
						htmlStr +="<td>"+val.dong+"</td>";
						htmlStr +="<td>"+bunji+"</td>";
						htmlStr +="</tr>";
					});
					htmlStr +="</table>";
					$("#area").html(htmlStr);
				} // callback function
		); // post
	}); // click
	$("#area").on("click","tr",function(){
		var address = $("td:eq(1)",this).text()+" "+$("td:eq(2)",this).text()+" "+$("td:eq(3)",this).text()+" "+$("td:eq(4)",this).text();
		$(opener.document).find("#zipCode").val($("td:eq(0)",this).text());
		$(opener.document).find("#address").val(address);
		self.close();
	})
	
	
});	// document
</script>
</head>
<body>
<div class="content">
	<table>
		<tr>
			<td colspan="5">동이름 입력 : 
				<input type="text" name="dong" id="dong">
				<input type="button" value="검색" id="send">
			</td>
		</tr>
	</table>
</div>
<div class="content2" id="area"></div>	
</body>
</html>