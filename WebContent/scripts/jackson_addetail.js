/**
 * JacksonLi
 */
var adId = window.localStorage.getItem("adId");
var path = window.localStorage.getItem("path");
var showTime = window.localStorage.getItem("showTime");
var type = window.localStorage.getItem("type");
var usable = window.localStorage.getItem("usable");
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
						$(".formcontent table:last-child tbody").html("");
						// load all ads
						//type 0 intermission 1 banner
						//usable 0 usable 1 unusable
						if (type == "0") {
							if (usable == "0") {
								$(".formcontent table:last-child tbody").append('<tr><td rowspan="5"><img src="'
										+ path
										+ '" /></td></tr><tr><td>AdId: '
										+ adId
										+ '</td></tr><tr><td>ShowTime: '
										+ (parseInt(showTime)/1000)
										+ ' S</td></tr><tr><td>Type: <select id="type"><option value="0" selected>Intermission</option>'
										+ '<option value="1">Banner</option></select></td></tr><tr><td>Usable: <select id="usable"><option value="0" selected>Usable</option>'
										+ '<option value="1">Unusable</option></select></td></tr>');
							} else {
								$(".formcontent table:last-child tbody").append('<tr><td rowspan="5"><img src="'
										+ path
										+ '" /></td></tr><tr><td>AdId: '
										+ adId
										+ '</td></tr><tr><td>ShowTime: '
										+ (parseInt(showTime)/1000)
										+ ' S</td></tr><tr><td>Type: <select id="type"><option value="0" selected>Intermission</option>'
										+ '<option value="1">Banner</option></select></td></tr><tr><td>Usable: <select id="usable"><option value="0">Usable</option>'
										+ '<option value="1" selected>Unusable</option></select></td></tr>');
							}
						} else {
							if (usable == "0") {
								$(".formcontent table:last-child tbody").append('<tr><td rowspan="5"><img src="'
										+ path
										+ '" /></td></tr><tr><td>AdId: '
										+ adId
										+ '</td></tr><tr><td>ShowTime: '
										+ (parseInt(showTime)/1000)
										+ ' S</td></tr><tr><td>Type: <select id="type"><option value="0">Intermission</option>'
										+ '<option value="1" selected>Banner</option></select></td></tr><tr><td>Usable: <select id="usable"><option value="0" selected>Usable</option>'
										+ '<option value="1">Unusable</option></select></td></tr>');
							} else {
								$(".formcontent table:last-child tbody").append('<tr><td rowspan="5"><img src="'
										+ path
										+ '" /></td></tr><tr><td>AdId: '
										+ adId
										+ '</td></tr><tr><td>ShowTime: '
										+ (parseInt(showTime)/1000)
										+ ' S</td></tr><tr><td>Type: <select id="type"><option value="0">Intermission</option>'
										+ '<option value="1" selected>Banner</option></select></td></tr><tr><td>Usable: <select id="usable"><option value="0">Usable</option>'
										+ '<option value="1" selected>Unusable</option></select></td></tr>');
							}
						}
					}else{
						alert("Operate Time Out");
						window.location="index_login.html";
					}
				}
			}
		});
	}
	
	/**
	 * edit ads
	 */
	$("#editImage").click(function() {
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
						//no change
						if ($("#type").val() == type && $("#usable").val() == usable) {
							window.location = urlStr + "index_uploadimage.html";
						} else {
							var ad = new AdIntermission(adId, path, showTime,$("#type").val(), $("#usable").val());
							var getData = "";
							$.ajax({url : urlStr + "EditAdServlet?ad="+ JSON.stringify(ad),
								type : "GET",
								timeout : 20000,
								cache : false,
								dataType : "text",
								success : function(data) {
									getData = data;
								},
								error : function() {

								},
								complete : function(status) {
									if (status.readyState == 4&& status.status == 200) {
										if (getData == "true") {
											window.location = urlStr+ "index_uploadimage.html?setImage=true";
										} else {

										}
									}
								}	
							});
						}
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