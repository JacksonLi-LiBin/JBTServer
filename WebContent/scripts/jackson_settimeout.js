/**
 * JacksonLi
 */
var updateOutTime="";
var adminToken;
$(function(){
	adminToken=window.localStorage.getItem("adminToken");
	if(adminToken==null){
		window.location="index_login.html";
	}else{
		var getData="";
		$.ajax({
			url:urlStr+"rest/AdminLogin/adminTokenUsable?adminToken="+adminToken,
			type:'GET',
			cache:false,
			dataType: "text",
			timeout:10000,
			success:function(data){
				getData=data;
			},
			error:function(){
				
			},
			complete:function(status){
				if(status.readyState == 4 && status.status == 200){
					if(getData=="true"){
						//get time and data from database
						var getTimeDate="";
						$.ajax({
							url:urlStr+"rest/Admin/"+adminToken+"/getTimeAndDate",
							type:'GET',
							cache:false,
							dataType: "text",
							timeout:10000,
							success:function(data){
								getTimeDate=data;
							},
							error:function(){
								
							},
							complete:function(status){
								if(status.readyState == 4 && status.status == 200){
									if(getTimeDate!="null"){
										var getTimeDateJson=eval("("+getTimeDate+")");
										var getTimeOut=getTimeDateJson.timeOut;
										var getCPWDTimeOut=getTimeDateJson.cpwdTimeOut;
										var getFirstTimeGetData=getTimeDateJson.firstTimeGetData;
										var getSecondTimeGetData=getTimeDateJson.secondTimeGetData;
										
										$("#out_time").attr("placeholder",getTimeOut.substr(0,getTimeOut.length-3));
										$("#cpwd_out_time").attr("placeholder",getCPWDTimeOut.substr(0,getCPWDTimeOut.length-3));
										
										var firstHH=getFirstTimeGetData.split(":")[0];
										var firstMM=getFirstTimeGetData.split(":")[1];
										var firstSS=getFirstTimeGetData.split(":")[2];
										
										//set first get data time
										var firstH="";
										var firstM="";
										var firstS="";
										for(var i=0;i<24;i++){
											if(firstHH==i){
												firstH=firstH+'<option selected>'+i+'</option>';
											}else{
												firstH=firstH+'<option>'+i+'</option>';
											}
										}
										for(var i=0;i<60;i++){
											if(firstMM==i){
												firstM=firstM+'<option selected>'+i+'</option>';
											}else{
												firstM=firstM+'<option>'+i+'</option>';
											}
											if(firstSS==i){
												firstS=firstS+'<option selected>'+i+'</option>';
											}else{
												firstS=firstS+'<option>'+i+'</option>';
											}
										}
										$("#firstGetHour").html(firstH);
										$("#firstGetMinute").html(firstM);
										$("#firstGetSecond").html(firstS);
										
										var secondHH=getSecondTimeGetData.split(":")[0];
										var secondMM=getSecondTimeGetData.split(":")[1];
										var secondSS=getSecondTimeGetData.split(":")[2];
										
										//set second get data time
										var secondH="";
										var secondM="";
										var secondS="";
										for(var i=0;i<24;i++){
											if(secondHH==i){
												secondH=secondH+'<option selected>'+i+'</option>';
											}else{
												secondH=secondH+'<option>'+i+'</option>';
											}
										}
										for(var i=0;i<60;i++){
											if(secondMM==i){
												secondM=secondM+'<option selected>'+i+'</option>';
											}else{
												secondM=secondM+'<option>'+i+'</option>';
											}
											if(secondSS==i){
												secondS=secondS+'<option selected>'+i+'</option>';
											}else{
												secondS=secondS+'<option>'+i+'</option>';
											}
										}
										$("#secondGetHour").html(secondH);
										$("#secondGetMinute").html(secondM);
										$("#secondGetSecond").html(secondS);
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
	}
	
	//0 set Operate Time Out
	//1 set change password time out
	$("#setOutTime").click(function(){
		if($.trim($("#out_time").val()).length>0){
			var getData="";
			$.ajax({
				url:urlStr+"rest/AdminLogin/adminTokenUsable?adminToken="+adminToken,
				type:'GET',
				cache:false,
				dataType: "text",
				timeout:10000,
				success:function(data){
					getData=data;
				},
				error:function(){
					
				},
				complete:function(status){
					if(status.readyState == 4 && status.status == 200){
						if(getData=="true"){
							if(parseInt($.trim($("#out_time").val()))>10){
								$.ajax({
									url:urlStr+"UpdateOutTimeServlet?outTime="+$.trim($("#out_time").val())+"&type=0",
									type:"GET",
									timeout:20000,
									cache:false,
									dataType:"text",
									success:function(data){
										updateOutTime=data;
									},
									error:function(){
										
									},
									complete:function(status){
										if(status.readyState == 4 && status.status == 200){
											if(updateOutTime=="true"){
												alert("Set Time Out Successfully");
												window.location="index_settimeout.html";
											}
										}
									}
								});
							}else{
								alert("Please Input A Number Larger Than 10");
							}
						}else{
							alert("Operate Time Out");
							window.location="index_login.html";
						}
					}
				}
			});
		}else{
			alert("please input the user timeout!");
		}
	});
	
	$("#setChangePasswordOutTime").click(function(){
		if($.trim($("#cpwd_out_time").val()).length>0){
			var getData="";
			$.ajax({
				url:urlStr+"rest/AdminLogin/adminTokenUsable?adminToken="+adminToken,
				type:'GET',
				cache:false,
				dataType: "text",
				timeout:10000,
				success:function(data){
					getData=data;
				},
				error:function(){
					
				},
				complete:function(status){
					if(status.readyState == 4 && status.status == 200){
						if(getData=="true"){
							$.ajax({
								url:urlStr+"UpdateOutTimeServlet?outTime="+$.trim($("#cpwd_out_time").val())+"&type=1",
								type:"GET",
								timeout:20000,
								cache:false,
								dataType:"text",
								success:function(data){
									updateOutTime=data;
								},
								error:function(){
									
								},
								complete:function(status){
									if(status.readyState == 4 && status.status == 200){
										if(updateOutTime=="true"){
											alert("Set Change password Time Out Successfully");
											window.location="index_settimeout.html";
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
		}else{
			alert("please input the change password timeout!");
		}
	});
	
	$("#setFirstGetTime").click(function(){
		var firstHour=$("#firstGetHour").val();
		var firstMinute=$("#firstGetMinute").val();
		var firstSecond=$("#firstGetSecond").val();
		
		var getData="";
		$.ajax({
			url:urlStr+"rest/AdminLogin/adminTokenUsable?adminToken="+adminToken,
			type:'GET',
			cache:false,
			dataType: "text",
			timeout:10000,
			success:function(data){
				getData=data;
			},
			error:function(){
				
			},
			complete:function(status){
				if(status.readyState == 4 && status.status == 200){
					if(getData=="true"){
						$.ajax({
							url:urlStr+"UpdateOutTimeServlet?setGetTime=true&firstHour="+firstHour+"&firstMinute="+firstMinute+"&firstSecond="+firstSecond+"&type=2",
							type:"GET",
							timeout:20000,
							cache:false,
							dataType:"text",
							success:function(data){
								updateOutTime=data;
							},
							error:function(){
								alert("error");
							},
							complete:function(status){
								if(status.readyState == 4 && status.status == 200){
									if(updateOutTime=="true"){
										alert("Set First Time Get Data From John Bryce Successfully");
										window.location="index_settimeout.html";
									}else{
										alert("Set First Time Get Data From John Bryce Failed. Because The Progress Is Running Or The Time You Set Is Larger Than The Second Time");
										window.location="index_settimeout.html";
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
	
	$("#setSecondGetTime").click(function(){
		var secondHour=$("#secondGetHour").val();
		var secondMinute=$("#secondGetMinute").val();
		var secondSecond=$("#secondGetSecond").val();
		
		var getData="";
		$.ajax({
			url:urlStr+"rest/AdminLogin/adminTokenUsable?adminToken="+adminToken,
			type:'GET',
			cache:false,
			dataType: "text",
			timeout:10000,
			success:function(data){
				getData=data;
			},
			error:function(){
				
			},
			complete:function(status){
				if(status.readyState == 4 && status.status == 200){
					if(getData=="true"){
						$.ajax({
							url:urlStr+"UpdateOutTimeServlet?setGetTime=true&secondHour="+secondHour+"&secondMinute="+secondMinute+"&secondSecond="+secondSecond+"&type=3",
							type:"GET",
							timeout:20000,
							cache:false,
							dataType:"text",
							success:function(data){
								updateOutTime=data;
							},
							error:function(){
								alert("error");
							},
							complete:function(status){
								if(status.readyState == 4 && status.status == 200){
									if(updateOutTime=="true"){
										alert("Set Second Time Get Data From John Bryce Successfully");
										window.location="index_settimeout.html";
									}else{
										alert("Set Second Time Get Data From John Bryce Failed. Because The Progress Is Running Or The Time You Set Is Smailler Than The First Time");
										window.location="index_settimeout.html";
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