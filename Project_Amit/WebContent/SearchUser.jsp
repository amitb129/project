<!-- 
 File Header            : SearchUser.jsp
 Description            : search user via user first name with exact,start and end button
 Author                 : Amit Banik 
 Created On             : 01-11-2018
 Maintenance History    : Amit Nov 2018 - Added jquery/ajax for client validation and send data to
 										  java class.
 										- Added some coding standard.
 Copyright © iTech Workshop Private Limited 2018 All rights reserved.
 -->

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/welcome_css.css">
<!-- <link rel="stylesheet" href="css/UpdateUserCss.css"> -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/userPagination.js"></script>
<script type="text/javascript" src="js/readUserData.js"></script>
<script type="text/javascript" src="js/SearchUser.js"></script>
</head>
<h1>User List</h1>
<div class="container">
	<p>
	<table class="table table-bordered">
		<%@ page import="java.util.*,com.task.*"%>
		<%
			String spageid = request.getParameter("page");
			String s_name = request.getParameter("s_Name");
			String s_type = request.getParameter("s_type");
			int pageid;
			if (spageid == null) {
				pageid = 1;
			} else {
				pageid = Integer.parseInt(spageid);
			}
			int total = 3;
			int limit = 0;
			if (pageid == 1) {
				limit = 0;
			} else {
				limit = (pageid - 1) * 3;
			}
			List<UserData> list;
			if (s_name == null || s_type == null) {
				s_name = "na";
				s_type = "na";
				list = UserDataDao.getRecords(total, limit, s_name, s_type);
			} else {
				list = UserDataDao.getRecords(total, limit, s_name, s_type);
			}
		%>
		<div id="UserListHead">
			<h1>
				Page No:<%=pageid%>
			</h1>
		</div>
		<div>
			<div class="input-group">
				<span class="input-group-addon"><i
					class="glyphicon glyphicon-search"></i></span> <input id="SearchValue"
					type="text" class="form-control" name="search"
					placeholder="Enter First Name to be search">
			</div>
			<div class="dropdown">
				<button id="searchBy" class="btn btn-primary dropdown-toggle"
					type="button" data-toggle="dropdown">
					Select your search type <span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a><button type="button" class="btn btn-info"
								id="btnExact">Exact</button></a></li>
					<li><a><button type="button" class="btn btn-info"
								id="btnStart">Start With</button></a></li>
					<li><a><button type="button" class="btn btn-info"
								id="btnEnd">End With</button></a></li>
				</ul>
			</div>
			<p id="SearchType" style="display: none;"></p>
		</div>

		<thead>
			<tr>
				<th>Id</th>
				<!--<th>Password</th>-->
				<th>Email</th>
				<th>Fist Name</th>
				<th>Last Name</th>
				<th>Role Name</th>
				<th>Edit User</th>
				<th>Delete User</th>
		</thead>
		<%
			for (UserData e : list) {
		%>
		<tbody>
			<tr>
				<td id="UserIdInfo"><%=e.getUserId()%></td>
				<!--<td id="UserPassword"><%=e.getPassword()%></td>-->
				<td id="UserEmail"><%=e.getEmail()%></td>
				<td id="UserFname"><%=e.getFname()%></td>
				<td id="UserLname"><%=e.getLname()%></td>
				<td id="UserRname"><%=e.getRname()%></td>
				<td><button id="btnEdit" type="button" class="btn btn-warning"
						data-toggle="modal" data-target="#myModal">Edit</button></td>
				<td><button id="btnDelete" type="button" class="btn btn-danger">Delete</button></td>
			</tr>
		</tbody>
		<%
			}
		%>

	</table>
	<%
		//UserDataDao pageno = new UserDataDao();//without static
		int NoOfPage = UserDataDao.NoofPages(s_name, s_type);
		if(NoOfPage <1){%>
			<center><h3>No records found</h3></center>
		<%}
	%>

	total no of page:
	<%=NoOfPage%>
	</br>
	<%
		for (int k = 1; k <= NoOfPage; k++) {
	%>
	<%-- <b><a class="Pageno" href="Welcome.jsp?page=<%= k %>"> <%= k %></a></b> <%-- without jquery --%>
	<b><a class="pageno" href="javascript:;" rel="Welcome.jsp"> <%=k%>
	</a></b>
	<%-- with jquery --%>
	<%-- <% out.print("<b><a class=Pageno href= javascript:; rel=Welcome.jsp>" + k + "</a></b>"); --%>
	<%-- jquery with out/println --%>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<%
		}
	%>
	</p>
</div>




