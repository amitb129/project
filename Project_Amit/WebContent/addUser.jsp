<!-- 
 File Header            : adduser.jsp
 Description            : Read all user details from add user form before send to the java class
 						  client side validation is happening.
 Author                 : Amit Banik 
 Created On             : 16-10-2018
 Maintenance History    : Amit Nov 2018 - Added jquery/ajax for read user details and send data to
 										  java class for add new user.
 										- Added some coding standard.
 Copyright © iTech Workshop Private Limited 2018 All rights reserved.
 -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/AddUser.js"></script>
<link rel="stylesheet" href="css/AddUserCss.css">
</head>

<div class="wrapper">
	<form class="form-add" action="Controller" method="post">
		<h2 class="form-add-heading">Add New user</h2>
		<p id="ErroMessage"></p>
		<p id="SuccesMessage"></p>
		<div class="input-group">
			<span class="input-group-addon"><i
				class="glyphicon glyphicon-pencil"></i></span> <input id="UserId"
				type="text" class="form-control" name="userid"
				placeholder="Enter User id">
		</div>
		</br>
		<p id="UserIdCheck"></p>
		<div class="input-group">
			<span class="input-group-addon"><i
				class="glyphicon glyphicon-user"></i></span> <input id="Fname" type="text"
				class="form-control" name="fname" placeholder="Enter First Name">
		</div>
		</br>
		<div class="input-group">
			<span class="input-group-addon"><i
				class="glyphicon glyphicon-user"></i></span> <input id="lname" type="text"
				class="form-control" name="lname" placeholder="Enter Last Name">
		</div>
		</br>
		<div class="input-group">
			<span class="input-group-addon"><i
				class="glyphicon glyphicon-briefcase"></i></span> <input id="Rname"
				type="text" class="form-control" name="rname"
				placeholder="Enter Role Name">
		</div>
		</br>
		<div class="input-group">
			<span class="input-group-addon"><i
				class="glyphicon glyphicon-envelope"></i></span> <input id="EmailId"
				type="text" class="form-control" name="emailid"
				placeholder="Enter Email id">
		</div>
		</br>
		<p id="EmailIdCheck"></p>
		<div class="input-group">
			<span class="input-group-addon"><i
				class="glyphicon glyphicon-lock"></i></span> <input id="Pwd"
				type="password" class="form-control" name="password"
				placeholder="Enter Password">
		</div>
		</br>
		<div class="input-group">
			<span class="input-group-addon"><i
				class="glyphicon glyphicon-lock"></i></span> <input id="RePwd"
				type="password" class="form-control" name="re-password"
				placeholder="Enter Re-Password">
		</div>
		</br>

		<center>
			<button type="button" id="AddBtn" class="btn btn-default btn-sm">
				<span class="glyphicon glyphicon-plus-sign"></span> Add User
			</button>
			<button type="reset" id="Reset" class="btn btn-default btn-sm">
				<span class="glyphicon glyphicon-minus-sign"></span> Reset
			</button>
		</center>
	</form>
</div>



