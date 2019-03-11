/*****************************************************************************
 File Header            : UserData.java
 Description            : Bean class for read all user data
 Author                 : Amit Banik 
 Created On             : 23-10-2018
 Maintenance History    : Amit Nov 2018 - Added some coding standard.
 Copyright © iTech Workshop Private Limited 2018 All rights reserved.
 ****************************************************************************/
package com.task.bo;

public class UserData {

	private String sUserId;
	public String getsUserId() {
		return sUserId;
	}
	public void setsUserId(String sUserId) {
		this.sUserId = sUserId;
	}
	public String getsFirstName() {
		return sFirstName;
	}
	public void setsFirstName(String sFirstName) {
		this.sFirstName = sFirstName;
	}
	public String getsLastName() {
		return sLastName;
	}
	public void setsLastName(String sLastName) {
		this.sLastName = sLastName;
	}
	public String getsRoleName() {
		return sRoleName;
	}
	public void setsRoleName(String sRoleName) {
		this.sRoleName = sRoleName;
	}
	public String getsPassword() {
		return sPassword;
	}
	public void setsPassword(String sPassword) {
		this.sPassword = sPassword;
	}
	public String getsEmail() {
		return sEmail;
	}
	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}
	public int getiNoOfPage() {
		return iNoOfPage;
	}
	public void setiNoOfPage(int iNoOfPage) {
		this.iNoOfPage = iNoOfPage;
	}
	private String sFirstName;
	private String sLastName;
	private String sRoleName;
	private String sPassword;
	private String sEmail;
	private int iNoOfPage;
}
