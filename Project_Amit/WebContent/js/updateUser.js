$(document).ready(function(e) {

		/*
		 * validate all the date from client side before updating on database
		 */
		
		$("#closeBtn,#closeIcon").each(function(){
		    $(this).on('click',function(e){
		        
		    	$("#UpdateEmailIdCheck").hide().slideUp();
				$("#UpdateErroMessage").hide().slideUp();
				
		    	
		    });
		});
			/**
			 * check email id already exist or not
			 */
			
			$('#UpdateEmailId').on('keyup',function(e) {
						e.preventDefault();
						var EmailId = $('#UpdateEmailId').val();

						$.ajax({
							type : "POST",
							url : "MainController1",
							data : {
								"action":"checkemail","EmailId" : EmailId
							},
							success : function(data) {
								if (data == 'Email') {
									//alert("Email Id already exist");
									$("#UpdateEmailIdCheck").hide().slideDown(1000);
									$("#UpdateEmailIdCheck").text(" Email Id already exist");
									e.preventDefault();

								} else if ($('#UpdateEmailId').val()=="") {
									//alert("Email Id null");
									$("#UpdateEmailIdCheck").hide().slideUp();
									e.preventDefault();
								} else {
									//alert("Email Id avaliable");
									$("#UpdateEmailIdCheck").hide().slideDown(1000);
									$("#UpdateEmailIdCheck").text("Email Id avaliabe");
									//$("#UpdateErroMessage").hide().slideUp();
									e.preventDefault();

								}
							}
						});

					});
			
			/**
			 * End checking existing emaild id
			 */

			$('#UpdateBtn').on('click',function(e) {
				e.preventDefault();
						var userid = $("#UpdateUserId").val()
						var useremail = $("#UpdateEmailId").val();
						var userfname = $("#UpdateFname").val();
						var userlname = $("#Updatelname").val();
						var userrname = $("#UpdateRname").val();

						if (useremail.length == 0 || userfname.length == 0 || userlname.length == 0 || userrname.length == 0) {

							alert("Al field are mandatory");
							$("#UpdateErroMessage").hide().slideDown(1000);
							$("#UpdateErroMessage").text("All field are mandatory");
							//$("#UpdateEmailIdCheck").hide().slideUp();
							e.preventDefault();
						} else if (validateEmail(useremail)) {

							alert(" send data")
							
								$.ajax({
									type:"POST",
									url: "MainController1",
									data:{"action":"edituser","userid":userid,"useremail":useremail,"userfname":userfname,"userlname":userlname,"userrname":userrname},
									success: function(data){	 
										if(data=="True"){
											alert("update succesful");
										}
										else{
											$("#UpdateErroMessage").hide().slideDown(1000);
							        		$("#UpdateErroMessage").text("Something went wrong");
										}
									}
									
								})
							 
							e.preventDefault();

						} else {
							$("#UpdateErroMessage").hide().slideDown(1000);
							$("#UpdateErroMessage").text("Email id format not correct");
							e.preventDefault();
						}
						
						return false;

					});

			
});

/**
 * Validate user id
 */
function validateEmail(useremail) {
	var filter = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/;

	if (filter.test(useremail)) {
		return true;
	} else {
		return false;
	}

}