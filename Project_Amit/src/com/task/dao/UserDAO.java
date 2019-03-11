/*****************************************************************************
 File Header            : UserDAO.java
 Description            : It is our interface of our project we declare all important method to use. 
 Author                 : Amit Bnaik 
 Created On             : 21-11-2018
 Maintenance History    : Amit Nov 2018 -implementing loosely couple.
 Copyright © iTech Workshop Private Limited 2018 All rights reserved.
 ****************************************************************************/
package com.task.dao;

import com.task.bo.UserData;

public interface UserDAO {

	 String UserLogin(UserData objUserData); // for validate user login details
	 String InsertUser(UserData objUserData);// for insert new user
	 String CheckUserId(UserData objUserData); //for check existing email id
	 String CheckEmailId(UserData objUserData); //for check existing email id
	 String deleteuser(UserData objUserData); //for delete user
	 String updateuser(UserData objUserData);// for update user details
	 
}
