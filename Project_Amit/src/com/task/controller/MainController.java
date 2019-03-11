/*****************************************************************************
 File Header            : MainController.java
 Description            : Controller servlet for loosely couple.
 Author                 : Amit Banik 
 Created On             : 30-10-2018
 Maintenance History    : Amit Nov 2018 - Created each operation class.
 Copyright © iTech Workshop Private Limited 2018 All rights reserved.
 ****************************************************************************/
package com.task.controller;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.task.bo.UserData;
import com.task.dao.ConnectionPooling;
import com.task.dao.UserDAO;
import com.task.dao.UserDAOImplementation;
import com.task.log.ProjectLogging;

/**
 * Servlet implementation class MainController
 */
@WebServlet("/MainController1")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final Logger LOG = Logger.getLogger(MainController.class);
	private static final ProjectLogging LOG = new ProjectLogging(); 
	
	/**
	 * create object of connection pool and Connection
	 */
	ConnectionPooling objConCP;
	private Connection objCon;

	/**
	 * create object of operation class
	 */
	UserData objUserData;
	UserDAO objUserDAOImplementation;

	public void init(ServletConfig config) throws ServletException {
		/**
		 * initiate object of operation class
		 */
		
		objUserData = new UserData();
		objUserDAOImplementation = new UserDAOImplementation();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String sAction = request.getParameter("action");
		LOG.printdebug(sAction);
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
			case "sessionout":
				doSessionOut(request, response);
				break;
			case "apiWelcome":
				System.out.println("switch apiWelcome");
				doApiHomePage(request, response);
				break;
			}
		} catch (Exception e) {
			LOG.printerror("MainController genarate exception", e);
		}

	}
	
	private void doApiHomePage(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("doApiHomePage called");
		objConCP = new ConnectionPooling(); // initiate the object

		objCon = objConCP.getConnection(); // call connection from connection pooling class
		String sTokenId = request.getParameter("TokenID");
		if(sTokenId.equals("1234")){
			try {

				LOG.printdebug("doApiHomePage called");
				response.setContentType("text/html;charset=UTF-8");
				String sEmail = request.getParameter("email");
				String sPassword = request.getParameter("password");
				System.out.println(sEmail);
				System.out.println(sPassword);
				objUserData.setsEmail(sEmail);
				objUserData.setsPassword(sPassword);
				String sControllerResponse = objUserDAOImplementation.UserLogin(objUserData);
				LOG.printdebug(sControllerResponse);
				if (sControllerResponse.equals("True")) {
					System.out.println("login true");
					LOG.printdebug("true");
					try {
						CallableStatement csmt = objCon.prepareCall("{call UserNameSession(?,?,?)}");
						csmt.setString(1, sEmail);
						csmt.setString(2, sPassword);
						csmt.registerOutParameter(3, Types.VARCHAR);
						csmt.execute();
						String sFirstName = csmt.getString(3);
						if (sFirstName.equals("")) {
							LOG.printinfo("seesion name not created");
						} else {
							LOG.printinfo(sFirstName + "succesfully login with session name");
						}
						HttpSession session = request.getSession(true);
						session.setAttribute("Session_Name", sFirstName);
						String s= (String) session.getAttribute("Session_Name");
						System.out.println(s);
						response.sendRedirect("Welcome.jsp");
						
					} catch (Exception e) {
						LOG.printerror("MainController genarate exception" + e, e);
					}
					
				}
				else {
					
					PrintWriter out = response.getWriter();
					HttpSession session = request.getSession();
					session.invalidate();
					
					out.println("<script type=\"text/javascript\">");
					out.println("alert('unthorized api');");
					out.println("</script>");
				}

			} catch (Exception e) {
				LOG.printerror("MainController genarate exception" + e, e);
			}
		}
		else {
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println("<script type=\"text/javascript\">");
			out.println("alert('unthorized api');");
			out.println("</script>");
		}
			

	
		
	}

	private void doSessionOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		LOG.printdebug("doSessionOut");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			LOG.printinfo("Logout process initiate");
			HttpSession session = request.getSession();
			String sName = (String) session.getAttribute("Session_Name");
			session.invalidate();
			LOG.printinfo(sName + " logout ");
			// response.sendRedirect("index.jsp");
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Succesfully logout');");
			out.println("location='index.jsp';");
			out.println("</script>");
		} catch (Exception e) {
			LOG.printerror("Exception generate in session logout" , e);
		} finally {
			LOG.printinfo("Logout process End");
		}
		
	}

	/**
	 * check user id controller
	 */
	private void doCheckUser(HttpServletRequest request, HttpServletResponse response) {

		LOG.printdebug("doCheckUser");
		try {
			response.setContentType("text/html;charset=UTF-8");
			String sUserId = request.getParameter("UserId");
			objUserData.setsUserId(sUserId);
			String sCheckUserResponse = objUserDAOImplementation.CheckUserId(objUserData);
			response.getWriter().write(sCheckUserResponse);
		} catch (Exception e) {
			LOG.printerror("MainController genarate exception" + e, e);
		}

	}
	
	/**
	 * check email id controller
	 */
	private void doCheckEmail(HttpServletRequest request, HttpServletResponse response) {
		LOG.printdebug("doCheckEmail");
		try {

			response.setContentType("text/html;charset=UTF-8");
			String sEmailId = request.getParameter("EmailId");
			objUserData.setsEmail(sEmailId);
			String sCheckEmailResponse = objUserDAOImplementation.CheckEmailId(objUserData);
			response.getWriter().write(sCheckEmailResponse);
		} catch (Exception e) {
			LOG.printerror("MainController genarate exception "+e, e);
		}

	}
	
	/**
	 * Delete user controller
	 */
	private void doDeleteUser(HttpServletRequest request, HttpServletResponse response) {
		LOG.printdebug("doDeleteUser");
		try {

			response.setContentType("text/html;charset=UTF-8");
			String sdeleteUserInfo = request.getParameter("userinfo");
			objUserData.setsUserId(sdeleteUserInfo);
			String sDeleteUserResponse = objUserDAOImplementation.deleteuser(objUserData);
			response.getWriter().write(sDeleteUserResponse);
		} catch (Exception e) {
			LOG.printerror("MainController genarate exception" + e, e);
		}

	}

	/**
	 * update user controller
	 */
	private void doEditUser(HttpServletRequest request, HttpServletResponse response) {
		LOG.printdebug("doEditUser");
		try {

			response.setContentType("text/html;charset=UTF-8");

			String sEmailId = request.getParameter("useremail");
			String sFirstName = request.getParameter("userfname");
			String sLastName = request.getParameter("userlname");
			String sRoleName = request.getParameter("userrname");
			String sUserid = request.getParameter("userid");
			objUserData.setsEmail(sEmailId);
			objUserData.setsFirstName(sFirstName);
			objUserData.setsLastName(sLastName);
			objUserData.setsRoleName(sRoleName);
			objUserData.setsUserId(sUserid);
			
			String sUpdateUserResponse = objUserDAOImplementation.updateuser(objUserData);
			LOG.printdebug(sUpdateUserResponse);
			response.getWriter().write(sUpdateUserResponse);
		} catch (Exception e) {
			LOG.printerror("MainController genarate exception" + e, e);
		}

	}
	
	/**
	 * insert user new data controller
	 */
	private void doInsertUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			LOG.printdebug("doInsertUser called");
			response.setContentType("text/html;charset=UTF-8");
			String sUserId = request.getParameter("UserId");
			String sFirstName = request.getParameter("Fname");
			String sLastName = request.getParameter("lname");
			String sRoleName = request.getParameter("Rname");
			String sEmailId = request.getParameter("EmailId");
			String sPassword = request.getParameter("Pwd");
			objUserData.setsUserId(sUserId);
			objUserData.setsFirstName(sFirstName);
			objUserData.setsLastName(sLastName);
			objUserData.setsRoleName(sRoleName);
			objUserData.setsEmail(sEmailId);
			objUserData.setsPassword(sPassword);
			
			String sAddNewUserResponse = objUserDAOImplementation.InsertUser(objUserData);
			response.getWriter().write(sAddNewUserResponse);
		} catch (Exception e) {
			LOG.printerror("MainController genarate exception" + e, e);
		}

	}
	
	/**
	 * login user controller 
	 */
	private void doLoginUser(HttpServletRequest request, HttpServletResponse response) {
		objConCP = new ConnectionPooling(); // initiate the object

		objCon = objConCP.getConnection(); // call connection from connection pooling class
		try {

			LOG.printdebug("doLoginUser called");
			response.setContentType("text/html;charset=UTF-8");
			String sEmail = request.getParameter("i_Email");
			String sPassword = request.getParameter("i_Password");
			objUserData.setsEmail(sEmail);
			objUserData.setsPassword(sPassword);
			String sControllerResponse = objUserDAOImplementation.UserLogin(objUserData);
			LOG.printdebug(sControllerResponse);
			if (sControllerResponse.equals("True")) {
				LOG.printdebug("true");
				try {
					CallableStatement csmt = objCon.prepareCall("{call UserNameSession(?,?,?)}");
					csmt.setString(1, sEmail);
					csmt.setString(2, sPassword);
					csmt.registerOutParameter(3, Types.VARCHAR);
					csmt.execute();
					String sFirstName = csmt.getString(3);
					if (sFirstName.equals("")) {
						LOG.printinfo("seesion name not created");
					} else {
						LOG.printinfo(sFirstName + "succesfully login with session name");
					}
					HttpSession session = request.getSession(true);
					session.setAttribute("Session_Name", sFirstName);
					String s= (String) session.getAttribute("Session_Name");
					System.out.println(s);
					response.getWriter().write(sControllerResponse);
				} catch (Exception e) {
					LOG.printerror("MainController genarate exception" + e, e);
				}
			}

		} catch (Exception e) {
			LOG.printerror("MainController genarate exception" + e, e);
		}

	}

}
