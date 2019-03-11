/*****************************************************************************
 File Header            : UserDataDao.java
 Description            : perform various function based on user request on user list like search and
 						  list all user data. And also calculate total page.
 Author                 : Amit Banik 
 Created On             : 23-10-2018
 Maintenance History    : Amit Nov 2018 - Added connection pooling.
  										- Added store procedure 
  										- Added log4j
 										- Added some coding standard.
 Copyright © iTech Workshop Private Limited 2018 All rights reserved.
 ****************************************************************************/
package com.task.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.task.bo.UserData;
import com.task.log.ProjectLogging;

public class UserDataDao {

	/**
	 * create object of connection pool and Connection
	 */
	static ConnectionPooling objConCP;
	private static Connection objCon;
	
	/**
	 * create object for log4j class
	 */
	//private static final Logger LOG = Logger.getLogger(Login.class);
		private static final ProjectLogging LOG = new ProjectLogging(); 
	
	/**
	 * search no of page with store procedure
	 */
	public static int NoofPages(String sSearchName, String sSearchType) {
		int iNumberOfPages = 0;
		try {
			/**
			 * create connection with connection pool
			 */
	
			objConCP = new ConnectionPooling();
			objCon = objConCP.getConnection();

			CallableStatement csmt = objCon.prepareCall("{call NoOfPage(?,?,?)}");
			csmt.setString(1, sSearchName);
			csmt.setString(2, sSearchType);
			csmt.registerOutParameter(3, Types.INTEGER);
			csmt.execute();
			int iTotalNumberOfRecords = csmt.getInt(3);
			

			iNumberOfPages = iTotalNumberOfRecords / 3;
			if (iTotalNumberOfRecords % 3 != 0) {
				iNumberOfPages = iNumberOfPages + 1;
			}

		} catch (Exception e) {
			
			LOG.printerror("UserDataDao generate exception "+e,e);
		} finally {
			try {
				objCon.close();
			} catch (SQLException e) {
				
				LOG.printerror("UserDataDao generate exception "+e,e);
			} catch (Exception e) {
				
				LOG.printerror("UserDataDao generate exception "+e,e);
			}
		}
		return iNumberOfPages;
	}
	

	/**
	 * fetch data from db for pagination
	 */
	public static List<UserData> getRecords(int iNoOfRow, int iLimit, String sSearchName, String sSearchType) {
		List<UserData> list = new ArrayList<UserData>();
		try {
			
			/**
			 * create connection with connection pool
			 */
			objConCP = new ConnectionPooling();
			objCon = objConCP.getConnection();
			LOG.printdebug("name= " + sSearchName + "type= " + sSearchType);
			/**
			 * execute query with using store procedure
			 */
			
			CallableStatement csmt = objCon.prepareCall("{call AllUserDetail1(?,?,?,?)}");
			csmt.setInt(1, iNoOfRow);
			csmt.setInt(2, iLimit);
			csmt.setString(3,sSearchName);
			csmt.setString(4,sSearchType);
			csmt.execute();
			
			ResultSet rs = csmt.getResultSet();
			LOG.printdebug("calable data "+rs);
			while (rs.next()) {
				UserData objUserData = new UserData();
				objUserData.setsUserId(rs.getString(1));
				objUserData.setsEmail(rs.getString(2));
				objUserData.setsFirstName(rs.getString(3));
				objUserData.setsLastName(rs.getString(4));
				objUserData.setsRoleName(rs.getString(5));
				list.add(objUserData);
				
				
			}
			//LOG.printdebug(list);
		} catch (Exception e) {
			LOG.printerror("UserDataDao generate exception "+e,e);
		} finally {
			try {
				objCon.close();
			} catch (SQLException e) {
				LOG.printerror("UserDataDao generate exception "+e,e);
			} catch (Exception e) {
				LOG.printerror("UserDataDao generate exception "+e,e);
			}
		}

		return list;
	}

}
