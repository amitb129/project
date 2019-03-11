package com.task;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class StartSession
 */
/**
 * right now no use of this class created for experimental purpose
 */
@WebServlet("/StartSession")
public class StartSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpServletRequest request;
	HttpServletResponse response;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.sendRedirect("http://www.google.com");

		// HttpSession session=request.getSession();
		// session.setAttribute("Session_Name","hi");
		// RequestDispatcher rd=request.getRequestDispatcher("Welcome.jsp");
		// rd.forward(request, response);

	}

	public void SessionStart() {

		try {

			/*
			 * Class.forName("org.postgresql.Driver"); Connection
			 * con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/Tast1",
			 * "postgres", "postgres");
			 * 
			 * PreparedStatement ps=con.
			 * prepareStatement("select Registration_Details.f_name from u_login inner join Registration_Details on u_login.user_id = Registration_Details.R_id where email_id=? AND password=?;"
			 * ); ps.setString(1,email); ps.setString(2,password);
			 * 
			 * ResultSet rs=ps.executeQuery(); //rs.next();
			 * //response.sendRedirect("http://www.google.com"); // rs.next(); //String
			 * S_Name=rs.getString("Registration_Details.f_name");
			 */
			HttpSession session = request.getSession(true);
			session.setAttribute("Session_Name", "hi");
			// response.sendRedirect("http://www.google.com");
			response.sendRedirect("Welcome.jsp");

		} catch (Exception e) {
			System.out.println(e);
		}

		// return true;

	}

}
