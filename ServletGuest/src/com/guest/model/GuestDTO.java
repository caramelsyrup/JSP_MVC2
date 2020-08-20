package com.guest.model;

public class GuestDTO {
	private int num;
	private String writter, content, grade, created, ipaddr;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getWritter() {
		return writter == null ? "" : writter.trim();
	}
	public void setWritter(String writter) {
		this.writter = writter;
	}
	public String getContent() {
		return content == null ? "" : content.trim();
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getGrade() {
		return grade == null ? "" : grade.trim();
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getCreated() {
		return created == null ? "" : created.trim();
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getIpaddr() {
		return ipaddr == null ? "" : ipaddr.trim();
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	
	
}
