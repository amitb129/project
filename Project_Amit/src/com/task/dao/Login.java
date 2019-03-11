/*****************************************************************************
 File Header            : Login.java
 Description            : bean class for login details and it also validate the login 
 						  details with database and return status
 Author                 : Amit Banik 
 Created On             : 10-10-2018
 Maintenance History    : Amit Nov 2018 - Added connection pooling.
  										- Added store procedure.
  										- Added log4j. 
 										- Added some coding standard.
 Copyright © iTech Workshop Private Limited 2018 All rights reserved.
 ****************************************************************************/
package com.task.dao;

import java.sql.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import com.task.log.ProjectLogging;

public class Login extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * create object for log4j class
	 */
	//private static final Logger LOG = Logger.getLogger(Login.class);
	private static final ProjectLogging LOG = new ProjectLogging(); 
	/**
	 * create objects of connection pooling
	 */
	ConnectionPooling objConCP;
	private Connection objCon;

	private String email, password;
	String sStatusValue;
	boolean bStatus = false;
	HttpServletRequest request;
	HttpServletResponse response;

	public String getemail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean validate() {
		try {

			/**
			 * create connection with connection pool
			 */
			objConCP = new ConnectionPooling();

			objCon = objConCP.getConnection();
			/**
			 * fetch data with using store procedure
			 */

			CallableStatement csmt = objCon.prepareCall("{call login1(?,?,?)}");
			LOG.printdebug(email);
			LOG.printinfo("login user email id:" + email);
			LOG.printdebug(password);
			LOG.printinfo("login user password:" + password);
			csmt.setString(1, email);
			csmt.setString(2, password);
			csmt.registerOutParameter(3, Types.VARCHAR);
			csmt.execute();
			sStatusValue = csmt.getString(3);
			LOG.printdebug(sStatusValue);
			LOG.printinfo("login status value" + sStatusValue);
			if (sStatusValue.equals("True")) {
				bStatus = true;
			} else {
				bStatus = false;
			}

		} catch (Exception e) {
			LOG.printerror("Login.java generate excepton "+ e, e);
		} finally {
			try {
				objCon.close();
			} catch (SQLException e) {
				LOG.printerror("Login.java generate excepton "+ e, e);
			} catch (Exception e) {
				LOG.printerror("Login.java generate excepton "+ e, e);
			}
		}

		return bStatus;

	}

}
