//Login.javaのHTML用　未入力があったらエラーメッセージ表示
var elmSubmit = document.getElementById("ID_SUBMIT");
elmSubmit.onclick = function(){
  var elmUserId   = document.getElementById("ID_USER_ID");
  var elmPassword = document.getElementById("ID_PASSWORD");
  var canSubmit = true;
  if(elmUserId.value == "" || elmPassword.value == ""){
    alert("入力漏れの項目があります。");
    canSubmit = false;
  }
  return canSubmit;
}

let pass = document.getElementById("ID_PASSWORD");
let chkPass = document.getElementById("chkPass");
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
