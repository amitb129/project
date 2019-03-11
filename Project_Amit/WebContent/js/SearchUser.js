$(document).ready(function(e){
	
	/*
	 * search user details with first name using three buton exact,start and end with
	 */
	
	var SearchType;
	$("#btnExact").on("click",function(e){
		
		var SearchValue = $("#SearchValue").val();
		alert("Hi i am exact button");
		alert("search value=" + SearchValue);
		
		$.ajax({
			
			url: "SearchUser.jsp",
			type: "POST",
			data:{"s_Name":SearchValue,"s_type":"exact"},
			success:function(response){
				$("#UserList").html(response);		//print the response 
				$('#SearchValue').val(SearchValue);		//print the search value to search text box
				SearchType="exact";
				$('#SearchType').text(SearchType);	//store the	the searchtype value in <p> tag with hide css property
				$("#searchBy").dropdown();			//load the dropdown by manually
				return false;
			},
			error:function(){
				alert("something went wrong in exact")
			}
		});
		
		
	});
	
	$("#btnStart").on("click", function(e){
		
		var SearchValue = $("#SearchValue").val();
		alert("Hi i am start button");
		alert("search value=" + SearchValue);
		
		$.ajax({
			
			url: "SearchUser.jsp",
			type: "POST",
			data: {"s_Name":SearchValue,"s_type":"start"},
			success: function(response){
				$("#UserList").html(response);
				$('#SearchValue').val(SearchValue);		//print the search value to search text box
				SearchType="start";
				$('#SearchType').text(SearchType);	//store the	the searchtype value in <p> tag with hide css property
				$("#searchBy").dropdown();			//load the dropdown by manually
				return false;
			},
			error: function(e){
				alert("something went wrong in start")
			}
			
		})
		
	});
	
	$("#btnEnd").on("click",function(e){
		
		var SearchValue = $("#SearchValue").val();
		alert("Hi i am end button");
		alert("search value=" + SearchValue);
		
		$.ajax({
			
			url: "SearchUser.jsp",
			type: "POST",
			data:{"s_Name":SearchValue,"s_type":"end"},
			success: function(response){
				$("#UserList").html(response);							//print the response 
				$('#SearchValue').val(SearchValue);		//print the search value to search text box
				SearchType="end";
				$('#SearchType').text(SearchType);	//store the	the searchtype value in <p> tag with hide css property
				$("#searchBy").dropdown();			//load the dropdown by manually
				return false;
			},
			error: function(){
				
				alert("something went wrong in end")
			}
		});
		
	})
	
	
	$('.pageno').click(function(e){
		 //alert("pagination");
		  e.preventDefault();
		  var SearchValue = $("#SearchValue").val();
		  alert(SearchValue);
		  var SearchType = $('#SearchType').text();	
		  alert(SearchType);
		    //var targetUrl = $(this).attr('rel'); 							// read the url link <a> tag
		    var page = $(this).text().trim();    							// read the page no on hit page link
		   
		    $.ajax({
		        url: "SearchUser.jsp",										//Target link where our page is going
		        type: "POST",												//method using for sending data
		        beforeSend: function(){										
		            $('.ajax-loader').css("visibility", "visible");			//loader start
		        },
		        data:{"page": page,"s_Name":SearchValue,"s_type":SearchType},										// data(page no) we are passing to updateUser.jsp
		        success:function(response){
		            $("#UserList").html(response);							//print the response 
		            $('#SearchValue').val(SearchValue);		//print the search value to search text box
		            $('#SearchType').text(SearchType);	//store the	the searchtype value in <p> tag with hide css property
					$("#searchBy").dropdown();			//load the dropdown by manually
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
	
});