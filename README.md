初作成のWebアプリです。
 タイトル：kaien-project 概要：架空の商店の商品管理を目的としたWebアプリケーションになります。 　
 ＃＃＃サンプル画像掲載、後から 
 URL:https://github.com/ogawatarou1223-cmyk/kaien-project/tree/main
 使用技術： 
 	・java version 17 　
 	・eclipse IDE 
 	・Tomcat 10 java 17 
 	・SQL 　
 	・MySQL 
 	・A5:SQL Mk-2 
 	・html 　
 	・jstl 
 	・javascript 
 	・css 
 各フォルダの分類、役割：
 	・controller：同名jspファイル及び同名DAOを制御、呼び出し
 	・model：同名jspからのリクエストに対してDBを操作、呼び出し
 	・view：ユーザーが直接操作する画面の表示
 	・htmls：各リクエスト送信に対する成功、エラー発生の通知用画面の表示
 	・js：viewに存在するファイルの画面遷移を伴わないアクション（必須項目未入力に対する警告など）の制御
 	・css：viewに存在するファイルのUI調整
 機能一覧 
 	・ユーザー登録、ログイン機能 
 	・商品一覧閲覧 　
 	・商品詳細閲覧 
 	・ログアウト機能 
 	・以下は管理ユーザーのみ 　
		・新商品登録 
		・商品情報編集、更新 
		・商品情報削除 
テスト ＃＃＃書き方どうする？