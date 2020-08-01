package com.program.master;

public class Master {
	private String masterID;
	private String masterName;
	private String password;
	private String name;
	private String masterSex;
	private String masterAddress;
	public Master() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Master(String masterID, String masterName, String password, String name, String masterSex,
			String masterAddress) {
		super();
		this.masterID = masterID;
		this.masterName = masterName;
		this.password = password;
		this.name = name;
		this.masterSex = masterSex;
		this.masterAddress = masterAddress;
	}
	public String getMasterID() {
		return masterID;
	}
	public void setMasterID(String masterID) {
		this.masterID = masterID;
	}
	public String getMasterName() {
		return masterName;
	}
	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMasterSex() {
		return masterSex;
	}
	public void setMasterSex(String masterSex) {
		this.masterSex = masterSex;
	}
	public String getMasterAddress() {
		return masterAddress;
	}
	public void setMasterAddress(String masterAddress) {
		this.masterAddress = masterAddress;
	}
	@Override
	public String toString() {
		return "Master [masterID=" + masterID + ", masterName=" + masterName + ", password=" + password + ", name="
				+ name + ", masterSex=" + masterSex + ", masterAddress=" + masterAddress + "]";
	}
	
}
