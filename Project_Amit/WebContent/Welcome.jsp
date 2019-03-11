<!-- 
 File Header            : index.jsp
 Description            : Its our home page from here we can do multiple user functionality like
 						  add new user, show list of user, updation , delete , edit and logout.
 Author                 : Amit Banik 
 Created On             : 13-10-2018
 Maintenance History    : Amit Nov 2018 - Added jquery/ajax for client validation and send data to
 										  java class.
 										- Added some coding standard.
 Copyright © iTech Workshop Private Limited 2018 All rights reserved.
 -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<% response.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/welcome_css.css">
<link rel="stylesheet" href="css/UpdateUserCss.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/home_page_controller.js"></script>
<script type="text/javascript" src="js/updateUser.js"></script>

<title>Welcome</title>
</head>

<body>
	<div id="Color">
		<div id="sessionDiv">
			<%
				String sFirstName = (String) session.getAttribute("Session_Name");
				if (sFirstName == null) {
					response.sendRedirect("index.jsp");
				} else {
					out.print("hi, " + sFirstName + "  Welcome to Itech");
				}
			%>

		</div>
		<div>
			<form action="MainController1" method="post">
				<input type="hidden" name="action" value="sessionout" />
				<button type="submit" id='btnSessionOut'
					class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-log-out"></span> Log out
				</button>
			</form>
		</div>
		<div>
			<button type="button" id="addBtn" class="btn btn-default btn-sm">
				<span class="glyphicon glyphicon-user"></span> Add user
			</button>

			<button type="button" id="listBtn" class="btn btn-default btn-sm">
				<span class="glyphicon glyphicon-th-list"></span> List of all user
			</button>
		</div>
	</div>

	<div id="AddUser">
		<!-- add user page load -->
		<p><jsp:include page="addUser.jsp" /></p>
	</div>
	<div id="list"></div>
	<!-- loader div image -->
	<div class="ajax-loader">
		<img src="images/icons/ajax_loader_blue_32.gif" class="img-responsive" />
	</div>

	<div id="UserList"></div>
	<!-- display pagination form -->
	<!-- Update user modal form -->
	<div id="UpdateUser">
		<div class="container">
			<!-- Trigger the modal with a button -->
			<!--   <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Open Modal</button>-->

			<!-- Modal -->
			<div class="modal fade" id="myModal" role="dialog">
				<div class="modal-dialog">

					<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<!-- Modal header -->
							<button type="button" class="close" id="closeIcon"
								data-dismiss="modal">&times;</button>
							<center>
								<h4 class="modal-title">Update existing user profile</h4>
							</center>
						</div>
						<center>
							<div class="modal-body">
								<form class="form-add">
									<p id="UpdateErroMessage"></p>
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-pencil"></i></span> <input
											id="UpdateUserId" type="text" class="form-control "
											name="userid" placeholder="Enter User id" disabled>
									</div>
									</br>
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-user"></i></span> <input id="UpdateFname"
											type="text" class="form-control" name="fname"
											placeholder="Enter First Name">
									</div>
									</br>
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-user"></i></span> <input id="Updatelname"
											type="text" class="form-control" name="lname"
											placeholder="Enter Last Name">
									</div>
									</br>
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-briefcase"></i></span> <input
											id="UpdateRname" type="text" class="form-control"
											name="rname" placeholder="Enter Role Name">
									</div>
									</br>
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-envelope"></i></span> <input
											id="UpdateEmailId" type="text" class="form-control"
											name="emailid" placeholder="Enter Email id">
									</div>
									</br>
									<p id="UpdateEmailIdCheck"></p>
									</br>

									<center>
										<button type="button" id="UpdateBtn"
											class="btn btn-default btn-sm">
											<span class="glyphicon glyphicon-plus-sign"></span> Update
										</button>
										<button type="button" id="closeBtn" class="btn btn-default"
											data-dismiss="modal">Cancel</button>
									</center>
								</form>
							</div>
						</center>
					</div>

				</div>
			</div>
			<!--End Modal -->
		</div>
	</div>

</body>

</html>