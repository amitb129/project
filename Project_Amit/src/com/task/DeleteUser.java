/*****************************************************************************
 File Header            : DeleteUser.java
 Description            : Delete user details from the table.
 Author                 : Amit Banik 
 Created On             : 29-10-2018
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
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet {


	
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
	private static final Logger LOG = Logger.getLogger(DeleteUser.class);

//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
	String deleteuser(UserData objUserData){
		System.out.println("deleteuser");
		/**
		 * create connection with connection pool
		 */
		objConCP = new ConnectionPooling();
		objCon = objConCP.getConnection();

		// TODO Auto-generated method stub
//		String sdeleteUserInfo = request.getParameter("userinfo");
		String sdeleteUserInfo = objUserData.getUserId();
		String sdeletestatus;
		String sCheckUserResponse="false";

		LOG.info("Delete user process initate");

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
			 * Statement stmt = objCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
			 * ResultSet.CONCUR_UPDATABLE);
			 * 
			 * String DeleteTbl1 = "delete from registration_details where r_id='" +
			 * deleteUserInfo + "'"; String DeleteTbl2 =
			 * "delete from u_login where user_id='" + deleteUserInfo + "'";
			 * System.out.println(DeleteTbl1 + "  " + DeleteTbl2);
			 * objCon.setAutoCommit(false);
			 * 
			 * stmt.addBatch(DeleteTbl1); stmt.addBatch(DeleteTbl2);
			 * 
			 * stmt.executeBatch(); objCon.commit();
			 * 
			 * response.setContentType("text/html;charset=UTF-8");
			 * response.getWriter().write("True");
			 */

			/**
			 * execute query with using store procedure
			 */
			CallableStatement csmt = objCon.prepareCall("{call DeleteUser(?,?)}");
			csmt.setString(1, sdeleteUserInfo);
			csmt.registerOutParameter(2, Types.VARCHAR);
			csmt.execute();
			sdeletestatus = csmt.getString(2);
			if (sdeletestatus.equals("true")) {
				// System.out.println("deletestatus:"+sdeletestatus);
				LOG.info("deletestatus:" + sdeletestatus);
				LOG.info(sdeleteUserInfo + "successfully deleted");
				//response.setContentType("text/html;charset=UTF-8");
				//response.getWriter().write("True");
				sCheckUserResponse="True";
			} else {
				LOG.info("deletestatus:" + sdeletestatus);
				LOG.info(sdeleteUserInfo + "not deleted");
				//response.setContentType("text/html;charset=UTF-8");
				//response.getWriter().write("false");
				sCheckUserResponse="false";
			}

		} catch (Exception e) {
			// e.printStackTrace();
			LOG.error("Exception generate in delete user" + e, e);

		} finally {
			try {
				objCon.close();
				LOG.info("Delete user process end");
			} catch (SQLException e) {
				// e.printStackTrace();
				// e.printStackTrace();
				LOG.error("Exception generate in delete user" + e, e);
			} catch (Exception e) {
				// e.printStackTrace();
				// e.printStackTrace();
				LOG.error("Exception generate in delete user" + e, e);
			}
		}

		return sCheckUserResponse;
	}

}
