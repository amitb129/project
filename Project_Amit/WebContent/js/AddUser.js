$(document).ready(function(e) {
	/**
	 * check email id already exist or not
	 */
	$('#EmailId').on('keyup',function(e){
		e.preventDefault();
		var EmailId = $('#EmailId').val();
		
		$.ajax({
            type: "POST",
            url:"MainController1",
            data:{"action":"checkemail","EmailId":EmailId},
            success: function (data) {
               if(data=='Email'){
            	   $("#EmailIdCheck").hide().slideDown(1000);
            	   $("#EmailIdCheck").text(" Email Id already exist");  
               }
               else if ($('#EmailId').val()==""){
       			$("#EmailIdCheck").hide().slideUp();
       		}
               else{
            	   $("#EmailIdCheck").hide().slideDown(1000);
            	   $("#EmailIdCheck").text(" Email Id avaliabe" );

               }
            }
          });
		
	});
	
	/**
	 * check user id already exist or not
	 */
	
	$('#UserId').on('keyup',function(e){
		e.preventDefault();
		var UserId = $('#UserId').val();
		
		$.ajax({
            type: "POST",
            url:"MainController1",
            data:{"action":"checkuser","UserId":UserId},
            success: function (data) {
               if(data=='User'){
            	   $("#UserIdCheck").hide().slideDown(1000);
            	   $("#UserIdCheck").text("Id already exist" );
            	  
               }
               else if($('#UserId').val()==""){
       			$("#UserIdCheck").hide().slideUp();
               }
       			else{
            	   $("#UserIdCheck").hide().slideDown(1000);
            	   $("#UserIdCheck").text("Id avaliabe" );
   
               }
            }
          });
	});
	
	/**
	 * Insert new user
	 */
	$('#AddBtn').on('click',function(e) {
		
		var UserId = $('#UserId').val();
        var Fname = $('#Fname').val();
        var lname = $('#lname').val();
        var Rname = $('#Rname').val();
        var EmailId = $('#EmailId').val();
        var Pwd = $('#Pwd').val();
        // check any field is empty or not 
        if ( $('#UserId').val()=="" || $('#Fname').val()=="" || $('#lname').val()=="" || $('#Rname').val()=="" || $.trim(EmailId).lenght == 0  || $("#Pwd").val()=="") {
        	$("#SuccesMessage").hide().slideUp(1000);
        	$("#ErroMessage").hide().slideDown(1000);
     	    $("#ErroMessage").text("All fields are mandatory");
            e.preventDefault();
        }
        //check password matching
        else if($("#Pwd").val()!=$("#RePwd").val()){
        	$("#SuccesMessage").hide().slideUp(1000);
        	$("#ErroMessage").hide().slideDown(1000);
        	$("#ErroMessage").text("Password Mismatch");
        	e.preventDefault();
        }

else if (validateEmail(EmailId)) { //validate email address if true then ajax method will me call 
        	/**
        	 * send new user data through ajax call and receiving response also
        	 */
        	$.ajax({
                type: "POST",
                url:"MainController1",
                data:{"action":"adduser","UserId":UserId,"Fname":Fname,"lname":lname,"Rname":Rname,"EmailId":EmailId,"Pwd":Pwd},
                success: function (data) {
                   if(data=='True'){
                	  $("#ErroMessage").hide().slideUp(1000);
                	  $("#SuccesMessage").hide().slideDown(1000);
                	  $("#SuccesMessage").text("Successfully profile created");
                	  $(".form-add")[0].reset();
                	  
                   }else{
                	   $("#SuccesMessage").hide().slideUp(1000);
                	   $("#ErroMessage").hide().slideDown(1000);
                	   $("#ErroMessage").text("Something went Wrong");
                	   $(".form-add")[0].reset();

                	   
                   }
                }
              });
        } 
        else {
        	/**
        	 * if wrong email id put then it will show error message on form
        	 */
        	$("#ErroMessage").hide().slideDown(1500);
       	     $("#ErroMessage").text("Invalid Email Address");
            e.preventDefault();
        }
        return false;
    });

    
    return false;
    });

// validates email address through a regular expression.
function validateEmail(EmailId) {
	  var filter =  /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/;
    
   if (filter.test(EmailId)) {
        return true;
    }
    else {
        return false;
    }
}