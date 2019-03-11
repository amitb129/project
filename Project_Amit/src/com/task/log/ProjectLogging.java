package com.task.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProjectLogging {

	private static final Log infoLogger = LogFactory.getLog("PROJECT-INFO");
	private static final Log errorLogger = LogFactory.getLog("PROJECT-ERROR");
	private static final Log debugLogger = LogFactory.getLog("PROJECT-DEBUG");
	
	private boolean DEBUG = true;
	private boolean INFO = true;
	private boolean ERROR = true;

	
	public void printinfo(String sInfo) {
		
		try {
			if(INFO) {
				if (infoLogger.isInfoEnabled())//from log4j.xml
					infoLogger.info(sInfo);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void printerror(String sError, Throwable t ) {
		
		try {
			if(ERROR) {
				if(errorLogger.isErrorEnabled())
					errorLogger.error(sError,t);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void printdebug(String sDebug) {
		
		try {
			if(DEBUG) {
				if(debugLogger.isDebugEnabled())
					debugLogger.debug(sDebug);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	
}
