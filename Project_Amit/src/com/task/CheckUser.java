/*****************************************************************************
 File Header            : CheckUser.java
 Description            : Check user id enter by user already available or not.
 Author                 : Amit Banik 
 Created On             : 22-10-2018
 Maintenance History    : Amit Nov 2018 - Added connection pooling.
  										- Added store procedure. 
  										- Added log4j.
 										- Added some coding standard.
 Copyright © iTech Workshop Private Limited 2018 All rights reserved.
 ****************************************************************************/
package com.task;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class CheckUser extends HttpServlet {

	
	HttpServletRequest request;
    
	HttpServletResponse response;	
	
	/**
	 * create object of connection pool and Connection
	 */
	ConnectionPooling objConCP;
	private Connection objCon;

	private static final long serialVersionUID = 1L;

	/**
	 * create object for log4j class
	 */
	private static final Logger LOG = Logger.getLogger(CheckUser.class);

//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
	String CheckUserId(UserData objUserData){
		System.out.println("CheckUserId");
		/**
		 * create connection with connection pool
		 */
		objConCP = new ConnectionPooling();
		objCon = objConCP.getConnection();

		//response.setContentType("text/html;charset=UTF-8");
//		String sUserId = request.getParameter("UserId");
		String sUserId = objUserData.getUserId();
		String sUserIdStatus;
		String sCheckUserResponse = "false";  

		try {

			/**
			 * create connection without connection pool
			 */
			/*
			 * Class.forName("org.postgresql.Driver"); Connection objCon =
			 * DriverManager.getConnection("jdbc:postgresql://localhost:5432/Tast1",
			 * "postgres", "postgres");
			 */

			if (sUserId != "") {
				/**
				 * fetch data without using store procedure
				 */
				/*
				 * PreparedStatement ps =
				 * objCon.prepareStatement("select user_id from u_login  where user_id=?;");
				 * ps.setString(1, UserId); ResultSet rs = ps.executeQuery(); rs.next(); String
				 * userid = rs.getString(1); if (UserId.equals(userid)) {
				 * response.setContentType("text/html;charset=UTF-8");
				 * response.getWriter().write("User"); }
				 */
				/**
				 * fetch data with using store procedure
				 */

				CallableStatement csmt = objCon.prepareCall("{call usernamecheck(?,?)}");
				csmt.setString(1, sUserId);
				csmt.registerOutParameter(2, Types.VARCHAR);
				csmt.execute();
				sUserIdStatus = csmt.getString(2);
				if (sUserIdStatus.equals("true")) {
					System.out.println("UserIdStatus:" + sUserIdStatus);
					//response.getWriter().write("User");
					sCheckUserResponse = "User";  
				} else {
					//response.getWriter().write("false");
					sCheckUserResponse = "false";  
				}

			}

		} catch (Exception e) {
			// e.printStackTrace();
			LOG.error("Check user id generate exception" + e, e);
		} finally {
			try {
				objCon.close();
			} catch (SQLException e) {
				// e.printStackTrace();
				LOG.error("Check user id generate exception" + e, e);
			} catch (Exception e) {
				// e.printStackTrace();
				LOG.error("Check user id generate exception" + e, e);
			}
		}
		return sCheckUserResponse;
	}

}
