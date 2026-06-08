<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%--
ファイル名:newMemberRegestraition.jsp
新規会員登録用ページ(HTML)を表示
--%>
<html>
  <head>
  <meta charset="UTF-8">
  <title>新規会員登録</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newMemberRegestration.css" type="text/css">
  </head>
  <body>
    <%--上部のバナーで登録、キャンセル設定 --%>
    <div class="top-banner">
      <div class="banner-content">
        <h2>新規会員登録</h2>
        <div class="button-group">
          <div class="edit">
            <input type="submit" value="登録" class="banner-btn" id="ID_SUBMIT" method="post" form="regestrationForm"> 
          </div>
          <a href="Login" class="cancel-btn">キャンセル</a>
        </div>
      </div>
    </div>
    
    <h1>会員登録に必要な情報の入力をお願いします。</h1>
    
    <div class="container">          
      <form action="SaveUserInfo" id="regestrationForm" name="regestrationForm" method="post" onsubmit="return validateForm()">
      
      <%--必要情報の入力 --%>
      <div class="id-container">
        <div class="id-item">
        <label>ユーザーID:</label><br>
          <input type="text" name="USER_ID" maxlength="20" id="USER_ID" required>
        <p><small>※このサイトでのみ使われるIDになります。</small></p>
        </div>
        </div>
        <div class="name-flex-container">
          <div class="name-flex-item">
            <label>姓:</label>
              <input type="text" name="FAMILY_NAME" maxlength="20" id="FAMILY_NAME" required>
          </div>
          <div class="name-flex-item">
            <label>名:</label>
              <input type="text" name="LAST_NAME" maxlength="20" id="LAST_NAME" required>
          </div>
        </div>
        <label>メールアドレス:</label><br>
          <input type="email" name="USER_EMAIL" maxlength="30" id="USER_EMAIL" required><br>
        <small>※会社からの連絡はこちらに送られます。</small><br>
    	<%--パスワードは表示、非表示切り替えをjsで指定 --%>
        <label>パスワード（20文字以内）:</label>
        <div class="password-group">
          <input type="password" name="USER_PASS" maxlength="20" id="USER_PASS" required>
          <button type="button" id="chkPass">表示</button>
        </div>
      </form>
    </div>
    <script type="text/javascript" src="/kaien_project/js/brankCheck-newMember.js"></script>
  </body>
</html>