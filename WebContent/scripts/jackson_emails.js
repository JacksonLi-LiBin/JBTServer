/**
 * JacksonLi
 */
var strUrl=urlStr;
//0 add a new email  1 edit the specified email
var operate=0;
var emailEdit="";
//current page number
var currentRecommendEmailPage=1;
var currentUserEmailPage=1;
//judge weather recommend email is in the search model
var isRecommendEmailSearchModel=false;
//judge weather user email is in the search model
var isUserEmailSearchModel=false;
//last edit user email
var lastEditUserEmail="";

var adminToken;
$(function(){
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
						//get recommend email
						$.ajax({
							 url: strUrl+"EmailServlet?type=0&currentRecommendEmailPage="+currentRecommendEmailPage,
							 type:'GET',
							 cache:false,
							 dataType: "text",
							 timeout:10000
							 }).done(function(data){
							   var getJson=eval("(" + data+ ")");
							   var sumPageCounts=getJson.d.sumPages;
							   var emailJSON=getJson.d.getEmail;
							   var str="";
							   for(var i=0; i<emailJSON.length; i++){
								   str=str+'<tr><td id="email'+i+'" align="center" colspan="2">'+emailJSON[i].email+'</td><td align="right"><input type="button" value="Delete" class="upload" id="delete'+i+'" onclick="deleteEmail(this);"/><input type="button" value="Edit" class="upload" id="modify'+i+'" onclick="modifyEmail(this);"/></td></tr>';
							   }
							   str=str+'<tr><td colspan="3" align="center"><input type="button" value="Previous" class="upload" onclick="previousRecommendEmail()"/>';
							   if(sumPageCounts != 0){
								   if(sumPageCounts<10){
									   for(var j=0;j<sumPageCounts;j++){
										   if(currentRecommendEmailPage==(j+1)){
											   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailPage'+j+'" onclick="jumpToPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }else{
											   str=str+'&nbsp;<span class="countPage" id="emailPage'+j+'" onclick="jumpToPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }
									   }
								   }else{
									   for(var j=0;j<10;j++){
										   if(currentRecommendEmailPage==(j+1)){
											   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailPage'+j+'" onclick="jumpToPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }else{
											   str=str+'&nbsp;<span class="countPage" id="emailPage'+j+'" onclick="jumpToPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }
									   }
								   }
							   }
							   str=str+'<input type="button" value="Next" class="upload" onclick="nextRecommendEmail()"/></td></tr>';
							   $(".emailDetail").html("");
							   $(".emailDetail").append(str).trigger("create");
							 }).fail(function(data){});
							 
						//get user email
						$.ajax({
							 url: strUrl+"EmailServlet?type=4&currentUserEmailPage="+currentUserEmailPage,
							 type:'GET',
							 cache:false,
							 dataType: "text",
							 timeout:10000
							 }).done(function(data){
							   var getJson=eval("(" + data+ ")");
							   var sumUserPages=getJson.d.sumUserPages;
							   var userJSON=getJson.d.getUserAndCourses;
							   var str="";
							   for(var i=0; i<userJSON.length; i++){
								   str=str+'<tr><td align="center" id="user'+userJSON[i].userId+'">'+userJSON[i].userId+'</td><td align="center">'+userJSON[i].firstName+" "+userJSON[i].lastName+'</td><td align="center"><input type="text" id="email'+userJSON[i].userId+'" value="'+userJSON[i].email+'" disabled maxlength="50"/></td><td align="center">';
								   var maslulNosStr="";
								   var maslulNos=userJSON[i].maslulNos;
								   for(var j=0; j<maslulNos.length; j++){
									   maslulNosStr=maslulNosStr+maslulNos[j]+" ";
								   }
								   str=str+maslulNosStr+'</td><td align="center"><input type="button" id="edit'+userJSON[i].userId+'" value="Edit" onclick="editUserEmail(this)"/></td></tr>';
								}
							   str=str+'<tr><td colspan="5" align="center"><input type="button" value="Previous" class="upload" onclick="previousUserEmail()"/>';
							   if(sumUserPages != 0){
								   if(sumUserPages<10){
									   for(var j=0;j<sumUserPages;j++){
										   if(currentUserEmailPage==(j+1)){
											   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }else{
											   str=str+'&nbsp;<span class="countPage" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }
									   }
								   }else{
									   for(var j=0;j<10;j++){
										   if(currentUserEmailPage==(j+1)){
											   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }else{
											   str=str+'&nbsp;<span class="countPage" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }
									   }
								   }
							   }
							   str=str+'<input type="button" value="Next" class="upload" onclick="nextUserEmail()"/></td></tr>';
							   $(".userEmail").html("");
							   $(".userEmail").append(str).trigger("create");
							 }).fail(function(data){});
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
 * validate email
 * @param email
 * @returns
 */
function valid_email(email) {
	var patten = new RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/);
	return patten.test(email);
}

//previous page email
function previousRecommendEmail(){
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
					if(currentRecommendEmailPage>1){
						currentRecommendEmailPage=currentRecommendEmailPage-1;
						$.ajax({
							 url: strUrl+"EmailServlet?type=0&currentRecommendEmailPage="+currentRecommendEmailPage,
							 type:'GET',
							 cache:false,
							 dataType: "text",
							 timeout:10000
							 }).done(function(data){					   
							   var getJson=eval("(" + data+ ")");
							   var sumPageCounts=getJson.d.sumPages;
							   var emailJSON=getJson.d.getEmail;
							   var str="";
							   for(var i=0; i<emailJSON.length; i++){
								   str=str+'<tr><td id="email'+i+'" align="center" colspan="2">'+emailJSON[i].email+'</td><td align="right"><input type="button" value="Delete" class="upload" id="delete'+i+'" onclick="deleteEmail(this);"/><input type="button" value="Edit" class="upload" id="modify'+i+'" onclick="modifyEmail(this);"/></td></tr>';
							   }
							   str=str+'<tr><td colspan="3" align="center"><input type="button" value="Previous" class="upload" onclick="previousRecommendEmail()"/>';
							   if(sumPageCounts != 0){
								   if(sumPageCounts<10){
									   for(var j=0;j<sumPageCounts;j++){
										   if(currentRecommendEmailPage==(j+1)){
											   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailPage'+j+'" onclick="jumpToPage(this,'+sumPageCounts+')">'+(j+1)+'</span>&nbsp;'; 
										   }else{
											   str=str+'&nbsp;<span class="countPage" id="emailPage'+j+'" onclick="jumpToPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }
									   }
								   }else{
									   if(currentRecommendEmailPage<7){
										   for(var j=0;j<10;j++){
											   if(currentRecommendEmailPage==(j+1)){
												   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailPage'+j+'" onclick="jumpToPage(this,'+sumPageCounts+')">'+(j+1)+'</span>&nbsp;'; 
											   }else{
												   str=str+'&nbsp;<span class="countPage" id="emailPage'+j+'" onclick="jumpToPage(this)">'+(j+1)+'</span>&nbsp;'; 
											   }
										   }
									   }else{
										   if((currentRecommendEmailPage+4)<sumPageCounts){
											   for(var j=(currentRecommendEmailPage-6);j<(currentRecommendEmailPage+4);j++){
												   if(currentRecommendEmailPage==(j+1)){
													   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailPage'+j+'" onclick="jumpToPage(this,'+sumPageCounts+')">'+(j+1)+'</span>&nbsp;'; 
												   }else{
													   str=str+'&nbsp;<span class="countPage" id="emailPage'+j+'" onclick="jumpToPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }
											   }
										   }else{
											   for(var j=(sumPageCounts-10);j<sumPageCounts;j++){
												   if(currentRecommendEmailPage==(j+1)){
													   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailPage'+j+'" onclick="jumpToPage(this,'+sumPageCounts+')">'+(j+1)+'</span>&nbsp;'; 
												   }else{
													   str=str+'&nbsp;<span class="countPage" id="emailPage'+j+'" onclick="jumpToPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }
											   }
										   }
									   }
								   }
							   }
							   str=str+'<input type="button" value="Next" class="upload" onclick="nextRecommendEmail()"/></td></tr>';
							   $(".emailDetail").html("");
							   $(".emailDetail").append(str).trigger("create");
							 }).fail(function(data){});
					}
				}else{
					alert("Operate Time Out");
					window.location="index_login.html";
				}
			}
		}
	});
}

//next page email
function nextRecommendEmail(){
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
					currentRecommendEmailPage=currentRecommendEmailPage+1;
					$.ajax({
						 url: strUrl+"EmailServlet?type=0&currentRecommendEmailPage="+currentRecommendEmailPage,
						 type:'GET',
						 cache:false,
						 dataType: "text",
						 timeout:10000
						 }).done(function(data){
						   var getJson=eval("(" + data+ ")");
						   var sumPageCounts=getJson.d.sumPages;
						   var emailJSON=getJson.d.getEmail;
						   if(emailJSON.length>0){
							   var str="";
							   for(var i=0; i<emailJSON.length; i++){
								   str=str+'<tr><td id="email'+i+'" align="center" colspan="2">'+emailJSON[i].email+'</td><td align="right"><input type="button" value="Delete" class="upload" id="delete'+i+'" onclick="deleteEmail(this);"/><input type="button" value="Edit" class="upload" id="modify'+i+'" onclick="modifyEmail(this);"/></td></tr>';
							   }
							   str=str+'<tr><td colspan="3" align="center"><input type="button" value="Previous" class="upload" onclick="previousRecommendEmail()"/>';
							   if(sumPageCounts != 0){
								   if(sumPageCounts<10){
									   for(var j=0;j<sumPageCounts;j++){
										   if(currentRecommendEmailPage==(j+1)){
											   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailPage'+j+'" onclick="jumpToPage(this,'+sumPageCounts+')">'+(j+1)+'</span>&nbsp;'; 
										   }else{
											   str=str+'&nbsp;<span class="countPage" id="emailPage'+j+'" onclick="jumpToPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }
									   }
								   }else{
									   if(currentRecommendEmailPage<7){
										   for(var j=0;j<10;j++){
											   if(currentRecommendEmailPage==(j+1)){
												   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailPage'+j+'" onclick="jumpToPage(this,'+sumPageCounts+')">'+(j+1)+'</span>&nbsp;'; 
											   }else{
												   str=str+'&nbsp;<span class="countPage" id="emailPage'+j+'" onclick="jumpToPage(this)">'+(j+1)+'</span>&nbsp;'; 
											   }
										   }
									   }else{
										   if((currentRecommendEmailPage+4)<sumPageCounts){
											   for(var j=(currentRecommendEmailPage-6);j<(currentRecommendEmailPage+4);j++){
												   if(currentRecommendEmailPage==(j+1)){
													   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailPage'+j+'" onclick="jumpToPage(this,'+sumPageCounts+')">'+(j+1)+'</span>&nbsp;'; 
												   }else{
													   str=str+'&nbsp;<span class="countPage" id="emailPage'+j+'" onclick="jumpToPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }
											   }
										   }else{
											   for(var j=(sumPageCounts-10);j<sumPageCounts;j++){
												   if(currentRecommendEmailPage==(j+1)){
													   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailPage'+j+'" onclick="jumpToPage(this,'+sumPageCounts+')">'+(j+1)+'</span>&nbsp;'; 
												   }else{
													   str=str+'&nbsp;<span class="countPage" id="emailPage'+j+'" onclick="jumpToPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }
											   }
										   }
									   }
								   }
							   }
							   str=str+'<input type="button" value="Next" class="upload" onclick="nextRecommendEmail()"/></td></tr>';
							   $(".emailDetail").html("");
							   $(".emailDetail").append(str).trigger("create");
						   }else{
							   currentRecommendEmailPage=currentRecommendEmailPage-1;
						   }
						 }).fail(function(data){});	
				}else{
					alert("Operate Time Out");
					window.location="index_login.html";
				}
			}
		}
	});
}

function searchRecommendEmail(){
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
					currentRecommendEmailPage=1;
					var character=$("#recommendemailsearch").val();
					$.ajax({
						 url: encodeURI(encodeURI(strUrl+"EmailServlet?type=6&currentRecommendEmailPage="+currentRecommendEmailPage+"&character="+character)),
						 type:'GET',
						 cache:false,
						 dataType: "text",
						 timeout:10000
						 }).done(function(data){	
						   isRecommendEmailSearchModel=true;
						   var getJson=eval("(" + data+ ")");
						   var sumPageCounts=getJson.d.sumFilterPages;
						   var emailJSON=getJson.d.getFilterEmail;
						   var str="";
						   for(var i=0; i<emailJSON.length; i++){
							   str=str+'<tr><td id="email'+i+'" align="center" colspan="2">'+emailJSON[i].email+'</td><td align="right"><input type="button" value="Delete" class="upload" id="delete'+i+'" onclick="deleteEmail(this);"/><input type="button" value="Edit" class="upload" id="modify'+i+'" onclick="modifyEmail(this);"/></td></tr>';
						   }
						   str=str+'<tr><td colspan="3" align="center"><input type="button" value="Previous" class="upload" onclick="previousRecommendEmailSearch()"/>';
						   if(sumPageCounts != 0){
							   if(sumPageCounts<10){
								   for(var j=0;j<sumPageCounts;j++){
									   if(currentRecommendEmailPage==(j+1)){
										   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
									   }else{
										   str=str+'&nbsp;<span class="countPage" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
									   }
								   }
							   }else{
								   for(var j=0;j<10;j++){
									   if(currentRecommendEmailPage==(j+1)){
										   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
									   }else{
										   str=str+'&nbsp;<span class="countPage" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
									   }
								   }
							   }
						   }
						   str=str+'<input type="button" value="Next" class="upload" onclick="nextRecommendEmailSearch()"/></td></tr>';
						   $(".emailDetail").html("");
						   $(".emailDetail").append(str).trigger("create");
						 }).fail(function(data){});	
				}else{
					alert("Operate Time Out");
					window.location="index_login.html";
				}
			}
		}
	});
}

//previous recommend email page with search filter
function previousRecommendEmailSearch(){
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
					if(currentRecommendEmailPage>1){
						currentRecommendEmailPage=currentRecommendEmailPage-1;
						var character=$("#recommendemailsearch").val();
						$.ajax({
							 url: encodeURI(encodeURI(strUrl+"EmailServlet?type=6&currentRecommendEmailPage="+currentRecommendEmailPage+"&character="+character)),
							 type:'GET',
							 cache:false,
							 dataType: "text",
							 timeout:10000
							 }).done(function(data){					   
								 var getJson=eval("(" + data+ ")");
								   var sumPageCounts=getJson.d.sumFilterPages;
								   var emailJSON=getJson.d.getFilterEmail;
								   var str="";
								   for(var i=0; i<emailJSON.length; i++){
									   str=str+'<tr><td id="email'+i+'" align="center" colspan="2">'+emailJSON[i].email+'</td><td align="right"><input type="button" value="Delete" class="upload" id="delete'+i+'" onclick="deleteEmail(this);"/><input type="button" value="Edit" class="upload" id="modify'+i+'" onclick="modifyEmail(this);"/></td></tr>';
								   }
								   str=str+'<tr><td colspan="3" align="center"><input type="button" value="Previous" class="upload" onclick="previousRecommendEmailSearch()"/>';
								   if(sumPageCounts != 0){
									   if(sumPageCounts<10){
										   for(var j=0;j<sumPageCounts;j++){
											   if(currentRecommendEmailPage==(j+1)){
												   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
											   }else{
												   str=str+'&nbsp;<span class="countPage" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
											   }
										   }
									   }else{
										   if(currentRecommendEmailPage<7){
											   for(var j=0;j<10;j++){
												   if(currentRecommendEmailPage==(j+1)){
													   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }else{
													   str=str+'&nbsp;<span class="countPage" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }
											   }
										   }else{
											   if((currentRecommendEmailPage+4)<sumPageCounts){
												   for(var j=(currentRecommendEmailPage-6);j<(currentRecommendEmailPage+4);j++){
													   if(currentRecommendEmailPage==(j+1)){
														   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
													   }else{
														   str=str+'&nbsp;<span class="countPage" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
													   }
												   }
											   }else{
												   for(var j=(sumPageCounts-10);j<sumPageCounts;j++){
													   if(currentRecommendEmailPage==(j+1)){
														   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
													   }else{
														   str=str+'&nbsp;<span class="countPage" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
													   }
												   }
											   }
										   }
									   }
								   }
								   str=str+'<input type="button" value="Next" class="upload" onclick="nextRecommendEmailSearch()"/></td></tr>';
								   $(".emailDetail").html("");
								   $(".emailDetail").append(str).trigger("create");
							 }).fail(function(data){});	
					}
				}else{
					alert("Operate Time Out");
					window.location="index_login.html";
				}
			}
		}
	});
}

//next recommend email page with search filter
function nextRecommendEmailSearch(){
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
					currentRecommendEmailPage=currentRecommendEmailPage+1;
					var character=$("#recommendemailsearch").val();
					$.ajax({
						 url: encodeURI(encodeURI(strUrl+"EmailServlet?type=6&currentRecommendEmailPage="+currentRecommendEmailPage+"&character="+character)),
						 type:'GET',
						 cache:false,
						 dataType: "text",
						 timeout:10000
						 }).done(function(data){					   
						   var getJson=eval("(" + data+ ")");
						   var sumPageCounts=getJson.d.sumFilterPages;
						   var emailJSON=getJson.d.getFilterEmail;
						   if(emailJSON.length>0){
							   var str="";
							   for(var i=0; i<emailJSON.length; i++){
								   str=str+'<tr><td id="email'+i+'" align="center" colspan="2">'+emailJSON[i].email+'</td><td align="right"><input type="button" value="Delete" class="upload" id="delete'+i+'" onclick="deleteEmail(this);"/><input type="button" value="Edit" class="upload" id="modify'+i+'" onclick="modifyEmail(this);"/></td></tr>';
							   }
							   str=str+'<tr><td colspan="3" align="center"><input type="button" value="Previous" class="upload" onclick="previousRecommendEmailSearch()"/>';
							   if(sumPageCounts != 0){
								   if(sumPageCounts<10){
									   for(var j=0;j<sumPageCounts;j++){
										   if(currentRecommendEmailPage==(j+1)){
											   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }else{
											   str=str+'&nbsp;<span class="countPage" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }
									   }
								   }else{
									   if(currentRecommendEmailPage<7){
										   for(var j=0;j<10;j++){
											   if(currentRecommendEmailPage==(j+1)){
												   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
											   }else{
												   str=str+'&nbsp;<span class="countPage" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
											   }
										   }
									   }else{
										   if((currentRecommendEmailPage+4)<sumPageCounts){
											   for(var j=(currentRecommendEmailPage-6);j<(currentRecommendEmailPage+4);j++){
												   if(currentRecommendEmailPage==(j+1)){
													   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }else{
													   str=str+'&nbsp;<span class="countPage" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }
											   }
										   }else{
											   for(var j=(sumPageCounts-10);j<sumPageCounts;j++){
												   if(currentRecommendEmailPage==(j+1)){
													   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }else{
													   str=str+'&nbsp;<span class="countPage" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }
											   }
										   }
									   }
								   }
							   }
							   str=str+'<input type="button" value="Next" class="upload" onclick="nextRecommendEmailSearch()"/></td></tr>';
							   $(".emailDetail").html("");
							   $(".emailDetail").append(str).trigger("create");
						   }else{
							   currentRecommendEmailPage=currentRecommendEmailPage-1;
						   }
						 }).fail(function(data){});		
				}else{
					alert("Operate Time Out");
					window.location="index_login.html";
				}
			}
		}
	});
}

function jumpToPage(evt){
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
					var emailPageId=(evt.id).substr(9);
					if((parseInt(emailPageId)+1)==currentRecommendEmailPage){
						
					}else{
						currentRecommendEmailPage=parseInt(emailPageId)+1;
						jumpAndRefreshRecommendEmail(currentRecommendEmailPage);
					}				
				}else{
					alert("Operate Time Out");
					window.location="index_login.html";
				}
			}
		}
	});	
}

//search email with filter character
function jumpToFilterPage(evt){
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
					var emailFilterPageId=(evt.id).substr(14);
					if((parseInt(emailFilterPageId)+1)==currentRecommendEmailPage){
						
					}else{
						var character=$("#recommendemailsearch").val();
						currentRecommendEmailPage=(parseInt(emailFilterPageId)+1);
						jumpAndRefreshRecommendEmailWithFilter(currentRecommendEmailPage,character);
					}
				}else{
					alert("Operate Time Out");
					window.location="index_login.html";
				}
			}
		}
	});
}

//jump to specified recommend email page
function jumpAndRefreshRecommendEmail(currentPage){
	$.ajax({
		 url: strUrl+"EmailServlet?type=0&currentRecommendEmailPage="+currentPage,
		 type:'GET',
		 cache:false,
		 dataType: "text",
		 timeout:10000
		 }).done(function(data){
		   var getJson=eval("(" + data+ ")");
		   var sumPageCounts=getJson.d.sumPages;
		   var emailJSON=getJson.d.getEmail;
		   $(".emailDetail").html("");
		   var str="";
		   for(var i=0; i<emailJSON.length; i++){
			   str=str+'<tr><td id="email'+i+'" align="center" colspan="2">'+emailJSON[i].email+'</td><td align="right"><input type="button" value="Delete" class="upload" id="delete'+i+'" onclick="deleteEmail(this);"/><input type="button" value="Edit" class="upload" id="modify'+i+'" onclick="modifyEmail(this);"/></td></tr>';
		   }
		   str=str+'<tr><td colspan="3" align="center"><input type="button" value="Previous" class="upload" onclick="previousRecommendEmail()"/>';
		   if(sumPageCounts != 0){
			   if(sumPageCounts<10){
				   for(var j=0;j<sumPageCounts;j++){
					   if(currentRecommendEmailPage==(j+1)){
						   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailPage'+j+'" onclick="jumpToPage(this,'+sumPageCounts+')">'+(j+1)+'</span>&nbsp;'; 
					   }else{
						   str=str+'&nbsp;<span class="countPage" id="emailPage'+j+'" onclick="jumpToPage(this)">'+(j+1)+'</span>&nbsp;'; 
					   }
				   }
			   }else{
				   if(currentRecommendEmailPage<7){
					   for(var j=0;j<10;j++){
						   if(currentRecommendEmailPage==(j+1)){
							   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailPage'+j+'" onclick="jumpToPage(this,'+sumPageCounts+')">'+(j+1)+'</span>&nbsp;'; 
						   }else{
							   str=str+'&nbsp;<span class="countPage" id="emailPage'+j+'" onclick="jumpToPage(this)">'+(j+1)+'</span>&nbsp;'; 
						   }
					   }
				   }else{
					   if((currentRecommendEmailPage+4)<sumPageCounts){
						   for(var j=(currentRecommendEmailPage-6);j<(currentRecommendEmailPage+4);j++){
							   if(currentRecommendEmailPage==(j+1)){
								   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailPage'+j+'" onclick="jumpToPage(this,'+sumPageCounts+')">'+(j+1)+'</span>&nbsp;'; 
							   }else{
								   str=str+'&nbsp;<span class="countPage" id="emailPage'+j+'" onclick="jumpToPage(this)">'+(j+1)+'</span>&nbsp;'; 
							   }
						   }
					   }else{
						   for(var j=(sumPageCounts-10);j<sumPageCounts;j++){
							   if(currentRecommendEmailPage==(j+1)){
								   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailPage'+j+'" onclick="jumpToPage(this,'+sumPageCounts+')">'+(j+1)+'</span>&nbsp;'; 
							   }else{
								   str=str+'&nbsp;<span class="countPage" id="emailPage'+j+'" onclick="jumpToPage(this)">'+(j+1)+'</span>&nbsp;'; 
							   }
						   }
					   }
				   }
			   }
		   }
		   str=str+'<input type="button" value="Next" class="upload" onclick="nextRecommendEmail()"/></td></tr>';
		   $(".emailDetail").append(str).trigger("create");   
		 }).fail(function(data){});	
}

//jump to specified recommend email page with filter
function jumpAndRefreshRecommendEmailWithFilter(currentPage,filterChar){
	$.ajax({
		 url: encodeURI(encodeURI(strUrl+"EmailServlet?type=6&currentRecommendEmailPage="+currentPage+"&character="+filterChar)),
		 type:'GET',
		 cache:false,
		 dataType: "text",
		 timeout:10000
		 }).done(function(data){					   
		   var getJson=eval("(" + data+ ")");
		   var sumPageCounts=getJson.d.sumFilterPages;
		   var emailJSON=getJson.d.getFilterEmail;
		   $(".emailDetail").html("");
		   var str="";
		   for(var i=0; i<emailJSON.length; i++){
			   str=str+'<tr><td id="email'+i+'" align="center" colspan="2">'+emailJSON[i].email+'</td><td align="right"><input type="button" value="Delete" class="upload" id="delete'+i+'" onclick="deleteEmail(this);"/><input type="button" value="Edit" class="upload" id="modify'+i+'" onclick="modifyEmail(this);"/></td></tr>';
		   }
		   str=str+'<tr><td colspan="3" align="center"><input type="button" value="Previous" class="upload" onclick="previousRecommendEmailSearch()"/>';
		   if(sumPageCounts != 0){
			   if(sumPageCounts<10){
				   for(var j=0;j<sumPageCounts;j++){
					   if(currentRecommendEmailPage==(j+1)){
						   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
					   }else{
						   str=str+'&nbsp;<span class="countPage" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
					   }
				   }
			   }else{
				   if(currentRecommendEmailPage<7){
					   for(var j=0;j<10;j++){
						   if(currentRecommendEmailPage==(j+1)){
							   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
						   }else{
							   str=str+'&nbsp;<span class="countPage" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
						   }
					   }
				   }else{
					   if((currentRecommendEmailPage+4)<sumPageCounts){
						   for(var j=(currentRecommendEmailPage-6);j<(currentRecommendEmailPage+4);j++){
							   if(currentRecommendEmailPage==(j+1)){
								   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
							   }else{
								   str=str+'&nbsp;<span class="countPage" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
							   }
						   }
					   }else{
						   for(var j=(sumPageCounts-10);j<sumPageCounts;j++){
							   if(currentRecommendEmailPage==(j+1)){
								   str=str+'&nbsp;<span class="countPage" style="background:red;" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
							   }else{
								   str=str+'&nbsp;<span class="countPage" id="emailFlterPage'+j+'" onclick="jumpToFilterPage(this)">'+(j+1)+'</span>&nbsp;'; 
							   }
						   }
					   }
				   }
			   }
		   }
		   str=str+'<input type="button" value="Next" class="upload" onclick="nextRecommendEmailSearch()"/></td></tr>';
		   $(".emailDetail").append(str).trigger("create");
		 }).fail(function(data){});
}

//previous user email
function previousUserEmail(){
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
					if(currentUserEmailPage>1){
						currentUserEmailPage=currentUserEmailPage-1;
						//get all user email
						$.ajax({
							 url: strUrl+"EmailServlet?type=4&currentUserEmailPage="+currentUserEmailPage,
							 type:'GET',
							 cache:false,
							 dataType: "text",
							 timeout:10000
							 }).done(function(data){
							   var getJson=eval("(" + data+ ")");
							   var sumUserPages=getJson.d.sumUserPages;
							   var userJSON=getJson.d.getUserAndCourses;
							   var str="";
							   for(var i=0; i<userJSON.length; i++){
								   str=str+'<tr><td align="center" id="user'+userJSON[i].userId+'">'+userJSON[i].userId+'</td><td align="center">'+userJSON[i].firstName+" "+userJSON[i].lastName+'</td><td align="center"><input type="text" id="email'+userJSON[i].userId+'" value="'+userJSON[i].email+'" disabled maxlength="50"/></td><td align="center">';
								   var maslulNosStr="";
								   var maslulNos=userJSON[i].maslulNos;
								   for(var j=0; j<maslulNos.length; j++){
									   maslulNosStr=maslulNosStr+maslulNos[j]+" ";
								   }
								   str=str+maslulNosStr+'</td><td align="center"><input type="button" id="edit'+userJSON[i].userId+'" value="Edit" onclick="editUserEmail(this)"/></td></tr>';
								}
							   str=str+'<tr><td colspan="5" align="center"><input type="button" value="Previous" class="upload" onclick="previousUserEmail()"/>';
							   if(sumUserPages != 0){
								   if(sumUserPages<10){
									   for(var j=0;j<sumUserPages;j++){
										   if(currentUserEmailPage==(j+1)){
											   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }else{
											   str=str+'&nbsp;<span class="countPage" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }
									   }
								   }else{
									   if(currentUserEmailPage<7){
										   for(var j=0;j<10;j++){
											   if(currentUserEmailPage==(j+1)){
												   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
											   }else{
												   str=str+'&nbsp;<span class="countPage" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
											   }
										   }
									   }else{
										   if((currentUserEmailPage+4)<sumUserPages){
											   for(var j=(currentUserEmailPage-6);j<(currentUserEmailPage+4);j++){
												   if(currentUserEmailPage==(j+1)){
													   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }else{
													   str=str+'&nbsp;<span class="countPage" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }
											   }
										   }else{
											   for(var j=(sumUserPages-10);j<sumUserPages;j++){
												   if(currentUserEmailPage==(j+1)){
													   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }else{
													   str=str+'&nbsp;<span class="countPage" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }
											   }
										   }
									   }
								   }
							   }
							   str=str+'<input type="button" value="Next" class="upload" onclick="nextUserEmail()"/></td></tr>';
							   $(".userEmail").html("");
							   $(".userEmail").append(str).trigger("create");
							 }).fail(function(data){});
					}
				}else{
					alert("Operate Time Out");
					window.location="index_login.html";
				}
			}
		}
	});
}

//next user email
function nextUserEmail(){
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
					currentUserEmailPage=currentUserEmailPage+1;
					//get all user email
					$.ajax({
						 url: strUrl+"EmailServlet?type=4&currentUserEmailPage="+currentUserEmailPage,
						 type:'GET',
						 cache:false,
						 dataType: "text",
						 timeout:10000
						 }).done(function(data){
						   var getJson=eval("(" + data+ ")");
						   var sumUserPages=getJson.d.sumUserPages;
						   var userJSON=getJson.d.getUserAndCourses;
						   if(userJSON.length>0){
							   var str="";
							   for(var i=0; i<userJSON.length; i++){
								   str=str+'<tr><td align="center" id="user'+userJSON[i].userId+'">'+userJSON[i].userId+'</td><td align="center">'+userJSON[i].firstName+" "+userJSON[i].lastName+'</td><td align="center"><input type="text" id="email'+userJSON[i].userId+'" value="'+userJSON[i].email+'" disabled maxlength="50"/></td><td align="center">';
								   var maslulNosStr="";
								   var maslulNos=userJSON[i].maslulNos;
								   for(var j=0; j<maslulNos.length; j++){
									   maslulNosStr=maslulNosStr+maslulNos[j]+" ";
								   }
								   str=str+maslulNosStr+'</td><td align="center"><input type="button" id="edit'+userJSON[i].userId+'" value="Edit" onclick="editUserEmail(this)"/></td></tr>';
								}
							   str=str+'<tr><td colspan="5" align="center"><input type="button" value="Previous" class="upload" onclick="previousUserEmail()"/>';
							   if(sumUserPages != 0){
								   if(sumUserPages<10){
									   for(var j=0;j<sumUserPages;j++){
										   if(currentUserEmailPage==(j+1)){
											   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }else{
											   str=str+'&nbsp;<span class="countPage" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }
									   }
								   }else{
									   if(currentUserEmailPage<7){
										   for(var j=0;j<10;j++){
											   if(currentUserEmailPage==(j+1)){
												   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
											   }else{
												   str=str+'&nbsp;<span class="countPage" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
											   }
										   }
									   }else{
										   if((currentUserEmailPage+4)<sumUserPages){
											   for(var j=(currentUserEmailPage-6);j<(currentUserEmailPage+4);j++){
												   if(currentUserEmailPage==(j+1)){
													   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }else{
													   str=str+'&nbsp;<span class="countPage" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }
											   }
										   }else{
											   for(var j=(sumUserPages-10);j<sumUserPages;j++){
												   if(currentUserEmailPage==(j+1)){
													   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }else{
													   str=str+'&nbsp;<span class="countPage" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }
											   }
										   }
									   }
								   }
							   }
							   str=str+'<input type="button" value="Next" class="upload" onclick="nextUserEmail()"/></td></tr>';
							   $(".userEmail").html("");
							   $(".userEmail").append(str).trigger("create");
						   }else{
							   currentUserEmailPage=currentUserEmailPage-1;
						   }
						 }).fail(function(data){});	
				}else{
					alert("Operate Time Out");
					window.location="index_login.html";
				}
			}
		}
	});
}

//search user email
function searchUserEmail(){
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
					currentUserEmailPage=1;
					var characterName=$("#useremailsearch").val();
					$.ajax({
						 //url: strUrl+"EmailServlet?type=7&currentUserEmailPage="+currentUserEmailPage+"&characterName="+encodeURI(characterName),
						 url:encodeURI(encodeURI(strUrl+"EmailServlet?type=7&currentUserEmailPage="+currentUserEmailPage+"&characterName="+characterName)),
						 type:'GET',
						 cache:false,
						 dataType: "text",
						 timeout:10000
						 }).done(function(data){
						   isUserEmailSearchModel=true;
						   var getJson=eval("(" + data+ ")");
						   var sumUserPages=getJson.d.sumFilterUserPages;
						   var userJSON=getJson.d.getFilterUserAndCourses;
						   var str="";
						   for(var i=0; i<userJSON.length; i++){
							   str=str+'<tr><td align="center" id="user'+userJSON[i].userId+'">'+userJSON[i].userId+'</td><td align="center">'+userJSON[i].firstName+" "+userJSON[i].lastName+'</td><td align="center"><input type="text" id="email'+userJSON[i].userId+'" value="'+userJSON[i].email+'" disabled maxlength="50"/></td><td align="center">';
							   var maslulNosStr="";
							   var maslulNos=userJSON[i].maslulNos;
							   for(var j=0; j<maslulNos.length; j++){
								   maslulNosStr=maslulNosStr+maslulNos[j]+" ";
							   }
							   str=str+maslulNosStr+'</td><td align="center"><input type="button" id="edit'+userJSON[i].userId+'" value="Edit" onclick="editUserEmail(this)"/></td></tr>';
							}
						   str=str+'<tr><td colspan="5" align="center"><input type="button" value="Previous" class="upload" onclick="previousUserEmailSearch()"/>';
						   if(sumUserPages != 0){
							   if(sumUserPages<10){
								   for(var j=0;j<sumUserPages;j++){
									   if(currentUserEmailPage==(j+1)){
										   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
									   }else{
										   str=str+'&nbsp;<span class="countPage" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
									   }
								   }
							   }else{
								   for(var j=0;j<10;j++){
									   if(currentUserEmailPage==(j+1)){
										   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
									   }else{
										   str=str+'&nbsp;<span class="countPage" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
									   }
								   }
							   }
						   }
						   str=str+'<input type="button" value="Next" class="upload" onclick="nextUserEmailSearch()"/></td></tr>';
						   $(".userEmail").html("");
						   $(".userEmail").append(str).trigger("create");
						 }).fail(function(data){});
				}else{
					alert("Operate Time Out");
					window.location="index_login.html";
				}
			}
		}
	});
}

function previousUserEmailSearch(){
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
					if(currentUserEmailPage>1){
						currentUserEmailPage=currentUserEmailPage-1;
						var characterName=$("#useremailsearch").val();
						$.ajax({
							 url:encodeURI(encodeURI(strUrl+"EmailServlet?type=7&currentUserEmailPage="+currentUserEmailPage+"&characterName="+characterName)) ,
							 type:'GET',
							 cache:false,
							 dataType: "text",
							 timeout:10000
							 }).done(function(data){
							   var getJson=eval("(" + data+ ")");
							   var sumUserPages=getJson.d.sumFilterUserPages;
							   var userJSON=getJson.d.getFilterUserAndCourses;
							   var str="";
							   for(var i=0; i<userJSON.length; i++){
								   str=str+'<tr><td align="center" id="user'+userJSON[i].userId+'">'+userJSON[i].userId+'</td><td align="center">'+userJSON[i].firstName+" "+userJSON[i].lastName+'</td><td align="center"><input type="text" id="email'+userJSON[i].userId+'" value="'+userJSON[i].email+'" disabled maxlength="50"/></td><td align="center">';
								   var maslulNosStr="";
								   var maslulNos=userJSON[i].maslulNos;
								   for(var j=0; j<maslulNos.length; j++){
									   maslulNosStr=maslulNosStr+maslulNos[j]+" ";
								   }
								   str=str+maslulNosStr+'</td><td align="center"><input type="button" id="edit'+userJSON[i].userId+'" value="Edit" onclick="editUserEmail(this)"/></td></tr>';
								}
							   str=str+'<tr><td colspan="5" align="center"><input type="button" value="Previous" class="upload" onclick="previousUserEmailSearch()"/>';
							   if(sumUserPages != 0){
								   if(sumUserPages<10){
									   for(var j=0;j<sumUserPages;j++){
										   if(currentUserEmailPage==(j+1)){
											   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }else{
											   str=str+'&nbsp;<span class="countPage" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }
									   }
								   }else{
									   if(currentUserEmailPage<7){
										   for(var j=0;j<10;j++){
											   if(currentUserEmailPage==(j+1)){
												   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
											   }else{
												   str=str+'&nbsp;<span class="countPage" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
											   }
										   }
									   }else{
										   if((currentUserEmailPage+4)<sumUserPages){
											   for(var j=(currentUserEmailPage-6);j<(currentUserEmailPage+4);j++){
												   if(currentUserEmailPage==(j+1)){
													   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }else{
													   str=str+'&nbsp;<span class="countPage" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }
											   }
										   }else{
											   for(var j=(sumUserPages-10);j<sumUserPages;j++){
												   if(currentUserEmailPage==(j+1)){
													   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }else{
													   str=str+'&nbsp;<span class="countPage" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }
											   }
										   }
									   }
								   }
							   }
							   str=str+'<input type="button" value="Next" class="upload" onclick="nextUserEmailSearch()"/></td></tr>';
							   $(".userEmail").html("");
							   $(".userEmail").append(str).trigger("create");
							 }).fail(function(data){});
					}
				}else{
					alert("Operate Time Out");
					window.location="index_login.html";
				}
			}
		}
	});
}

function nextUserEmailSearch(){
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
					currentUserEmailPage=currentUserEmailPage+1;
					var characterName=$("#useremailsearch").val();
					$.ajax({
						 url: encodeURI(encodeURI(strUrl+"EmailServlet?type=7&currentUserEmailPage="+currentUserEmailPage+"&characterName="+characterName)),
						 type:'GET',
						 cache:false,
						 dataType: "text",
						 timeout:10000
						 }).done(function(data){
						   var getJson=eval("(" + data+ ")");
						   var sumUserPages=getJson.d.sumFilterUserPages;
						   var userJSON=getJson.d.getFilterUserAndCourses;
						   if(userJSON.length>0){
							   var str="";
							   for(var i=0; i<userJSON.length; i++){
								   str=str+'<tr><td align="center" id="user'+userJSON[i].userId+'">'+userJSON[i].userId+'</td><td align="center">'+userJSON[i].firstName+" "+userJSON[i].lastName+'</td><td align="center"><input type="text" id="email'+userJSON[i].userId+'" value="'+userJSON[i].email+'" disabled maxlength="50"/></td><td align="center">';
								   var maslulNosStr="";
								   var maslulNos=userJSON[i].maslulNos;
								   for(var j=0; j<maslulNos.length; j++){
									   maslulNosStr=maslulNosStr+maslulNos[j]+" ";
								   }
								   str=str+maslulNosStr+'</td><td align="center"><input type="button" id="edit'+userJSON[i].userId+'" value="Edit" onclick="editUserEmail(this)"/></td></tr>';
								}
							   str=str+'<tr><td colspan="5" align="center"><input type="button" value="Previous" class="upload" onclick="previousUserEmailSearch()"/>';
							   if(sumUserPages != 0){
								   if(sumUserPages<10){
									   for(var j=0;j<sumUserPages;j++){
										   if(currentUserEmailPage==(j+1)){
											   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }else{
											   str=str+'&nbsp;<span class="countPage" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
										   }
									   }
								   }else{
									   if(currentUserEmailPage<7){
										   for(var j=0;j<10;j++){
											   if(currentUserEmailPage==(j+1)){
												   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
											   }else{
												   str=str+'&nbsp;<span class="countPage" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
											   }
										   }
									   }else{
										   if((currentUserEmailPage+4)<sumUserPages){
											   for(var j=(currentUserEmailPage-6);j<(currentUserEmailPage+4);j++){
												   if(currentUserEmailPage==(j+1)){
													   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }else{
													   str=str+'&nbsp;<span class="countPage" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }
											   }
										   }else{
											   for(var j=(sumUserPages-10);j<sumUserPages;j++){
												   if(currentUserEmailPage==(j+1)){
													   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }else{
													   str=str+'&nbsp;<span class="countPage" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
												   }
											   }
										   }
									   }
								   }
							   }
							   str=str+'<input type="button" value="Next" class="upload" onclick="nextUserEmailSearch()"/></td></tr>';
							   $(".userEmail").html("");
							   $(".userEmail").append(str).trigger("create");
						   }else{
							   currentUserEmailPage=currentUserEmailPage-1;
						   }
						 }).fail(function(data){});
				}else{
					alert("Operate Time Out");
					window.location="index_login.html";
				}
			}
		}
	});
}

function jumpToUserEmailPage(evt){
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
					var userEmailPageId=(evt.id).substr(14);
					if((parseInt(userEmailPageId)+1)==currentUserEmailPage){
						
					}else{
						currentUserEmailPage=parseInt(userEmailPageId)+1;
						jumpAndRefreshUserEmail(currentUserEmailPage);
					}				
				}else{
					alert("Operate Time Out");
					window.location="index_login.html";
				}
			}
		}
	});
}

function jumpToFilterUserEmailPage(evt){
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
					var userEmailPageId=(evt.id).substr(20);
					if((parseInt(userEmailPageId)+1)==currentUserEmailPage){
						
					}else{
						currentUserEmailPage=parseInt(userEmailPageId)+1;
						var characterName=$("#useremailsearch").val();
						jumpAndRefreshUserEmailWithFilter(currentUserEmailPage, characterName);
					}				
				}else{
					alert("Operate Time Out");
					window.location="index_login.html";
				}
			}
		}
	});
}

//jump to specified user email page
function jumpAndRefreshUserEmail(currentPage){
	$.ajax({
		 url: strUrl+"EmailServlet?type=4&currentUserEmailPage="+currentPage,
		 type:'GET',
		 cache:false,
		 dataType: "text",
		 timeout:10000
		 }).done(function(data){
		   var getJson=eval("(" + data+ ")");
		   var sumUserPages=getJson.d.sumUserPages;
		   var userJSON=getJson.d.getUserAndCourses;
		   var str="";
		   for(var i=0; i<userJSON.length; i++){
			   str=str+'<tr><td align="center" id="user'+userJSON[i].userId+'">'+userJSON[i].userId+'</td><td align="center">'+userJSON[i].firstName+" "+userJSON[i].lastName+'</td><td align="center"><input type="text" id="email'+userJSON[i].userId+'" value="'+userJSON[i].email+'" disabled maxlength="50"/></td><td align="center">';
			   var maslulNosStr="";
			   var maslulNos=userJSON[i].maslulNos;
			   for(var j=0; j<maslulNos.length; j++){
				   maslulNosStr=maslulNosStr+maslulNos[j]+" ";
			   }
			   str=str+maslulNosStr+'</td><td align="center"><input type="button" id="edit'+userJSON[i].userId+'" value="Edit" onclick="editUserEmail(this)"/></td></tr>';
			}
		   str=str+'<tr><td colspan="5" align="center"><input type="button" value="Previous" class="upload" onclick="previousUserEmail()"/>';
		   if(sumUserPages != 0){
			   if(sumUserPages<10){
				   for(var j=0;j<sumUserPages;j++){
					   if(currentUserEmailPage==(j+1)){
						   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
					   }else{
						   str=str+'&nbsp;<span class="countPage" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
					   }
				   }
			   }else{
				   if(currentUserEmailPage<7){
					   for(var j=0;j<10;j++){
						   if(currentUserEmailPage==(j+1)){
							   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
						   }else{
							   str=str+'&nbsp;<span class="countPage" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
						   }
					   }
				   }else{
					   if((currentUserEmailPage+4)<sumUserPages){
						   for(var j=(currentUserEmailPage-6);j<(currentUserEmailPage+4);j++){
							   if(currentUserEmailPage==(j+1)){
								   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
							   }else{
								   str=str+'&nbsp;<span class="countPage" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
							   }
						   }
					   }else{
						   for(var j=(sumUserPages-10);j<sumUserPages;j++){
							   if(currentUserEmailPage==(j+1)){
								   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
							   }else{
								   str=str+'&nbsp;<span class="countPage" id="userEemailPage'+j+'" onclick="jumpToUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
							   }
						   }
					   }
				   }
			   }
		   }
		   str=str+'<input type="button" value="Next" class="upload" onclick="nextUserEmail()"/></td></tr>';
		   $(".userEmail").html("");
		   $(".userEmail").append(str).trigger("create");
		 }).fail(function(data){});
}

//jump to specified user email page with filter
function jumpAndRefreshUserEmailWithFilter(currentPage,filterChar){
	$.ajax({
		 url: encodeURI(encodeURI(strUrl+"EmailServlet?type=7&currentUserEmailPage="+currentPage+"&characterName="+filterChar)),
		 type:'GET',
		 cache:false,
		 dataType: "text",
		 timeout:10000
		 }).done(function(data){
		   var getJson=eval("(" + data+ ")");
		   var sumUserPages=getJson.d.sumFilterUserPages;
		   var userJSON=getJson.d.getFilterUserAndCourses;
		   var str="";
		   for(var i=0; i<userJSON.length; i++){
			   str=str+'<tr><td align="center" id="user'+userJSON[i].userId+'">'+userJSON[i].userId+'</td><td align="center">'+userJSON[i].firstName+" "+userJSON[i].lastName+'</td><td align="center"><input type="text" id="email'+userJSON[i].userId+'" value="'+userJSON[i].email+'" disabled maxlength="50"/></td><td align="center">';
			   var maslulNosStr="";
			   var maslulNos=userJSON[i].maslulNos;
			   for(var j=0; j<maslulNos.length; j++){
				   maslulNosStr=maslulNosStr+maslulNos[j]+" ";
			   }
			   str=str+maslulNosStr+'</td><td align="center"><input type="button" id="edit'+userJSON[i].userId+'" value="Edit" onclick="editUserEmail(this)"/></td></tr>';
			}
		   str=str+'<tr><td colspan="5" align="center"><input type="button" value="Previous" class="upload" onclick="previousUserEmailSearch()"/>';
		   if(sumUserPages != 0){
			   if(sumUserPages<10){
				   for(var j=0;j<sumUserPages;j++){
					   if(currentUserEmailPage==(j+1)){
						   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
					   }else{
						   str=str+'&nbsp;<span class="countPage" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
					   }
				   }
			   }else{
				   if(currentUserEmailPage<7){
					   for(var j=0;j<10;j++){
						   if(currentUserEmailPage==(j+1)){
							   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
						   }else{
							   str=str+'&nbsp;<span class="countPage" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
						   }
					   }
				   }else{
					   if((currentUserEmailPage+4)<sumUserPages){
						   for(var j=(currentUserEmailPage-6);j<(currentUserEmailPage+4);j++){
							   if(currentUserEmailPage==(j+1)){
								   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
							   }else{
								   str=str+'&nbsp;<span class="countPage" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
							   }
						   }
					   }else{
						   for(var j=(sumUserPages-10);j<sumUserPages;j++){
							   if(currentUserEmailPage==(j+1)){
								   str=str+'&nbsp;<span class="countPage" style="background:red;" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
							   }else{
								   str=str+'&nbsp;<span class="countPage" id="userFilterEemailPage'+j+'" onclick="jumpToFilterUserEmailPage(this)">'+(j+1)+'</span>&nbsp;'; 
							   }
						   }
					   }
				   }
			   }
		   }
		   str=str+'<input type="button" value="Next" class="upload" onclick="nextUserEmailSearch()"/></td></tr>';
		   $(".userEmail").html("");
		   $(".userEmail").append(str).trigger("create");
		 }).fail(function(data){});
}

/**
 * delete specified email
 * @param a
 */
function deleteEmail(a){
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
					//let user confirm
					$("#emailinput").val("");
					var deleteConfirm=confirm("Delete?");
					if(deleteConfirm){
						var emailId="email"+$(a).attr("id").substring($(a).attr("id").length-1,$(a).attr("id").length);
						var email=$("#" + emailId).html();
						$.ajax({
							url: strUrl+"EmailServlet?type=3&emailName="+email,
							type:'POST',
							dataType: "text",
							cache:false,
							timeout:10000
							}).done(function(data){
								if(data=="true"){
									//window.location.href="index_email.html";
									if(isRecommendEmailSearchModel==true){
										var character=$("#recommendemailsearch").val();
										jumpAndRefreshRecommendEmailWithFilter(currentRecommendEmailPage, character);
									}else{
										jumpAndRefreshRecommendEmail(currentRecommendEmailPage);
									}
									alert("Delete Email Successfully");
								}
							}).fail(function(data){
	
						}); 
					}else{
						
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
 * edit email
 * @param a
 */
function modifyEmail(a){
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
					var emailId="email"+$(a).attr("id").substring($(a).attr("id").length-1,$(a).attr("id").length);
					emailEdit=$("#" + emailId).html();

					operate=1;
					$("#emailinput").val(emailEdit);
				}else{
					alert("Operate Time Out");
					window.location="index_login.html";
				}
			}
		}
	});
}

/**
 * modify or add email
 * @returns {Boolean}
 */
function saveEmail(){
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
					var emails=$("#emailinput").val();
					//0 add a new email  1 edit the specified email
					if(operate==0){
						if(valid_email(emails)==true){
							$.ajax({
								url: strUrl+"EmailServlet?type=2&email="+emails,
							    type:'POST',
								cache:false,
							    dataType: "text",
							    timeout:10000
							   }).done(function(data){
								   if(data=="true"){
									   $("#emailinput").val("");
									   if(isRecommendEmailSearchModel==true){
										   var character=$("#recommendemailsearch").val();
										   jumpAndRefreshRecommendEmailWithFilter(currentRecommendEmailPage, character);
									   }else{
										   jumpAndRefreshRecommendEmail(currentRecommendEmailPage);
									   }
									   alert("Add Email Successfully");
									   //window.location.href="index_email.html"; 
								   }
							   }).fail(function(data){

							  });
						}else{
							document.getElementById("emailinput").value='';
							alert("Please Input A Correct Email");
							return false;
						}
					}
					if(operate==1){
						if(valid_email(emails)==true){
							$.ajax({
								url: strUrl+"EmailServlet?type=1&oldEmail="+emailEdit+"&newEmail="+emails,
							    type:'POST',
								cache:false,
							    dataType: "text",
							    timeout:10000
							   }).done(function(data){					   
								   if(data=="true"){
									   $("#emailinput").val("");
									   operate=0;
									   if(isRecommendEmailSearchModel==true){
										   var character=$("#recommendemailsearch").val();
										   jumpAndRefreshRecommendEmailWithFilter(currentRecommendEmailPage, character);
									   }else{
										   jumpAndRefreshRecommendEmail(currentRecommendEmailPage);
									   }
									   alert("Edit Email Successfully");
									   //window.location.href="index_email.html"; 
								   }
							   }).fail(function(data){

							});
						}else{
							document.getElementById("emailinput").value='';
							alert("Please Input A Correct Email");
							return false;	
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
 * edit user email
 */
function editUserEmail(a){
	var buttonValue=$(a).attr("value");
	var buttonId=$(a).attr("id");
	var userId=buttonId.substr(4);
	if(buttonValue=="Edit"){
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
						var initEmail="";
						if(lastEditUserEmail.length==0){
							$("#email"+userId).attr("disabled",false);
							initEmail = $("#email"+userId).attr("value");
							$(a).attr("value","Save");
							$(a).parent().append('<input type="button" value="Cancel" id="cancelEdit'+userId+'" initEmail="'+initEmail+'" onclick="calcelEditUserEmail(this)"/>');
							lastEditUserEmail=initEmail+"/"+userId;
						}else{
							var lastEmail=lastEditUserEmail.split("/")[0];
							var lastId=lastEditUserEmail.split("/")[1];
							$("#email"+lastId).val(lastEmail);
							$("#email"+lastId).attr("disabled",true);
							$("#cancelEdit"+lastId).parent().html('<input id="edit'+lastId+'" type="button" value="Edit" onclick="editUserEmail(this)"/>');
							
							$("#email"+userId).attr("disabled",false);
							initEmail = $("#email"+userId).attr("value");
							$(a).attr("value","Save");
							$(a).parent().append('<input type="button" value="Cancel" id="cancelEdit'+userId+'" initEmail="'+initEmail+'" onclick="calcelEditUserEmail(this)"/>');
							lastEditUserEmail=initEmail+"/"+userId;
						}
					}else{
						alert("Operate Time Out");
						window.location="index_login.html";
					}
				}
			}
		});
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
						var useremail=$("#email"+userId).val();
						if(valid_email(useremail)==true){
							$.ajax({
								url: strUrl+"EmailServlet?type=5&userId="+userId+"&userEmail="+useremail,
							    type:'POST',
								cache:false,
							    dataType: "text",
							    timeout:10000
							   }).done(function(data){
								   if(data=="true"){
									   $("#email"+userId).attr("disabled",true);
									   //$(a).attr("value","Edit");
									   $(a).parent().html('<input type="button" value="Edit"/>');
									   lastEditUserEmail="";
									   //window.location.href="index_email.html"; 
									   if(isUserEmailSearchModel==true){
										   var characterName=$("#useremailsearch").val();
										   jumpAndRefreshUserEmailWithFilter(currentUserEmailPage, characterName);
									   }else{
										   jumpAndRefreshUserEmail(currentUserEmailPage);
									   }
									   alert("Edit User Email Successfully");
								   }
							   }).fail(function(data){

							  });
						}else{
							document.getElementById("emailinput").value='';
							alert("Please Input A Correct Email");
							return false;
						}	
					}else{
						alert("Operate Time Out");
						window.location="index_login.html";
					}
				}
			}
		});
	}
}

function calcelEditUserEmail(evt){
	var initEmail=$(evt).attr("initEmail");
	var cancelId=($(evt).attr("id")).substr(10);
	$("#email"+cancelId).val(initEmail);
	$("#email"+cancelId).attr("disabled",true);
	$(evt).parent().html('<input id="edit'+cancelId+'" type="button" value="Edit" onclick="editUserEmail(this)"/>');
	lastEditUserEmail="";
}