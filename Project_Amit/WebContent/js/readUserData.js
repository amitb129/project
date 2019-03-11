$(document).ready(function(e){
	
	/**
	 * Read data from list of user and display in modal from for update user details
	 */
	
	$('.table tbody').on('click', '#btnEdit', function(e) {
		//alert("Button edit");
		e.preventDefault();
		var userinfo = $(this).closest("tr") 		// Finds the closest row <tr> 
		.find("#UserIdInfo") 						// Gets a descendent with class="UserIdInfo"
		.text().trim(); 							// Retrieves the text with remove extra whitespaces
		//alert(userinfo);
		$('#UpdateUserId').val(userinfo); 			//print userid value in modal form userid text box

		var useremail = $(this).closest("tr") 		// Finds the closest row <tr> 
		.find("#UserEmail") 						// Gets a descendent with class="UserEmail"
		.text().trim(); 							// Retrieves the text with remove extra whitespaces
		//alert(useremail);
		$('#UpdateEmailId').val(useremail); 		//print emailid value in modal form userid text box

		var userfname = $(this).closest("tr") 		// Finds the closest row <tr> 
		.find("#UserFname") 						// Gets a descendent with class="UserFname"
		.text().trim(); 							// Retrieves the text with remove extra whitespaces
		// alert(userfname);
		$('#UpdateFname').val(userfname); 			//print first name value in modal form userid text box

		var userlname = $(this).closest("tr") 		// Finds the closest row <tr> 
		.find("#UserLname") 						// Gets a descendent with class="UserLname"
		.text().trim(); 							// Retrieves the text with remove extra whitespaces
		// alert(userlname);
		$('#Updatelname').val(userlname); 			//print Last Name in modal form userid text box

		var userrname = $(this).closest("tr") 		// Finds the closest row <tr> 
		.find("#UserRname") 						// Gets a descendent with class="UserRname"
		.text().trim(); 							// Retrieves the text with remove extra whitespaces
		//alert(userrname);
		$('#UpdateRname').val(userrname); 			//print role name value in modal form userid text box
		
		/**
		 * End reading data
		 */
		
		

	});
	
});