/**
 * JacksonLi
 */
var adintermission;
var adsMap = new Map();
var adminToken;
$(function() {
	adminToken=window.localStorage.getItem("adminToken");
	if(adminToken==null){
		window.location="index_login.html";
	}else{
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
						// get ads info by type
						$("#type").change(function() {
							if ($("#type").val() != -1) {
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
												var adData = "";
												$.ajax({
													url : urlStr+ "GetAdintermissionServlet?type="+ $("#type").val(),
													type : "GET",
													timeout : 20000,
													cache : false,
													dataType : "text",
													success : function(data) {
														adData = data;
													},
													error : function() {
														alert("error");
														$(".formcontent table:last-child tbody").html("");
													},
													complete : function(status) {
														if (status.readyState == 4&& status.status == 200) {
															if (adData == "nodata") {
																alert("no data has been found");
															} else {
																$(".formcontent table:last-child tbody").html("");
																adsJSON = eval("(" + adData+ ")");
																$(".formcontent table:last-child tbody").append('<tr><td width="26%" align="center">Type</td><td width="26%" align="center">Path</td><td width="31%" align="center">Usable</td><td width="17%" align="center">Edit</td></tr>');
																var typeStr = "";
																var usableStr = "";
																for (var i = 0; i < adsJSON.length; i++) {
																	adintermission = new AdIntermission();
																	adintermission.adId = adsJSON[i].adId;
																	adintermission.path = adsJSON[i].path;
																	adintermission.showTime = adsJSON[i].showTime;
																	adintermission.type = adsJSON[i].type;
																	adintermission.usable = adsJSON[i].usable;

																	adsMap.put(adsJSON[i].adId,adintermission);

																	if (adsJSON[i].type == "0") {
																		typeStr = "Intermission";
																	} else {
																		typeStr = "Banner";
																	}
																	if (adsJSON[i].usable == "0") {
																		usableStr = "usable";
																	} else {
																		usableStr = "unusable";
																	}
																	$(".formcontent table:last-child tbody").append(
																		'<tr style="font-weight: normal"><td width="26%" align="center">'
																		+ typeStr
																		+ '</td><td width="26%" align="center">'
																		+ adsJSON[i].path
																		+ '</td><td width="31%" align="center">'
																		+ usableStr
																		+ '</td><td width="17%" align="center"><input type="button" id="'
																		+ adsJSON[i].adId
																		+ '" value="edit" onClick="editImage(this);" /></td></tr>');
																}
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
							} else {
								$(".formcontent table:last-child tbody").html("");
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
