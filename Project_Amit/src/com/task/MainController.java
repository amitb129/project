/*****************************************************************************
 File Header            : MainController.java
 Description            : Controller servlet for loosely couple.
 Author                 : Amit Banik 
 Created On             : 30-10-2018
 Maintenance History    : Amit Nov 2018 - Created each operation class.
 Copyright © iTech Workshop Private Limited 2018 All rights reserved.
 ****************************************************************************/
package com.task;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class MainController
 */
@WebServlet("/MainController")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(MainController.class);
	/**
	 * create object of connection pool and Connection
	 */
	ConnectionPooling objConCP;
	private Connection objCon;

	/**
	 * create object of operation class
	 */
	Controller objController;
	AddNewUser objAddNewUser;
	CheckEmail objCheckEmail;
	CheckUser objCheckUser;
	DeleteUser objDeleteUser;
	UpdateUser objUpdateUser;
	UserData objUserData;
	UserDAOImplementation objUserDAOImplementation;

	public void init(ServletConfig config) throws ServletException {
		/**
		 * initiate object of operation class
		 */
		objController = new Controller();
		objAddNewUser = new AddNewUser();
		objCheckEmail = new CheckEmail();
		objCheckUser = new CheckUser();
		objDeleteUser = new DeleteUser();
		objUpdateUser = new UpdateUser();
		objUserData = new UserData();
		objUserDAOImplementation = new UserDAOImplementation();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String sAction = request.getParameter("action");
		System.out.println(sAction);
		try {
			switch (sAction) {

			case "login":
				doLoginUser(request, response);
				break;
			case "adduser":
				doInsertUser(request, response);
				break;
			case "edituser":
				doEditUser(request, response);
				break;
			case "deleteuser":
				doDeleteUser(request, response);
				break;
			case "checkemail":
				doCheckEmail(request, response);
				break;
			case "checkuser":
				doCheckUser(request, response);
				break;
			}
		} catch (Exception e) {
			LOG.error("MainController genarate exception" + e, e);
		}

	}

	private void doCheckUser(HttpServletRequest request, HttpServletResponse response) {

		System.out.println("doCheckUser");
		try {
			response.setContentType("text/html;charset=UTF-8");
			String sUserId = request.getParameter("UserId");
			objUserData.setUserId(sUserId);
			String sCheckUserResponse = objUserDAOImplementation.CheckUserId(objUserData);
			response.getWriter().write(sCheckUserResponse);
		} catch (Exception e) {
			LOG.error("MainController genarate exception" + e, e);
		}

	}

	private void doCheckEmail(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("doCheckEmail");
		try {

			response.setContentType("text/html;charset=UTF-8");
			String sEmailId = request.getParameter("EmailId");
			objUserData.setEmail(sEmailId);
			String sCheckEmailResponse = objUserDAOImplementation.CheckEmailId(objUserData);
			response.getWriter().write(sCheckEmailResponse);
		} catch (Exception e) {
			LOG.error("MainController genarate exception" + e, e);
		}

	}

	private void doDeleteUser(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("doDeleteUser");
		try {

			response.setContentType("text/html;charset=UTF-8");
			String sdeleteUserInfo = request.getParameter("userinfo");
			objUserData.setUserId(sdeleteUserInfo);
			String sDeleteUserResponse = objUserDAOImplementation.deleteuser(objUserData);
			response.getWriter().write(sDeleteUserResponse);
		} catch (Exception e) {
			LOG.error("MainController genarate exception" + e, e);
		}

	}


	private void doEditUser(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("doEditUser");
		try {

			response.setContentType("text/html;charset=UTF-8");

			String sEmailId = request.getParameter("useremail");
			String sFirstName = request.getParameter("userfname");
			String sLname = request.getParameter("userlname");
			String sRname = request.getParameter("userrname");
			String sUserid = request.getParameter("userid");
			objUserData.setEmail(sEmailId);
			objUserData.setFname(sFirstName);
			objUserData.setLname(sLname);
			objUserData.setRname(sRname);
			objUserData.setUserId(sUserid);
			
			String sUpdateUserResponse = objUserDAOImplementation.updateuser(objUserData);
			System.out.println(sUpdateUserResponse);
			response.getWriter().write(sUpdateUserResponse);
		} catch (Exception e) {
			LOG.error("MainController genarate exception" + e, e);
		}

	}

	private void doInsertUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("doInsertUser called");
			response.setContentType("text/html;charset=UTF-8");
			String sUserId = request.getParameter("UserId");
			String sFirstName = request.getParameter("Fname");
			String slname = request.getParameter("lname");
			String sRname = request.getParameter("Rname");
			String sEmailId = request.getParameter("EmailId");
			String sPwd = request.getParameter("Pwd");
			objUserData.setUserId(sUserId);
			objUserData.setFname(sFirstName);
			objUserData.setLname(slname);
			objUserData.setRname(sRname);
			objUserData.setEmail(sEmailId);
			objUserData.setPassword(sPwd);
			
			String sAddNewUserResponse = objUserDAOImplementation.InsertUser(objUserData);
			response.getWriter().write(sAddNewUserResponse);
		} catch (Exception e) {
			LOG.error("MainController genarate exception" + e, e);
		}

	}

	private void doLoginUser(HttpServletRequest request, HttpServletResponse response) {
		objConCP = new ConnectionPooling(); // initiate the object

		objCon = objConCP.getConnection(); // call connection from connection pooling class
		try {

			System.out.println("doLoginUser called");
			response.setContentType("text/html;charset=UTF-8");
			
			String sEmail = request.getParameter("i_Email");
			String sPassword = request.getParameter("i_Password");
			objUserData.setEmail(sEmail);
			objUserData.setPassword(sPassword);
			String sControllerResponse = objUserDAOImplementation.UserLogin(objUserData);
			System.out.println(sControllerResponse);
			if (sControllerResponse.equals("True")) {
				System.out.println("true");
				try {
					CallableStatement csmt = objCon.prepareCall("{call UserNameSession(?,?,?)}");
					csmt.setString(1, sEmail);
					csmt.setString(2, sPassword);
					csmt.registerOutParameter(3, Types.VARCHAR);
					csmt.execute();
					String sFirstName = csmt.getString(3);
					if (sFirstName.equals("")) {
						LOG.error("seesion name not created ");
					} else {
						LOG.info(sFirstName + "succesfully login with session name");
					}
					HttpSession session = request.getSession(true);
					session.setAttribute("Session_Name", sFirstName);
					response.getWriter().write(sControllerResponse);
				} catch (Exception e) {
					LOG.error("MainController genarate exception" + e, e);
				}
			}

		} catch (Exception e) {
			LOG.error("MainController genarate exception" + e, e);
		}

	}

}
