//商品更新のHTML用　未入力があったらエラーメッセージ表示
document.addEventListener('DOMContentLoaded', function() {
  // formのIDで取得
  const form = document.getElementById("updateForm");

  if (form) {
    form.addEventListener('submit' ,function(event) {
      // 各要素の取得
      const itemName = document.getElementById("ITEM_NAME").value;
      const price = document.getElementById("PRICE").value;
      const category = document.getElementById("ITEM_CATEGORY").value;
	  //textareaのためtrimを使用
      const itemD = document.getElementById("ITEM_DESCRIPTION").value.trim();
      
      // ラジオボタンの状態
      const publicStatusChecked = document.querySelector('input[name="PUBLIC_STATUS"]:checked');
      
      const regex = /^[1-9][0-9]*$/;
      let errorMsg = "";

      // バリデーション
      if (!itemName || !price || !category || !itemD || !publicStatusChecked) {
        errorMsg = "入力漏れの項目があります。";
      }
	  
	  // 2. 商品名の文字数チェック 
	  else if (itemName.length > 30) {
        errorMsg = "商品名は30文字以内で入力してください。";
	  }
      // 3. 商品説明の文字数チェック 
      else if (itemD.length > 200) {
        errorMsg = "商品説明は200文字以内で入力してください。";
      }
	  
	  //価格がゼロの場合
	   else if (!regex.test(price)) {
        errorMsg = "価格は1以上の半角数字で入力してください。";
      }
	  
	  else {
	          // 桁数が11桁以上の場合は、Javaのint最大値（10桁）を確実に超えるため即エラー
	          if (price.length >= 11) {
	            errorMsg = "価格が上限（2,147,483,647）を超えています。正しい価格を入力してください";
	          } else {
	            // 巨大な数字でも安全に比較できるよう BigInt を使用
	            var priceValue = BigInt(price);
	            var maxLimit = BigInt(2147483647);
	            
	            if (priceValue > maxLimit) {
	              errorMsg = "価格が上限（2,147,483,647）を超えています。正しい価格を入力してください";
	            }
	          }
			 }
	  
	//エラーありの場合
      if (errorMsg !== "") {
        alert(errorMsg);
       // 送信ブロック
	   event.preventDefault();
      }
    });
  }
});