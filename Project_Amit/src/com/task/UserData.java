/*****************************************************************************
 File Header            : UserData.java
 Description            : Bean class for read all user data
 Author                 : Amit Banik 
 Created On             : 23-10-2018
 Maintenance History    : Amit Nov 2018 - Added some coding standard.
 Copyright © iTech Workshop Private Limited 2018 All rights reserved.
 ****************************************************************************/
package com.task;

public class UserData {

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getFname() {
		return Fname;
	}

	public void setFname(String fname) {
		Fname = fname;
	}

	public String getLname() {
		return Lname;
	}

	public void setLname(String lname) {
		Lname = lname;
	}

	public String getRname() {
		return Rname;
	}

	public void setRname(String rname) {
		Rname = rname;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public int getNoOfPage() {
		return NoOfPage;
	}

	public void setNoOfPage(int noOfPage) {
		NoOfPage = noOfPage;
	}

	private String UserId;
	private String Fname;
	private String Lname;
	private String Rname;
	private String Password;
	private String Email;
	private int NoOfPage;

}
