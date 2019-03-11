/*****************************************************************************
 File Header            : UpdateUser.java
 Description            : Update user details enter by the login user.
 Author                 : Amit Banik 
 Created On             : 30-10-2018
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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet("/UpdateUser")
public class UpdateUser extends HttpServlet {


	
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
	private static final Logger LOG = Logger.getLogger(UpdateUser.class);

//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
	String updateuser(UserData objUserData) {
		System.out.println("updateuser");
		/**
		 * create connection with connection pool
		 */
		objConCP = new ConnectionPooling();
		objCon = objConCP.getConnection();

		
//		response.setContentType("text/html;charset=UTF-8");
//
//		String sEmailId = request.getParameter("useremail");
//		String sFname = request.getParameter("userfname");
//		String sLname = request.getParameter("userlname");
//		String sRname = request.getParameter("userrname");
//		String sUserid = request.getParameter("userid");
		String sEmailId = objUserData.getEmail();
		String sFname = objUserData.getFname();
		String sLname = objUserData.getLname();
		String sRname = objUserData.getRname();
		String sUserid = objUserData.getUserId();
		String sUpdateStatus;
		String sUpdateUserResponse = "false";
		
		LOG.info("Update user details process initiate");
		
		try {
			
			/**
			 * create connection without connection pool
			 */
			/*
			 * Class.forName("org.postgresql.Driver"); Connection objCon =
			 * DriverManager.getConnection("jdbc:postgresql://localhost:5432/Tast1",
			 * "postgres", "postgres");
			 */
			
			/**
			 * execute query without using store procedure
			 */
			/*
			PreparedStatement ps = objCon.prepareStatement(
					"update registration_details set email_id=? , f_name=?, l_name=? ,r_name=? where r_id=?;");
			ps.setString(1, EmailId);
			ps.setString(2, Fname);
			ps.setString(3, Lname);
			ps.setString(4, Rname);
			ps.setString(5, userid);
			ps.executeUpdate();
			System.out.println("hello");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("True");
			*/
			
			/**
			 * execute query with using store procedure
			 */
			
			CallableStatement csmt = objCon.prepareCall("{call UpdateUser(?,?,?,?,?,?)}");
			csmt.setString(1, sEmailId);
			csmt.setString(2, sFname);
			csmt.setString(3, sLname);
			csmt.setString(4, sRname);
			csmt.setString(5, sUserid);
			csmt.registerOutParameter(6, Types.VARCHAR);
			csmt.execute();
			sUpdateStatus = csmt.getString(6);
			if(sUpdateStatus.equals("true")) {
				//System.out.println("UpdateStatus:"+sUpdateStatus);
				LOG.info("UpdateStatus:"+sUpdateStatus);
				LOG.info(sUserid+" detalis updated");
				//response.getWriter().write("True");
				sUpdateUserResponse = "True";
			}
			else {
				LOG.info("UpdateStatus:"+sUpdateStatus);
				LOG.info(sUserid+" detalis not updated");
				LOG.info("May be"+sUserid+"or "+sEmailId+" already exist");
				//response.getWriter().write("false");
				sUpdateUserResponse = "false";
			}

		} catch (Exception e) {

			//e.printStackTrace();
			LOG.error("Exception generate in update user"+e,e);

		} finally {
			try {
				objCon.close();
				LOG.info("Update user details process end");
			} catch (SQLException e) {
				//e.printStackTrace();
				LOG.error("Exception generate in update user"+e,e);
			} catch (Exception e) {
				//e.printStackTrace();
				LOG.error("Exception generate in update user"+e,e);
			}
		}

		return sUpdateUserResponse;
	}

}
