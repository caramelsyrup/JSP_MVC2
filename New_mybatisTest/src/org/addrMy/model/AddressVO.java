package org.addrMy.model;

public class AddressVO {
	private int num;
	private String name,address,tel,zipcode;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name == null ? "" : name.trim();
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTel() {
		return tel == null ? "" : tel.trim();
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getZipcode() {
		return zipcode == null ? "" : zipcode.trim();
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress() {
		return address == null ? "" : address.trim();
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
