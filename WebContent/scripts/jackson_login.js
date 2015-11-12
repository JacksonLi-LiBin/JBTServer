/**
 * JacksonLi
 */
var adminToken=window.localStorage.getItem("adminToken");
$(function() {
	document.onkeydown = function(e){ 
	    var ev = document.all? window.event :e;
	    if(ev.keyCode==13) {
	    	userLogin();
	     }
	};
	
	/**
	 * judge weather the administrator has login
	 */
	function validateInput() {
		var flag = 0;
		if (flag == 0) {
			if ($.trim($("#username").val()) == "") {
				flag = 1;
			}
			if (flag == 0) {
				if ($.trim($("#password").val()) == "") {
					flag = 1;
				}
			}
		}
		return flag;
	}
	
	function userLogin(){
		if(validateInput()==0){
			var userName=$("#username").val();
			var password=$("#password").val();
			var sendUrl="";
			if(adminToken==null){
				sendUrl=urlStr+"rest/AdminLogin/login?username="+userName+"&password="+password+"&adminToken=null";
			}else{
				sendUrl=urlStr+"rest/AdminLogin/login?username="+userName+"&password="+password+"&adminToken="+adminToken;
			}
			var dataGet="";
			$.ajax({
				url:sendUrl,
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
						if(dataGet=="already"){
							alert("admin has already login");
						}else if(dataGet=="false"){
							alert("input error");
						}else{
							window.localStorage.setItem("adminToken", dataGet);
							window.localStorage.setItem("adminName", userName);
							window.location="index_backoffice.html";
							//window.open("index_backoffice.html","","height=100,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
						}
					}
				}
			});
		}else{
			//alert("input error");
		}
	}
	
	$("#login").click(function(){
		userLogin();
	});
});