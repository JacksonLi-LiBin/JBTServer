/**
 * JacksonLi
 */
//get parameter
window.queryString = new QeryStringParser();

var id = queryString.getParam("id");
var pwdToken = queryString.getParam("cpwdToken");
$(function() {
	if($.trim(id).length>0&&$.trim(pwdToken).length>0){
		$("#id").val(id);
		$("#pwdToken").val(pwdToken);
		$("#submit").click(function() {
			if (check_new_password()) {
				$.get(urlStr + "rest/CommonMethods/forgetPassword", {
					userId : $("#id").val(),
					newPwd : $("#new_password").val(),
					pwdToken:$("#pwdToken").val()
				}, function(data) {
					if (data == "true") {
						//change password success
						alert("סיסמתך שונתה בהצלחה!");
						window.location.href = "index_change_password.html?id="+id+"&cpwdToken="+pwdToken;
					} else if(data=="timeout"){
						//change password failed
						alert("שינוי כישלון סיסמא!");
						window.location.href = "index_change_password.html?id="+id+"&cpwdToken="+pwdToken;
					}else if(data=="false"){
						alert("שינוי כישלון סיסמא!");
						window.location.href = "index_change_password.html?id="+id+"&cpwdToken="+pwdToken;
					}
				});
			}
		});
	}
});

/**
 * validate your input
 * @returns {Boolean}
 */
function check_new_password() {
	var new_password = $("#new_password").val();
	var retype_new_password = $("#retype_new_password").val();
	if ($.trim(new_password) != "" && $.trim(retype_new_password) != "") {
		if ($.trim(new_password).length >= 8) {
			if (new_password != retype_new_password) {
				document.getElementById("retype_new_password").style.borderColor = "#F01F40";
				alert("סיסמאות אינן תואמות!");
				return false;
			} else {
				document.getElementById("retype_new_password").style.borderColor = "#ABADB3";
				return true;
			}
		} else {
			alert("אורך סיסמא של 8-12");
			return false;
		}
	} else {
		alert("להיכנס！");
	}
}