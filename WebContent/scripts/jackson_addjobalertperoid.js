/**
 * JacksonLi
 */
function addperiod(){
	if($.trim($("#alert_time").val()).length==0||$.trim($("#alert_millisecond").val()).length==0){
		alert("Input the details!");
		return false;
	}
}

var adminToken;
$(function(){
	adminToken=window.localStorage.getItem("adminToken");
	if(adminToken==null){
		window.location="index_login.html";
	}else{
		$("#adminToken").val(adminToken);
		var dataGet="";
		$.ajax({
			url:urlStr+"rest/AdminLogin/adminTokenUsable?adminToken="+adminToken,
			type:'GET',
			cache:false,
			dataType: "text",
			timeout:10000,
			success:function(data){
				dataGet=data;
			},
			error:function(){
				
			},
			complete:function(status){
				if(status.readyState == 4 && status.status == 200){
					if(dataGet=="true"){
						
					}else{
						alert("Operate Time Out");
						window.location="index_login.html";
					}
				}
			}
		});
	}
	
	$(".headercontent .logoutbtn").click(function(){
		var dataGet="";
		$.ajax({
			url:urlStr+"rest/AdminLogin/adminTokenUsable?adminToken="+adminToken,
			type:'GET',
			cache:false,
			dataType: "text",
			timeout:10000,
			success:function(data){
				dataGet=data;
			},
			error:function(){
				
			},
			complete:function(status){
				if(status.readyState == 4 && status.status == 200){
					if(dataGet=="true"){
						var returnData="";
						var userName=window.localStorage.getItem("adminName");
						$.ajax({
							url:urlStr+"rest/AdminLogin/logout?adminToken="+adminToken+"&adminName="+userName,
							type:'GET',
							cache:false,
							dataType: "text",
							timeout:10000,
							success:function(data){
								returnData=data;
							},
							error:function(){
								
							},
							complete:function(status){
								if(status.readyState == 4 && status.status == 200){
									if(returnData=="true"){
										alert("Logout Success");
										window.location="index_login.html";
									}else{
										
									}
								}
							}
						});
					}else{
						alert("Operate Time Out");
						window.location="index_login.html";
					}
				}
			}
		});
	});
});