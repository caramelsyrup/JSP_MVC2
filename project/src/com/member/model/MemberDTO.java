package com.member.model;

public class MemberDTO {
	private String userName,userID,userPwd,userEmail;
	private int userTel,admin;
	
	public String getUserName() {
		return userName == null ? "" : userName.trim();
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserID() {
		return userID == null ? "" : userID.trim();
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserPwd() {
		return userPwd == null ? "" : userPwd.trim();
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserEmail() {
		return userEmail == null ? "" : userEmail.trim();
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public int getUserTel() {
		return userTel;
	}
	public void setUserTel(int userTel) {
		this.userTel = userTel;
	}
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		this.admin = admin;
	}
	
	
}
