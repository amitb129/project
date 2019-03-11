/*****************************************************************************
 File Header            : CheckEmail.java
 Description            : Check email id enter by user already available or not.
 Author                 : Amit Banik 
 Created On             : 22-10-2018
 Maintenance History    : Amit Nov 2018 - Added connection pooling.
  										- Added store procedure.
  										- Added log4j
 										- Added some coding standard.
 Copyright © iTech Workshop Private Limited 2018 All rights reserved.
 ****************************************************************************/
package com.task;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class CheckEmail extends HttpServlet {

	
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
	private static final Logger LOG = Logger.getLogger(CheckEmail.class);

//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
	
	String CheckEmailId(UserData objUserData){
		System.out.println("CheckEmailId");
		/**
		 * create connection with connection pool
		 */
		objConCP = new ConnectionPooling();
		objCon = objConCP.getConnection();

		//response.setContentType("text/html;charset=UTF-8");

//		String sEmailId = request.getParameter("EmailId");
		String sEmailId = objUserData.getEmail();
		String sEmailIdStatus;
		String sCheckEmailResponse = "false";

		try {

			/*
			 * Class.forName("org.postgresql.Driver"); Connection objCon =
			 * DriverManager.getConnection("jdbc:postgresql://localhost:5432/Tast1",
			 * "postgres", "postgres");
			 */

			if (sEmailId != "") {

				/**
				 * fetch data without using store procedure
				 */
				/*
				 * PreparedStatement ps = objCon
				 * .prepareStatement("select email_id from registration_details  where email_id=?;"
				 * ); ps.setString(1, EmailId); ResultSet rs = ps.executeQuery(); rs.next();
				 * String email = rs.getString(1); if (EmailId.equals(email)) {
				 * response.setContentType("text/html;charset=UTF-8");
				 * response.getWriter().write("Email");
				 * 
				 * }
				 */
				/**
				 * fetch data with using store procedure
				 */

				CallableStatement csmt = objCon.prepareCall("{call useremailcheck(?,?)}");
				csmt.setString(1, sEmailId);
				csmt.registerOutParameter(2, Types.VARCHAR);
				csmt.execute();
				sEmailIdStatus = csmt.getString(2);
				if (sEmailIdStatus.equals("true")) {
					System.out.println("UserIdStatus:" + sEmailIdStatus);
					//response.getWriter().write("Email");
					sCheckEmailResponse = "Email";
				} else {
					//response.getWriter().write("false");
					sCheckEmailResponse = "false";
				}
			}

		} catch (Exception e) {
			LOG.error("Check email generate exception" + e, e);
			// e.printStackTrace();
		} finally {
			try {
				objCon.close();
			} catch (SQLException e) {
				LOG.error("Check email generate exception" + e, e);
				// e.printStackTrace();
			} catch (Exception e) {
				LOG.error("Check email generate exception" + e, e);
				// e.printStackTrace();
			}
		}
		
		return sCheckEmailResponse;
	}

}
