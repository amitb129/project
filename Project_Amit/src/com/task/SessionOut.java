/*****************************************************************************
 File Header            : SessionOut.java
 Description            : invalidate the session with user name.
 Author                 : Amit Bnaik 
 Created On             : 11-10-2018
 Maintenance History    : Amit Nov 2018 - Added log4j.
 										- Added some coding standard.
 Copyright © iTech Workshop Private Limited 2018 All rights reserved.
 ****************************************************************************/
package com.task;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

@WebServlet("/SessionOut")
public class SessionOut extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * create object for log4j class
	 */
	private static final Logger LOG = Logger.getLogger(SessionOut.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			LOG.info("Logout process initiate");
			HttpSession session = request.getSession();
			String sName = (String) session.getAttribute("Session_Name");
			session.invalidate();
			LOG.info(sName + " logout ");
			// response.sendRedirect("index.jsp");
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Succesfully logout');");
			out.println("location='index.jsp';");
			out.println("</script>");
		} catch (Exception e) {
			LOG.error("Exception generate in session logout" + e);
		} finally {
			LOG.info("Logout process End");
		}

	}
}
