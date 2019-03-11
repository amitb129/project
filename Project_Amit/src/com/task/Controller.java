/*****************************************************************************
 File Header            : Controller.java
 Description            : It read login details and if login details correct then,
                          Start session with user first Name. 
 Author                 : Amit Bnaik 
 Created On             : 10-10-2018
 Maintenance History    : Amit Nov 2018 - Added connection pooling.
  										- Added store procedure.
  										- Added log4j.
 										- Added some coding standard.
 Copyright © iTech Workshop Private Limited 2018 All rights reserved.
 ****************************************************************************/

package com.task;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import java.sql.*;

public class Controller{

	
	HttpServletRequest request;
    
	HttpServletResponse response;
	
	/**
	 * create object for log4j class
	 */
	private static final Logger LOG = Logger.getLogger(Controller.class);
	/**
	 * create object of connection pool and Connection
	 */
	ConnectionPooling objConCP;
	private Connection objCon;
	public String UserLogin(UserData objUserData ) {
		System.out.println("UserLogin called");

		/**
		 * create connection with connection pool
		 */

		objConCP = new ConnectionPooling(); // initiate the object

		objCon = objConCP.getConnection(); // call connection from connection pooling class
		System.out.println("concp object create");
		//response.setContentType("text/html");
		LOG.info("Login Process initiate ");
		//String sEmail = request.getParameter("i_Email");
		//String sPassword = request.getParameter("i_Password");
		String sEmail = objUserData.getEmail();
		String sPassword = objUserData.getPassword();
		String sFname = null;
		String sControllerResponse = "False";
		System.out.println(sEmail+sPassword);
		Login objBean = new Login();
		objBean.setEmail(sEmail);
		objBean.setPassword(sPassword);
		// request.setAttribute("objBean", objBean);
		// System.out.println("hi email"+objBean.getemail());

		boolean bStatus = objBean.validate(); // validate user login credentials with email id and password of
												// registered
												// user

		if (bStatus) {

			try {

				/**
				 * fetch the user name and start with user first name
				 */

				/**
				 * create connection without connection pool
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
				 * PreparedStatement ps = objCon.prepareStatement(
				 * "select Registration_Details.f_name from u_login inner join Registration_Details on u_login.user_id = Registration_Details.R_id where email_id=? AND password=?;"
				 * ); ps.setString(1, email); ps.setString(2, password);
				 * 
				 * ResultSet rs = ps.executeQuery();
				 * 
				 * rs.next(); String S_Name = rs.getString(1);
				 */

				LOG.info("successfully login");
				/**
				 * fetch data with using store procedure
				 */
				sControllerResponse="True";
				//response.setContentType("text/html;charset=UTF-8");
				//response.getWriter().write("True");

			} catch (Exception e) {
				// e.printStackTrace();
				LOG.error("login or session create exception" + e, e);
			}

			finally {
				try {
					objCon.close();
					objCon = null;
					LOG.info("Login Process End ");
					// System.out.println("conection details:");
					// System.out.println(objCon);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					LOG.error("login or session create exception" + e, e);

				} catch (Exception e) {
					// e.printStackTrace();
					LOG.error("login or session create exception" + e, e);
				}
			}

		} else {
			LOG.info(" login not sccessful");
			//response.setContentType("text/html;charset=UTF-8");
			//response.getWriter().write("False");
			sControllerResponse="False";
			LOG.info("Login Process End ");
		}

		
		return sControllerResponse;
		
	}
	private void createSession(HttpServletRequest request2, HttpServletResponse response2) {
		// TODO Auto-generated method stub
		
	}


}