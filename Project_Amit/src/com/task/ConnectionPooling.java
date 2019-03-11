/*****************************************************************************
 File Header            : ConnectionPooling.java
 Description            : connection pooling class.
 Author                 : Amit Banik 
 Created On             : 05-11-2018
 Maintenance History    : Amit Nov 2018 - Added some coding standard.
 										- Added log4j.
 Copyright © iTech Workshop Private Limited 2018 All rights reserved.
 ****************************************************************************/

package com.task;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import java.sql.Connection;
import javax.naming.*;

public class ConnectionPooling {

	/**
	 * create object for log4j class
	 */
	private static final Logger objInitContext = Logger.getLogger(ConnectionPooling.class);

	public Connection getConnection() {
		Connection objConeCP = null;
		try {

			Context objInitContext = new InitialContext();
			DataSource objDseCP = (DataSource) objInitContext.lookup("java:jboss/PostgresXADS");
			objConeCP = (Connection) objDseCP.getConnection();
			// System.out.println(objConeCP);
		} catch (Exception e) {
			// e.printStackTrace();
			objConeCP = null;
			objInitContext.error("Connection pooling generate exception" + e, e);
		} finally {

		}
		return objConeCP;
	}

}
