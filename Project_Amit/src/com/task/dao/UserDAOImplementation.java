package com.task.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.task.bo.UserData;
import com.task.log.ProjectLogging;

public class UserDAOImplementation implements UserDAO {

	HttpServletRequest request;
    
	HttpServletResponse response;
	
	/**
	 * create object for log4j class
	 */
	//private static final Logger LOG = Logger.getLogger(Login.class);
		private static final ProjectLogging LOG = new ProjectLogging(); 
	/**
	 * create object of connection pool and Connection
	 */
	ConnectionPooling objConCP;
	private Connection objCon;
	/**
	 *Login user
	 */
	@Override
	public String UserLogin(UserData objUserData) {
		LOG.printdebug("Interface MainController UserLogin called");

		/**
		 * create connection with connection pool
		 */

		objConCP = new ConnectionPooling(); // initiate the object

		objCon = objConCP.getConnection(); // call connection from connection pooling class
		LOG.printdebug("concp object create");
		LOG.printinfo("Login Process initiate ");
		String sEmail = objUserData.getsEmail();
		String sPassword = objUserData.getsPassword();
		String sControllerResponse = "False";
		LOG.printdebug(sEmail+sPassword);
		Login objBean = new Login();
		objBean.setEmail(sEmail);
		objBean.setPassword(sPassword);
		boolean bStatus = objBean.validate(); // validate user login credentials with email id and password of
												// registered
												// user

		if (bStatus) {

			try {

				/**
				 * fetch the user name and start with user first name
				 */

				LOG.printinfo("successfully login");
				/**
				 * fetch data with using store procedure
				 */
				sControllerResponse="True";

			} catch (Exception e) {
				LOG.printerror("login or session create exception" + e, e);
			}

			finally {
				try {
					objCon.close();
					objCon = null;
					LOG.printinfo("Login Process End ");
					LOG.printdebug("conection details:");
					//LOG.printdebug(objCon);
				} catch (SQLException e) {
					LOG.printerror("login or session create exception" + e, e);

				} catch (Exception e) {
					LOG.printerror("login or session create exception" + e, e);
				}
			}

		} else {
			LOG.printinfo(" login not sccessful");
			sControllerResponse="False";
			LOG.printinfo("Login Process End ");
		}

		
		return sControllerResponse;
		
	
	}
	
	/**
	 * Add new user
	 */
	@Override
	public String InsertUser(UserData objUserData) {
		LOG.printdebug("Interface MainController InsertUser called");
		/**
		 * create connection with coonection pool
		 */
		objConCP = new ConnectionPooling();
		objCon = objConCP.getConnection();

		String sUserId = objUserData.getsUserId();
		String sFirstName =  objUserData.getsFirstName();
		String sLastName =  objUserData.getsLastName();
		String sRoleName =  objUserData.getsRoleName();
		String sEmailId = objUserData.getsEmail();
		String sPassword = objUserData.getsPassword();
		String sAddUserQueryStatus;
		String sAddNewUserResponse = "False";

		LOG.printinfo("Add user process initiate");

		try {

			/**
			 * insert new user data into u_login and registration_details table
			 */

			/**
			 * fetch data with using store procedure
			 */

			CallableStatement csmt = objCon.prepareCall("{? = call AddUser1(?,?,?,?,?,?)}");
			csmt.registerOutParameter(1, Types.VARCHAR);
			csmt.setString(2, sUserId);
			csmt.setString(3, sPassword);
			csmt.setString(4, sEmailId);
			csmt.setString(5, sFirstName);
			csmt.setString(6, sLastName);
			csmt.setString(7, sRoleName);
			csmt.execute();
			sAddUserQueryStatus = csmt.getString(1);
			if (sAddUserQueryStatus.equals("true")) {
				LOG.printinfo("AddUserQueryStatus: " + sAddUserQueryStatus);
				LOG.printinfo(sFirstName + " user added");
				LOG.printdebug("AddUserQueryStatus: "+sAddUserQueryStatus);
				sAddNewUserResponse = "True";
				
			} else {
				LOG.printinfo("AddUserQueryStatus: " + sAddUserQueryStatus);
				LOG.printinfo("May be" + sUserId + " or " + sEmailId + " already exist");
				sAddNewUserResponse = "False";
			}

		} catch (Exception e) {
			LOG.printerror("Add user process generate exception" + e, e);
		} finally {
			try {
				objCon.close();
				LOG.printinfo("Add user process End");
			} catch (SQLException e) {
				LOG.printerror("Add user process generate exception" + e, e);
				
			} catch (Exception e) {
				LOG.printerror("Add user process generate exception" + e, e);
				
			}
		}

	return sAddNewUserResponse;
	}
	
	/**
	 * check existing user id  on database
	 */
	@Override
	public String CheckUserId(UserData objUserData) {

		LOG.printdebug("Interface MainController CheckUserId");
		/**
		 * create connection with connection pool
		 */
		objConCP = new ConnectionPooling();
		objCon = objConCP.getConnection();

		String sUserId = objUserData.getsUserId();
		String sUserIdStatus;
		String sCheckUserResponse = "false";  

		try {

			if (sUserId != "") {
				
				/**
				 * fetch data with using store procedure
				 */

				CallableStatement csmt = objCon.prepareCall("{call usernamecheck(?,?)}");
				csmt.setString(1, sUserId);
				csmt.registerOutParameter(2, Types.VARCHAR);
				csmt.execute();
				sUserIdStatus = csmt.getString(2);
				if (sUserIdStatus.equals("true")) {
					LOG.printdebug("UserIdStatus:" + sUserIdStatus);
					sCheckUserResponse = "User";  
				} else {
					sCheckUserResponse = "false";  
				}

			}

		} catch (Exception e) {
			LOG.printerror("Check user id generate exception" + e, e);
		} finally {
			try {
				objCon.close();
			} catch (SQLException e) {
				LOG.printerror("Check user id generate exception" + e, e);
			} catch (Exception e) {
				LOG.printerror("Check user id generate exception" + e, e);
			}
		}
		return sCheckUserResponse;
	
	}
	
	/**
	 * check existing email id on database 
	 */
	@Override
	public String CheckEmailId(UserData objUserData) {

		LOG.printdebug(" Interface MainController CheckEmailId");
		/**
		 * create connection with connection pool
		 */
		objConCP = new ConnectionPooling();
		objCon = objConCP.getConnection();

		String sEmailId = objUserData.getsEmail();
		String sEmailIdStatus;
		String sCheckEmailResponse = "false";

		try {

			if (sEmailId != "") {

				/**
				 * fetch data with using store procedure
				 */

				CallableStatement csmt = objCon.prepareCall("{call useremailcheck(?,?)}");
				csmt.setString(1, sEmailId);
				csmt.registerOutParameter(2, Types.VARCHAR);
				csmt.execute();
				sEmailIdStatus = csmt.getString(2);
				if (sEmailIdStatus.equals("true")) {
					LOG.printdebug("UserIdStatus:" + sEmailIdStatus);
					
					sCheckEmailResponse = "Email";
				} else {
					sCheckEmailResponse = "false";
				}
			}

		} catch (Exception e) {
			LOG.printerror("Check email generate exception" + e, e);
			
		} finally {
			try {
				objCon.close();
			} catch (SQLException e) {
				LOG.printerror("Check email generate exception" + e, e);
				
			} catch (Exception e) {
				LOG.printerror("Check email generate exception" + e, e);
			}
		}
		
		return sCheckEmailResponse;
	
	}

	/**
	 * delete user from database
	 */
	@Override
	public String deleteuser(UserData objUserData) {

		LOG.printdebug(" Interface MainController deleteuser");
		/**
		 * create connection with connection pool
		 */
		objConCP = new ConnectionPooling();
		objCon = objConCP.getConnection();
		String sdeleteUserInfo = objUserData.getsUserId();
		String sdeletestatus;
		String sCheckUserResponse="false";

		LOG.printinfo("Delete user process initate");

		try {

			/**
			 * execute query with using store procedure
			 */
			CallableStatement csmt = objCon.prepareCall("{call DeleteUser(?,?)}");
			csmt.setString(1, sdeleteUserInfo);
			csmt.registerOutParameter(2, Types.VARCHAR);
			csmt.execute();
			sdeletestatus = csmt.getString(2);
			if (sdeletestatus.equals("true")) {
				LOG.printdebug("deletestatus:"+sdeletestatus);
				LOG.printinfo("deletestatus:" + sdeletestatus);
				LOG.printinfo(sdeleteUserInfo + "successfully deleted");
				
				sCheckUserResponse="True";
			} else {
				LOG.printinfo("deletestatus:" + sdeletestatus);
				LOG.printinfo(sdeleteUserInfo + "not deleted");
				sCheckUserResponse="false";
			}

		} catch (Exception e) {
			
			LOG.printerror("Exception generate in delete user" + e, e);

		} finally {
			try {
				objCon.close();
				LOG.printinfo("Delete user process end");
			} catch (SQLException e) {
				LOG.printerror("Exception generate in delete user" + e, e);
			} catch (Exception e) {
				LOG.printerror("Exception generate in delete user" + e, e);
			}
		}

		return sCheckUserResponse;
	
	}
	
	/**
	 * update user data
	 */
	@Override
	public String updateuser(UserData objUserData) {

		LOG.printdebug(" Interface  MainController updateuser");
		/**
		 * create connection with connection pool
		 */
		objConCP = new ConnectionPooling();
		objCon = objConCP.getConnection();

		String sEmailId = objUserData.getsEmail();
		String sFirstName = objUserData.getsFirstName();
		String sLastName = objUserData.getsLastName();
		String sRoleName = objUserData.getsRoleName();
		String sUserid = objUserData.getsUserId();
		String sUpdateStatus;
		String sUpdateUserResponse = "false";
		
		LOG.printinfo("Update user details process initiate");
		
		try {
			
			/**
			 * execute query with using store procedure
			 */
			
			CallableStatement csmt = objCon.prepareCall("{call UpdateUser(?,?,?,?,?,?)}");
			csmt.setString(1, sEmailId);
			csmt.setString(2, sFirstName);
			csmt.setString(3, sLastName);
			csmt.setString(4, sRoleName);
			csmt.setString(5, sUserid);
			csmt.registerOutParameter(6, Types.VARCHAR);
			csmt.execute();
			sUpdateStatus = csmt.getString(6);
			if(sUpdateStatus.equals("true")) {
				LOG.printdebug("UpdateStatus:"+sUpdateStatus);
				LOG.printinfo("UpdateStatus:"+sUpdateStatus);
				LOG.printinfo(sUserid+" detalis updated");
				
				sUpdateUserResponse = "True";
			}
			else {
				LOG.printinfo("UpdateStatus:"+sUpdateStatus);
				LOG.printinfo(sUserid+" detalis not updated");
				LOG.printinfo("May be"+sUserid+"or "+sEmailId+" already exist");
				
				sUpdateUserResponse = "false";
			}

		} catch (Exception e) {

			
			LOG.printerror("Exception generate in update user"+e,e);

		} finally {
			try {
				objCon.close();
				LOG.printinfo("Update user details process end");
			} catch (SQLException e) {
				
				LOG.printerror("Exception generate in update user"+e,e);
			} catch (Exception e) {
				
				LOG.printerror("Exception generate in update user"+e,e);
			}
		}

		return sUpdateUserResponse;
	
	}

}
