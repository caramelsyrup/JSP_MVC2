<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<div class="container">
  <h2>JOIN form</h2>
  <form action="insert.me" method="post" id="frm">
    <div class="form-group">
      <label for="userId">UserId:</label>
      <input type="text" class="form-control" id="userId" placeholder="userId" name="userId"/>
      <input type="button" class="btn btn-primary" id="idChkBtn" value="중복검사"/>
    </div>
    <div class="form-group">
      <label for="pwd">Password:</label>
      <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="pwd"/>
    </div>
    <div class="form-group">
      <label for="name">Name:</label>
      <input type="text" class="form-control" id="name" placeholder="name" name="name">
    </div>
    <div class="form-group">
      <label for="email">Email:</label>
      <input type="text" class="form-control" id="email" placeholder="email" name="email">
    </div>
    <div class="form-group">
      <label for="phone">Phone:</label>
      <input type="text" class="form-control" id="phone" placeholder="phone" name="phone">
    </div>
  	<div class="form-check-inline">
	  <label class="form-check-label">
	    <input type="radio" class="form-check-input" id="admin" name="admin" value="0" checked="checked">일반회원
	  </label>
	</div>
	<div class="form-check-inline">
	  <label class="form-check-label">
	    <input type="radio" class="form-check-input" id="admin" name="admin" value="1" >관리자
	  </label>
	</div>
	<div>    
	    <button class="btn btn-primary" id="send">Submit</button>
	    <button type="reset" class="btn btn-primary">Cancel</button>
    </div>
  </form>
</div>

<%@ include file="../include/footer.jsp" %>
