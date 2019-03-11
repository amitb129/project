$(document).ready(function(e) {
	/*
	 * read login value validate the client side data and send to server for validate the login details
	 */
	
	
    $('#btnValidate').on('click',function(e) {
    	
        var u_Email = $('#i_Email').val();
        var u_Pwd = $('#i_Password').val();
        
        if ($.trim(u_Email).lenght == 0  || $("#i_Password").val()=="") {
        	$("#message").hide().slideDown(1500);
     	   $("#message").text("All fields are mandatory");
            e.preventDefault();
        }
        else if (validateEmail(u_Email)) {
        	
        	$.ajax({
                type: "POST",
                url:"MainController1",
                data:{"action":"login","i_Email":u_Email,"i_Password":u_Pwd},
                success: function (data) {
                	
                   if(data=='True'){
                	 
                     $(location).attr('href','Welcome.jsp');
                   }else{
                	   $("#message").hide().slideDown(1500);
                	   $("#message").text("Login validation failed");
                       //$(location).attr('href','index.jsp');
                	   
                   }
                }
              });
        	
        } 
        else {
        	$("#message").hide().slideDown(1500);
       	     $("#message").text("Invalid Email Address");
            //alert('Invalid Email Address');
            e.preventDefault();
        }
        return false;
    });

    
    return false;
    });

// Function that validates email address through a regular expression.
function validateEmail(u_Email) {
	  var filter =  /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/;
    
   if (filter.test(u_Email)) {
        return true;
    }
    else {
        return false;
    }
}