/*****************************************************************************
 File Header            : AddNewUser.java
 Description            : Read all user details from add user form and
 						  insert data to database.
 Author                 : Amit Banik 
 Created On             : 16-10-2018
 Maintenance History    : Amit Nov 2018 - Added connection pooling.
  										- Added store procedure 
  										- Added log4j
 										- Added some coding standard.
 Copyright © iTech Workshop Private Limited 2018 All rights reserved.
 ****************************************************************************/
package com.task;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet("/AddNewUser")
public class AddNewUser extends HttpServlet {
	
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
	private static final Logger LOG = Logger.getLogger(AddNewUser.class);

//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
	String InsertUser(UserData objUserData) {
		
		System.out.println("InsertUser called");
		/**
		 * create connection with coonection pool
		 */
		objConCP = new ConnectionPooling();
		objCon = objConCP.getConnection();

		//response.setContentType("text/html;charset=UTF-8");

//		String sUserId = request.getParameter("UserId");
//		String sFname = request.getParameter("Fname");
//		String slname = request.getParameter("lname");
//		String sRname = request.getParameter("Rname");
//		String sEmailId = request.getParameter("EmailId");
//		String sPwd = request.getParameter("Pwd");
		
		String sUserId = objUserData.getUserId();
		String sFname =  objUserData.getFname();
		String slname =  objUserData.getLname();
		String sRname =  objUserData.getRname();
		String sEmailId = objUserData.getEmail();
		String sPwd = objUserData.getPassword();
		String sAddUserQueryStatus;
		String sAddNewUserResponse = "False";

		LOG.info("Add user process initiate");

		try {

			/**
			 * insert new user data into u_login and registration_details table
			 */

			/**
			 * create connection without coonection pool
			 */
			/*
			 * Class.forName("org.postgresql.Driver"); Connection objCon =
			 * DriverManager.getConnection("jdbc:postgresql://localhost:5432/Tast1",
			 * "postgres", "postgres");
			 */
			/**
			 * fetch data without using store procedure
			 */
			/*
			 * Statement stmt = objCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
			 * ResultSet.CONCUR_UPDATABLE);
			 * 
			 * String insertDetails1 = "INSERT INTO u_login (user_id,password) VALUES (" +
			 * "'" + UserId + "'" + "," + "'" + Pwd + "'" + ")"; String insertDetails2 =
			 * "INSERT INTO registration_details ( r_id,email_id,f_name,l_name,r_name) VALUES ("
			 * + "'" + UserId + "'" + "," + "'" + EmailId + "'" + "," + "'" + Fname + "'" +
			 * "," + "'" + lname + "'" + "," + "'" + Rname + "'" + ")";
			 * objCon.setAutoCommit(false);
			 * 
			 * stmt.addBatch(insertDetails1); stmt.addBatch(insertDetails2);
			 * 
			 * stmt.executeBatch(); objCon.commit();
			 */

			/**
			 * fetch data with using store procedure
			 */

			CallableStatement csmt = objCon.prepareCall("{? = call AddUser1(?,?,?,?,?,?)}");
			csmt.registerOutParameter(1, Types.VARCHAR);
			csmt.setString(2, sUserId);
			csmt.setString(3, sPwd);
			csmt.setString(4, sEmailId);
			csmt.setString(5, sFname);
			csmt.setString(6, slname);
			csmt.setString(7, sRname);
			csmt.execute();
			sAddUserQueryStatus = csmt.getString(1);
			if (sAddUserQueryStatus.equals("true")) {
				LOG.info("AddUserQueryStatus: " + sAddUserQueryStatus);
				LOG.info(sFname + " user added");
				// System.out.println("AddUserQueryStatus: "+sAddUserQueryStatus);
				//response.getWriter().write("True");
				sAddNewUserResponse = "True";
				
			} else {
				LOG.info("AddUserQueryStatus: " + sAddUserQueryStatus);
				LOG.info("May be" + sUserId + " or " + sEmailId + " already exist");
				sAddNewUserResponse = "False";
			}

		} catch (Exception e) {
			// e.printStackTrace();
			LOG.error("Add user process generate exception" + e, e);
		} finally {
			try {
				objCon.close();
				LOG.info("Add user process End");
			} catch (SQLException e) {
				LOG.error("Add user process generate exception" + e, e);
				// e.printStackTrace();
			} catch (Exception e) {
				LOG.error("Add user process generate exception" + e, e);
				// e.printStackTrace();
			}
		}

	return sAddNewUserResponse;
	}

}
