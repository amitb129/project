$(document).ready(function(e) {

	/*
	 * code for pagination
	 */
	
	 $('.Pageno').click(function(e){
		 //alert("pagination");
		  e.preventDefault();
		    //var targetUrl = $(this).attr('rel'); 							// read the url link <a> tag
		    var page = $(this).text().trim();    							// read the page no on hit page link
		   
		    $.ajax({
		        url: "updateUser.jsp",										//Target link where our page is going
		        type: "POST",												//method using for sending data
		        beforeSend: function(){										
		            $('.ajax-loader').css("visibility", "visible");			//loader start
		        },
		        data:{"page": page},										// data(page no) we are passing to updateUser.jsp
		        success:function(response){
		        	//alert(response);
		            $("#UserList").html(response);							//print the response 
		            $("#searchBy").dropdown();
		            return false;
		        },
		        complete: function(){
		       	$('.ajax-loader').css("visibility", "hidden");				//loader stop
		         },
		        error:function (){
		            alert("testing error");									// if something error happen alert message will be trigger
		        }
		    });
		});
	
	
	/*
	 * Code for delete user details
	 */
	 
	$('.table tbody').on('click','#btnDelete',function(){
		//alert("buttonclick2");
	      
        var userinfo = $(this).closest("tr")   								// Finds the closest row <tr> 
                       .find("#UserIdInfo")     							// Gets a descendent with class="UserIdInfo"
                       .text().trim();         								// Retrieves the text with remove extra whitespaces

         $.ajax({

            url:'MainController1',												//target link servlet
            type:'post',
            data:{"action":"deleteuser","userinfo": userinfo},									// send userid data to servlet for delete row from table
            success: function(response){
            	if(response =='True'){
            		//alert("delete successfully")							//if its delete successful then it will print success message
            		$("#list").html("<h3>delete successfully</h3>"); 
            		$("#UserList").load("updateUser.jsp");
            	}
            	else{
            		alert("unsuccessfully")
            		$("#list").html("<h3>delete unsuccessfully</h3>");		//if its delete unsuccessful then it will print unsuccessful message
            		//$("#deleteMsg").html("Deletion Failed.");
            	}
            }
      }); 

   });
	
	return false;
});