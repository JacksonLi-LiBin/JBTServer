/**
 * JacksonLi
 */
$(function(){
	//push maslul no 
	$("#sendMaslulNo").click(function(){
		if($.trim($("#idInput").val()).length!=0&&$.trim($("#cycleNumberInput").val()).length!=0&&$.trim($("#courseNumberInput").val()).length!=0&&$.trim($("#courseNameInput").val()).length!=0){
			$.post(urlStr+"rest/CommonMethods/pushMaslulNo",
					JSON.stringify({
						"username" : "jackson",
						"password" : "123456",
						"data" : [
									{
										"ID" : ""+$("#idInput").val(),
										"CycleNumber" : parseInt($("#cycleNumberInput").val()),
										"CourseNumber" : parseInt($("#courseNumberInput").val()),
										"CourseName" : ""+$("#courseNameInput").val()
									}
								]			
					}), function(data) {
					alert("Data Loaded: " + data);	
			});
		}else{
			alert("please input your push message");
		}
	});
	
	//push job
	$("#sendJob").click(function(){
		if($.trim($("#jobIdinput").val()).length!=0&&$.trim($("#titleInput").val()).length!=0&&$.trim($("#companyInput").val()).length!=0&&$.trim($("#areaInput").val()).length!=0&&$.trim($("#domainInput").val()).length!=0&&$.trim($("#experienceInput").val()).length!=0&&$.trim($("#textInput").val()).length!=0&&$.trim($("#requirementsInput").val()).length!=0&&$.trim($("#emailInput").val()).length!=0){
			$.post(urlStr+"rest/CommonMethods/pushJob",
					JSON.stringify({
						"username" : "jackson",
						"password" : "123456",
						"data" : [
									{
										"ID" : parseInt($("#jobIdinput").val()),
										"Title" : $("#titleInput").val(),
										"Company" : $("#companyInput").val(),
										"Area" : ""+$("#areaInput").val(),
										"Domain" : $("#domainInput").val(),
										"Experience" : $("#experienceInput").val(),
										"Text" : $("#textInput").val(),
										"Requirements" : ""+$("#requirementsInput").val(),
										"Email" : $("#emailInput").val()
									}
								]			
					}), function(data) {
					alert("Data Loaded: " + data);	
			});
		}else{
			alert("please input your push message");
		}
	});
	
	//push class
	$("#sendClass").click(function(){
		if($.trim($("#classIdInput").val()).length!=0&&$.trim($("#classNamenput").val()).length!=0){
			$.post(urlStr+"rest/CommonMethods/pushClass",
					JSON.stringify({
						"username" : "jackson",
						"password" : "123456",
						"data" : [
									{
										"ClassId" : parseInt($("#classIdInput").val()),
										"ClassName" : $("#classNamenput").val()
									}
								]			
					}), function(data) {
					alert("Data Loaded: " + data);	
			});
		}else{
			alert("please input your push message");
		}
	});
	
	//push grade
	$("#sendGrade").click(function(){
		if($.trim($("#gradeIdInput").val()).length!=0&&$.trim($("#gradeNameInput").val()).length!=0){
			$.post(urlStr+"rest/CommonMethods/pushGrade",
					JSON.stringify({
						"username" : "jackson",
						"password" : "123456",
						"data" : [
									{
										"GradeId" : parseInt($("#gradeIdInput").val()),
										"GradeName" : $("#gradeNameInput").val()
									}
								]			
					}), function(data) {
					alert("Data Loaded: " + data);	
			});
		}else{
			alert("please input your push message");
		}
	});
});