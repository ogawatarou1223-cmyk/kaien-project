//新商品登録のHTML用　未入力があったらエラーメッセージ表示
document.addEventListener('DOMContentLoaded', function() {
  var form = document.getElementById("insertForm");

  if (form) {
	form.addEventListener('submit', function(event) {
      var elmItemId = document.getElementById("ITEM_ID");
      var elmItemName = document.getElementById("ITEM_NAME");
      var elmPrice = document.getElementById("PRICE");
      var elmItemCategory = document.getElementById("ITEM_CATEGORY");
      var elmItemD = document.getElementById("ITEM_DESCRIPTION");
      var elmDate = document.getElementById("RELEASE_DATE");
      var publicStatusChecked = document.querySelector('input[name="PUBLIC_STATUS"]:checked');

      var errorMsg = "";
      var canSubmit = true;
      const regex = /^[1-9][0-9]*$/;
	  var idValue    = parseInt(elmItemId.value, 10);

      // 未入力チェック
      if (elmItemId.value.trim() === "" || elmItemName.value.trim() === "" || elmPrice.value.trim() === "" ||
        elmItemCategory.value.trim() === "" || elmItemD.value.trim() === "" ||
        elmDate.value.trim() === "" || !publicStatusChecked) {
        errorMsg = "入力漏れの項目があります。";
        canSubmit = false;
      } 
	  
	  // 2. 商品名の文字数チェック 
	        else if (elmItemName.length > 30) {
	          errorMsg = "商品名は30文字以内で入力してください。";
	        }
	        // 3. 商品説明の文字数チェック 
	        else if (elmItemD.length > 200) {
	          errorMsg = "商品説明は200文字以内で入力してください。";
	        }
 
      // 価格の形式チェック
      else if (!regex.test(elmPrice.value)) {
        errorMsg = "価格は1以上の半角数字で入力してください。";
        canSubmit = false;
      }
	  
	  //IDの範囲チェック
	  else if(idValue < 0){
		errorMsg = "商品IDに負の数は設定できません";
		canSubmit = false;
				
	}else if(idValue >= 2147483647){
					errorMsg = "IDの数値が上限（2,147,483,647）です。正しい価格を入力してください";
					canSubmit = false;
	  }
	  
	  //価格の範囲チェック
	  else{
		var priceValue = parseInt(elmPrice.value,10);
		
		if(priceValue < 0){
			errorMsg = "価格に負の数は設定できません";
			canSubmit = false;
		}else if(priceValue >= 2147483647){
			errorMsg = "価格が上限（2,147,483,647）です。正しい価格を入力してください";
			canSubmit = false;
		}
	  }

      if (!canSubmit) {
        alert(errorMsg);
        event.preventDefault(); // 送信中止
      }
    });
  }
});