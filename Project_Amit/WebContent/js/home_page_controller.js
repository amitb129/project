$(document).ready(function(e) {
	//location.reload();
	/*
	 * load add user page and list user page 
	 */
	
	$("#addBtn").on('click',function(e){
		
		$("#AddUser").hide().slideDown();
		$("#UserList").hide().slideUp();
		$("#list").hide().slideUp();
		
	});
	
	$("#listBtn").on('click',function(e){
		$("#UserList").hide().slideDown()
		$("#AddUser").hide().slideUp();
		$("#list").hide().slideDown();
		$("#UserList").load("updateUser.jsp");
		return false;
	})
});