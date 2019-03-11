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
package com.task;

import java.sql.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

public class Login extends HttpServlet {

	/**
	 * create object for log4j class
	 */
	private static final Logger LOG = Logger.getLogger(Login.class);

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
			 * create connection without connection pool
			 */

			/*
			 * Class.forName("org.postgresql.Driver");
			 * 
			 * Connection objCon =
			 * DriverManager.getConnection("jdbc:postgresql://localhost:5432/Tast1",
			 * "postgres", "postgres");
			 */

			/**
			 * create connection with connection pool
			 */
			objConCP = new ConnectionPooling();

			objCon = objConCP.getConnection();
			// fetch data without using store procedure
			/*
			 * PreparedStatement ps = objCon.prepareStatement(
			 * "select Registration_Details.f_name from u_login inner join Registration_Details on u_login.user_id = Registration_Details.R_id where email_id=? AND password=?;"
			 * ); ps.setString(1, email); ps.setString(2, password);
			 * 
			 * ResultSet rs = ps.executeQuery(); status = rs.next();
			 */

			/**
			 * fetch data with using store procedure
			 */

			CallableStatement csmt = objCon.prepareCall("{call login1(?,?,?)}");
			// System.out.println(email);
			LOG.info("login user email id:" + email);
			// System.out.println(password);
			LOG.info("login user password:" + password);
			csmt.setString(1, email);
			csmt.setString(2, password);
			csmt.registerOutParameter(3, Types.VARCHAR);
			csmt.execute();
			sStatusValue = csmt.getString(3);
			// System.out.println(StatusValue);
			LOG.info("login status value" + sStatusValue);
			if (sStatusValue.equals("True")) {
				bStatus = true;
			} else {
				bStatus = false;
			}

		} catch (Exception e) {
			// e.printStackTrace();
			LOG.error(e, e);
		} finally {
			try {
				objCon.close();
			} catch (SQLException e) {
				LOG.error(e, e);
				// e.printStackTrace();
			} catch (Exception e) {
				LOG.error(e, e);
				// e.printStackTrace();
			}
		}

		return bStatus;

	}

}
