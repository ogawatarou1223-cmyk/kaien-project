//新規会員登録のHTML用　未入力があったらエラーメッセージ表示
document.addEventListener('DOMContentLoaded',function(){

  var form = document.getElementById("regestrationForm");
	if(form){
		form.addEventListener('submit' , function(event){
    var elmUserId   = document.getElementById("USER_ID");
    var elmFName   = document.getElementById("FAMILY_NAME");
    var elmLName   = document.getElementById("LAST_NAME");
    var elmEMail   = document.getElementById("USER_EMAIL");
    var elmPassword = document.getElementById("USER_PASS");
  
    var canSubmit = true;
    if(elmUserId.value == "" || elmFName.value == "" || elmLName.value == "" || elmEMail.value == "" || elmPassword.value == ""){
      alert("入力漏れの項目があります。");
      event.preventDefault();//フォーム送信停止
    }
    return canSubmit;
  });
}

let pass = document.getElementById("USER_PASS");
let chkPass = document.getElementById("chkPass");
if(chkPass && pass){
    chkPass.addEventListener("click",(e)=>{
	
	  e.preventDefault();
      if(pass.type==="password"){
	    pass.type = "text";
		chkPass.textContent = "非表示";
	  }else{
		pass.type = "password";
		chkPass.textContent = "表示";
	  }
    });
  }
});