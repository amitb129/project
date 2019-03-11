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
package com.task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import java.sql.*;

public class UserDataDao {

	/**
	 * create object of connection pool and Connection
	 */
	static ConnectionPooling objConCP;
	private static Connection objCon;
	
	/**
	 * create object for log4j class
	 */
	private static final Logger LOG = Logger.getLogger(UserDataDao.class);
	
	/**
	 * search no of page without store procedure
	 */
	/*
	public static int NoofPages() {
		int numberOfPages = 0;
		try {
			/**
			 * create connection with connection pool
			 */
	/*
			objConCP = new ConnectionPooling();
			objCon = objConCP.getConnection();

			PreparedStatement ps1 = objCon.prepareStatement(
					"select count(*) from u_login inner join Registration_Details on u_login.user_id = Registration_Details.R_id ");
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();
			int totalNumberOfRecords = rs1.getInt(1);
			rs1.close();

			numberOfPages = totalNumberOfRecords / 3;
			if (totalNumberOfRecords % 3 != 0) {
				numberOfPages = numberOfPages + 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				objCon.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return numberOfPages;
	}

	
	/**
	 * page count with searchvalue
	 **//*
	public static int SearchNoofPages(String s_name, String s_type) {

		int numberOfPages = 0;
		try {

			/**
			 * create connection with connection pool
			 *//*
			objConCP = new ConnectionPooling();
			objCon = objConCP.getConnection();

			/**
			 * 
			 * page count base on search type request
			 **//*
			if (s_type.equals("exact")) {
				PreparedStatement ps1 = objCon.prepareStatement(
						"select count(*) from u_login inner join Registration_Details on u_login.user_id = Registration_Details.R_id where Registration_Details.f_name LIKE '%"
								+ s_name + "%'");
				ResultSet rs1 = ps1.executeQuery();
				rs1.next();
				int totalNumberOfRecords = rs1.getInt(1);
				rs1.close();

				numberOfPages = totalNumberOfRecords / 3;
				if (totalNumberOfRecords % 3 != 0) {
					numberOfPages = numberOfPages + 1;
				}
			} else if (s_type.equals("start")) {
				PreparedStatement ps1 = objCon.prepareStatement(
						"select count(*) from u_login inner join Registration_Details on u_login.user_id = Registration_Details.R_id where Registration_Details.f_name LIKE '"
								+ s_name + "%' ");
				ResultSet rs1 = ps1.executeQuery();
				rs1.next();
				int totalNumberOfRecords = rs1.getInt(1);
				rs1.close();

				numberOfPages = totalNumberOfRecords / 3;
				if (totalNumberOfRecords % 3 != 0) {
					numberOfPages = numberOfPages + 1;
				}
			} else if (s_type.equals("end")) {

				PreparedStatement ps1 = objCon.prepareStatement(
						"select count(*) from u_login inner join Registration_Details on u_login.user_id = Registration_Details.R_id where Registration_Details.f_name LIKE '%"
								+ s_name + "' ");
				ResultSet rs1 = ps1.executeQuery();
				rs1.next();
				int totalNumberOfRecords = rs1.getInt(1);
				rs1.close();

				numberOfPages = totalNumberOfRecords / 3;
				if (totalNumberOfRecords % 3 != 0) {
					numberOfPages = numberOfPages + 1;
				}
				System.out.println("s_name" + s_name + "s_type" + s_type);
				System.out.println(numberOfPages);
			} else {
				System.out.println("out of reach");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				objCon.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return numberOfPages;

	}
	*/
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
			//e.printStackTrace();
			LOG.error("UserDataDao generate exception "+e,e);
		} finally {
			try {
				objCon.close();
			} catch (SQLException e) {
				//e.printStackTrace();
				LOG.error("UserDataDao generate exception "+e,e);
			} catch (Exception e) {
				//e.printStackTrace();
				LOG.error("UserDataDao generate exception "+e,e);
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
			System.out.println("name= " + sSearchName + "type= " + sSearchType);
			/**
			 * execute query without using store procedure
			 */
			/*
			if (s_name == "na" && s_type == "na") {
				System.out.println("im default");
				/**
				 * execute query without using store procedure
				 */
				/*
				PreparedStatement ps = objCon.prepareStatement(
						"select * from u_login inner join Registration_Details on u_login.user_id = Registration_Details.R_id limit "
								+ start + " offset " + total);
				ResultSet rs = ps.executeQuery();
				*/
				/*
				PreparedStatement ps = objCon.prepareStatement("select * from AllUserDetails(?,?)");
				ps.setInt(1, start);
				ps.setInt(2, total);
				 
				 */
			/*	
			}
		
			/**
			 * fetch data from db for pagination based on search request
			 */
			/*
			else if (s_type.equals("exact")) {

				System.out.println("im exact");
				PreparedStatement ps = objCon.prepareStatement(
						"select * from u_login inner join Registration_Details on u_login.user_id = Registration_Details.R_id where Registration_Details.f_name LIKE '%"
								+ s_name + "%' limit " + start + " offset " + total);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					UserData e = new UserData();
					e.setUserId(rs.getString(1));
					e.setPassword(rs.getString(2));
					e.setEmail(rs.getString(4));
					e.setFname(rs.getString(5));
					e.setLname(rs.getString(6));
					e.setRname(rs.getString(7));
					list.add(e);
				}

			} else if (s_type.equals("start")) {
				System.out.println("im start");
				PreparedStatement ps = objCon.prepareStatement(
						"select * from u_login inner join Registration_Details on u_login.user_id = Registration_Details.R_id where Registration_Details.f_name LIKE '"
								+ s_name + "%' limit " + start + " offset " + total);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					UserData e = new UserData();
					e.setUserId(rs.getString(1));
					e.setPassword(rs.getString(2));
					e.setEmail(rs.getString(4));
					e.setFname(rs.getString(5));
					e.setLname(rs.getString(6));
					e.setRname(rs.getString(7));
					list.add(e);
				}

			} else if (s_type.equals("end")) {
				System.out.println("im end");
				PreparedStatement ps = objCon.prepareStatement(
						"select * from u_login inner join Registration_Details on u_login.user_id = Registration_Details.R_id where Registration_Details.f_name LIKE '%"
								+ s_name + "' limit " + start + " offset " + total);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					UserData e = new UserData();
					e.setUserId(rs.getString(1));
					e.setPassword(rs.getString(2));
					e.setEmail(rs.getString(4));
					e.setFname(rs.getString(5));
					e.setLname(rs.getString(6));
					e.setRname(rs.getString(7));
					list.add(e);
				}

			} else {

				System.out.println("im nothing");
			}
			*/
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
			System.out.println("calable data "+rs);
			while (rs.next()) {
				UserData e = new UserData();
				e.setUserId(rs.getString(1));
				//e.setPassword(rs.getString(2));
				e.setEmail(rs.getString(2));
				e.setFname(rs.getString(3));
				e.setLname(rs.getString(4));
				e.setRname(rs.getString(5));
				list.add(e);
				//System.out.println(list);
				
			}
			//System.out.println(list);
		} catch (Exception e) {
			//e.printStackTrace();
			LOG.error("UserDataDao generate exception "+e,e);
		} finally {
			try {
				objCon.close();
			} catch (SQLException e) {
				//e.printStackTrace();
				LOG.error("UserDataDao generate exception "+e,e);
			} catch (Exception e) {
				//e.printStackTrace();
				LOG.error("UserDataDao generate exception "+e,e);
			}
		}

		return list;
	}

}
