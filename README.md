初作成のWebアプリです。まだまだ至らない点も多いと思いますがよろしくお願いいたします。  

## ■ 雑貨店向け　商品マスタメンテナンス
<img width="1792" height="889" alt="スクリーンショット 2026-06-11 141114" src="https://github.com/user-attachments/assets/e9f55496-e72c-4a41-bdfc-08497554d927" />  
<img width="1811" height="927" alt="Image" src="https://github.com/user-attachments/assets/c30ce9ff-f4fc-4ad1-b9e9-3f986652a6ca" />  

## ■ アプリURL
http://localhost:8080/kaien_project/Login

## ■ サービス概要
架空の雑貨店の商品管理アプリケーションです。

## ■ 作成した経緯  
javaの学習をする中で、一つの成果を示すために、就労移行支援の講師から業務依頼書という形で依頼を受けて作成しました。また、その中でよりユーザーに使いやすいシステムになるように、当初の依頼にプラスアルファで商品詳細ページの追加等も行いました。

## ■ 主なページと機能

ログイン画面：http://localhost:8080/kaien_project/Login  
→一般的なログイン機能です。ユーザーの不正アクセス防止のためユーザー情報がセッション上になければこちらの画面に遷移するようにしています。また、ログイン情報の中に管理者権限を設けており、ある場合にのみ後述する商品の登録、更新、削除が行える形になっています。こちらから商品一覧画面及び新規会員登録画面へアクセスできます。  

新規会員登録画面：http://localhost:8080/kaien_project/NewMemberRegestration  
→新しくログインができるユーザーをDBに登録する機能です。管理者権限を付与することはできません。現状管理者権限はDBのテーブルから直接付与する方法のみになっています。  

商品一覧画面：http://localhost:8080/kaien_project/ShowAllData  
→DBに登録している商品の情報をテーブルの形式で表示させるページです。画面上部のバナーにはログアウト用のボタンを設置しています。管理者ユーザーのみテーブルの一番左にチェック欄と、上部バナーに新商品登録、商品情報更新、削除のボタンが表示されます。また、商品説明の欄はある程度の長さで...と続くようになっており、全体像は商品詳細ページで見られるようになっています。  

商品詳細画面：http://localhost:8080/kaien_project/DetailDisplay  
→商品一覧画面にてクリックしたIDの商品のデータを大きく表示させ、...で区切られていた商品説明の全文も表示させる画面となっています。

#### 以下より管理者ユーザーのみアクセス可能になります。  
新商品登録：http://localhost:8080/kaien_project/ItemInsert  
→商品情報を入力し、ＤＢに新規に登録する機能を持ちます。  

商品情報更新画面：http://localhost:8080/kaien_project/ItemUpdate  
→DBに既に登録されている情報を更新し、一覧画面に反映する機能を持ちます。  

商品情報削除画面：http://localhost:8080/kaien_project/ItemDelete  
→DBに登録されている商品情報を削除する機能を持ちます。

## ■ 使用技術

#### バックエンド
- Java openjdk version "17.0.17"

#### フロントエンド
- HTML(JSTL)
- CSS
- JavaScript（jQuery）

#### データベース
- mysql  Ver 8.0.45

#### ローカルサーバー  
- tomcat 10_java 17

## ■ 画面遷移図
<img width="1805" height="441" alt="Image" src="https://github.com/user-attachments/assets/ffc0dfa8-be88-4c17-b2b2-53c372631b71" />

## ■ DB設計図
<img width="1606" height="720" alt="Image" src="https://github.com/user-attachments/assets/cf39746b-29ce-48d3-b323-3d78132bce93" />
