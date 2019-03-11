package com.task;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class UserDAOImplementation implements UserDAO {

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
	
	@Override
	public String UserLogin(UserData objUserData) {
		System.out.println("Interface UserLogin called");

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

	@Override
	public String InsertUser(UserData objUserData) {
		System.out.println("Interface InsertUser called");
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

	@Override
	public String CheckUserId(UserData objUserData) {

		System.out.println("Interface CheckUserId");
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

	@Override
	public String CheckEmailId(UserData objUserData) {

		System.out.println(" Interface CheckEmailId");
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

	@Override
	public String deleteuser(UserData objUserData) {

		System.out.println(" Interface deleteuser");
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

	@Override
	public String updateuser(UserData objUserData) {

		System.out.println(" Interface updateuser");
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
