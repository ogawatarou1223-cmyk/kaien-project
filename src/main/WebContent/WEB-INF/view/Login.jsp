<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%--
ファイル名：Login.jsp
ログイン用のユーザーデータ入力画面（HTML）を出力する
--%>

<html>
<head>
  <title>ログイン画面</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Login.css" type="text/css">
</head>
<body>
  <h1>ログインしてください</h1>
    <form action="ExecuteLogin" method="post">
      <div class="loginStyle">
        <p>ユーザーID：<br>
          <input type="text" name="USER_ID" maxlength="20" id="ID_USER_ID">
        </p>
        <%--パスワードの表示、非表示の切り替えはjsで指定 --%>
        <p>パスワード:<br>
          <input type="password" name="USER_PASS" maxlength="20" id="ID_PASSWORD">
          <button type="button" id="chkPass">表示</button>
        </p>
        <br>
		<input type="submit" value="ログイン" id="ID_SUBMIT"/>
		<br>
		<input type="submit" value="新規会員登録" formaction="NewMemberRegestration"/>
	  </div>
    </form>
    <script type="text/javascript" src="js/brankChk-showPass_Login.js"></script>
  </body>
</html>