<!-- 
 File Header            : index.jsp
 Description            : Index page is user login here we validating user login details.
 Author                 : Amit Banik 
 Created On             : 10-10-2018
 Maintenance History    : Amit Nov 2018 - Added jquery/ajax for client validation and send data to
 										  java class for login.
 										- Added some coding standard.
 Copyright © iTech Workshop Private Limited 2018 All rights reserved.
 -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);
	//prevents caching at the proxy server
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="icon" type="image/png" href="images/icons/favicon.ico" />
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="js/emailvalidation.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.js"
	integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
	crossorigin="anonymous"></script>

</head>
<body id="LoginForm">
	<form>
		<div class="container">

			<div class="login-form">
				<div class="main-div">
					<div class="panel">
						<%
							String name = (String) session.getAttribute("Session_Name");
							if (name != null) {

								response.sendRedirect("Welcome.jsp");
							}
						%>

						<p>Please enter your email and password</p>
						<p id="message"></p>
					</div>
					<form id="Login">

						<div class="form-group">


							<input type="text" class="form-control" name="i_Email"
								id="i_Email" required
								pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" autofocus
								placeholder="Email Address" title="Enter valid email id">


						</div>

						<div class="form-group">

							<input type="password" class="form-control" name="i_Password"
								id="i_Password" required placeholder="Password"
								title="Enter your password">

						</div>
						<div class="forgot">
							<!--<a href="reset.html">Forgot password?</a> -->
						</div>
						<button type="button" id='btnValidate' class="btn btn-primary">Login</button>
							
					</form>
					
				</div>
			</div>
		</div>
		</div>

	</form>

</body>
</html>
