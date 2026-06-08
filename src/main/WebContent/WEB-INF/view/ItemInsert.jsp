<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--jstlの使用を宣言 --%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
ファイル名:ItemInsert.jsp
item_infoテーブルに新商品を追加する
--%>
<html>
  <head>
    <title>新商品登録</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ItemInsert.css" type="text/css">
  </head>
  <body>
  <%--上部のバナーで登録、キャンセル設定 --%>
    <div class="top-banner">
      <div class="banner-content">
        <h2>新商品登録</h2>
        <div class="button-group">
          <a href="ShowAllData" class="cancel-btn">キャンセル</a>
          <input type="submit" value="登録する" form="insertForm" id="ID_SUBMIT" class="submit-btn">
        </div>
      </div>
    </div>
    <%--入力欄表示項目　ID,名前、説明、価格の上限はjsで指定 --%>
    <div class="container">
      <form action="ItemInsert" method="post"  enctype="multipart/form-data" id="insertForm">
        <h1>新商品の登録をお願いします。</h1>
        <label bgcolor="#c0c0c0">商品ID:</label>
          <input type="number" name="ITEM_ID" maxlength="20" id="ITEM_ID" required>
     
        <label bgcolor="#c0c0c0">商品名:</label>
          <input type="text" name="ITEM_NAME" maxlength="20" id="ITEM_NAME" maxlength="30" required value="<c:out value='${param.ITEM_DESCRIPTION}'/>">
      
        <label bgcolor="#c0c0c0">商品説明:</label>
          <textarea name="ITEM_DESCRIPTION" id="ITEM_DESCRIPTION" cols="40" rows="10" maxlength="200" required value="<c:out value='${param.ITEM_DESCRIPTION}'/>"></textarea>
       
        <div class="flex-container">
          <div class="flex-item">
            <label bgcolor="#c0c0c0">価格:</label>
            <div class="price-input-group">
              <input type="number" name="PRICE" id="PRICE" class="text-right" required>
              <span class="unit">円</span>
            </div>
          </div>
        
          <div class="flex-item">
            <label bgcolor="#c0c0c0">商品カテゴリ:</label>
            <select name="ITEM_CATEGORY" id="ITEM_CATEGORY" size="1" required>
              <option value="日用品">日用品</option>
              <option value="衣料品">衣料品</option>
              <option value="食品">食品</option>
            </select>
          </div>
        </div>
     
        <div class="flex-container">
          <div class="flex-item">
            <label bgcolor="#c0c0c0">公開状況:</label>
            <div style="padding-top: 10px;"> <!-- ラジオボタンの高さ調整用 -->
              <input type="radio" name="PUBLIC_STATUS" value="公開中" checked required>公開中 
              <input type="radio" name="PUBLIC_STATUS" value="非公開" required> 非公開
            </div>
          </div>
      
          <div class="flex-item">
            <label bgcolor="#c0c0c0">発売日時:</label>
              <input type="datetime-local" name="RELEASE_DATE" maxlength="30" id="RELEASE_DATE" value="${fn:replace(dto.releaseDate, 'T', ' ')}" required><%--datetime型のTの表示を置き換える --%>
          </div>
        </div>
     
        <label bgcolor="#c0c0c0">商品画像(ファイルアップロード):</label>
          <br>
          <input type="file" name="ITEM_IMAGE" >
          <input type="hidden" name="EXISTING_IMAGE">

      </form>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/brankCheck-insert.js"></script>
  </body>
</html>