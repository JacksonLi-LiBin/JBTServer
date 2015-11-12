/**
 * JacksonLi
 */
var adintermission;
var adsMap = new Map();
window.queryString = new QeryStringParser();
var uploadImageJudge=queryString.getParam("uploadImage");
var setImageJudge=queryString.getParam("setImage");
/**
 * validate your input
 */
function uploadFile() {
	if ($.trim($("#show_time").val()).length == 0
			|| $.trim($("#image_file").val()).length == 0) {
		alert("please input all informations!");
		return false;
	} else {
		if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test($("#image_file").val())) {
			alert("Image type should be .gif,jpeg,jpg or png");
			$("#image_file").val("");
			return false;
		} else {
			return true;
		}
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
						if(uploadImageJudge=="true"){
							alert("Upload Image Successfully");
//							var currentPageUpload=changeURLPar(window.location.href,"uploadImage","false");
//							window.location=currentPageUpload;
							parent.location.hash = "upload";
						}
						
						if(setImageJudge=="true"){
							alert("Set Image Successfully");
//							var currentPageEdit=changeURLPar(window.location.href,"setImage","false");
//							window.location=currentPageEdit;
							parent.location.hash = "edit";
						}
						
						//show all images
						var dataAd = "";
						$.ajax({
							url : urlStr+ "GetAdintermissionServlet?type=2",
							type : "GET",
							timeout : 20000,
							cache : false,
							dataType : "text",
							success : function(data) {
								dataAd = data;
							},
							error : function() {
								alert("error");
								$(".adsList table:last-child tbody").html("");
							},
							complete : function(status) {
								if (status.readyState == 4&& status.status == 200) {
									if (dataAd == "nodata") {
										alert("no data has been found");
									} else {
										$(".adsList table:last-child tbody").html("");
										adsJSON = eval("(" + dataAd+ ")");
										$(".adsList table:last-child tbody").append('<tr><td width="26%" align="center">Type</td><td width="26%" align="center">Path</td><td width="31%" align="center">Usable</td><td width="17%" align="center">Edit</td></tr>');
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
												usableStr = "Usable";
											} else {
												usableStr = "Unusable";
											}
											$(".adsList table:last-child tbody").append(
												'<tr style="font-weight: normal"><td width="26%" align="center">'
												+ typeStr
												+ '</td><td width="40%" align="center" style="word-break: break-all;word-wrap: break-word;">'
												+ adsJSON[i].path
												+ '</td><td width="17%" align="center">'
												+ usableStr
												+ '</td><td width="17%" align="center"><input type="button" id="'
												+ adsJSON[i].adId
												+ '" value="Edit" onClick="editImage(this);" /></td></tr>');
										}
									}
								}
							}
						});
						
						//change image type
						$("#adType").change(function(){
							if($("#adType").val()=="Banner"){
								$(".imgPiexl").html("(Dimensions:276x74)");
							}else{
								$(".imgPiexl").html("(Dimensions:500x433)");
							}
						});
						
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
														$(".adsList table:last-child tbody").html("");
													},
													complete : function(status) {
														if (status.readyState == 4&& status.status == 200) {
															if (adData == "nodata") {
																alert("no data has been found");
															} else {
																$(".adsList table:last-child tbody").html("");
																adsJSON = eval("(" + adData+ ")");
																$(".adsList table:last-child tbody").append('<tr><td width="26%" align="center">Type</td><td width="26%" align="center">Path</td><td width="31%" align="center">Usable</td><td width="17%" align="center">Edit</td></tr>');
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
																		usableStr = "Usable";
																	} else {
																		usableStr = "Unusable";
																	}
																	$(".adsList table:last-child tbody").append(
																		'<tr style="font-weight: normal"><td width="26%" align="center">'
																		+ typeStr
																		+ '</td><td width="40%" align="center" style="word-break: break-all;word-wrap: break-word;">'
																		+ adsJSON[i].path
																		+ '</td><td width="17%" align="center">'
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
								$(".adsList table:last-child tbody").html("");
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

/**
 * edit click
 * @param evt
 */
function editImage(evt) {
	var passAd = adsMap.get(evt.id);
	window.localStorage.setItem("adId", passAd.adId);
	window.localStorage.setItem("path", passAd.path);
	window.localStorage.setItem("showTime", passAd.showTime);
	window.localStorage.setItem("type", passAd.type);
	window.localStorage.setItem("usable", passAd.usable);
	window.location = "index_adDetail.html";
}