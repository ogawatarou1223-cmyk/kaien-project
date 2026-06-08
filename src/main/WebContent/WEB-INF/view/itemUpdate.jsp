<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"      %>
<%@ page import="model.ShowAllDataDto" %>

<%--
ファイル名：itemUpdate 
item_infoテーブルから条件に合致するデータを抽出し、指定したカラムのデータを更新する
--%>

<html>
  <head>
    <title>商品更新</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ItemUpdate.css" type="text/css">
  </head>
  <body>
    <h2>更新内容入力</h2>
    <c:forEach var="d" items="${SHOW_ALL_DATA}">
      <c:if test="${d.itemId == paramValues.selectedData[0]}">
        <c:set var="updateDto" value="${d}"/>
      </c:if>
    </c:forEach>
    
    <c:choose>
      <c:when test="${not empty updateDto}">
      <%--更新・キャンセルのバナー表示 --%>
        <div class="top-banner">
          <div class="banner-content">
            <h2>商品更新</h2>
            <div class="button-group">
              <a href="ShowAllData" class="cancel-btn">キャンセル</a>
              <input type="submit" value="更新する" form="updateForm" id="ID_SUBMIT" class="submit-btn">
            </div>
          </div>
        </div>
         
        <div class="container">          
          <form action="ItemUpdate" id="updateForm" name="updateForm" method="post" enctype="multipart/form-data">
          
	        <input type="hidden" name="ITEM_ID" value="${updateDto.itemId}">	  
            <label bgcolor="#c0c0c0">商品ID</label>
              <p class="id-display"><c:out value="${updateDto.itemId }"/></p><!-- IDは書き換えられないように表示のみ  -->
         <%--以下入力用フォーム　ID,名前、説明、価格の上限はjsで指定 --%>
            <label>商品名</label>
              <input type="text" name="ITEM_NAME" id="ITEM_NAME" maxlength="30" value="<c:out value='${updateDto.itemName}'/>">
           
            <label bgcolor="#c0c0c0">商品説明</label>
              <textarea name="ITEM_DESCRIPTION" id="ITEM_DESCRIPTION" cols="40" rows="10" maxlength="200"><c:out value="${updateDto.itemDescription}" /></textarea>
           
            <div class="flex-container">
              <div class="flex-item">
                <label bgcolor="#c0c0c0">価格</label>
                <div class="price-input-group">
                  <input type="number" name="PRICE" id="PRICE" value="${updateDto.price}" class="text-right">
                  <span class="unit">円</span>
                </div>
              </div>
         
              <div class="flex-item">
                <label bgcolor="#c0c0c0">商品カテゴリー</label>
                <select name="ITEM_CATEGORY" id="ITEM_CATEGORY" size="1" required>
                  <option value="日用品" ${updateDto.itemCategory == '日用品' ? 'selected' : ''}>日用品</option>
                  <option value="衣料品" ${updateDto.itemCategory == '衣料品' ? 'selected' : ''}>衣料品</option>
                  <option value="食品" ${updateDto.itemCategory == '食品' ? 'selected' : ''}>食品</option>
                </select>
              </div>
            </div>
         
            <div class="flex-container">
              <div class="flex-item">
                <label bgcolor="#c0c0c0">公開状況</label>
                <div style="padding-top: 10px;"> <!-- ラジオボタンの高さ調整用 -->
                  <input type="radio" name="PUBLIC_STATUS" value="公開中" ${updateDto.publicStatus == '公開中' ? 'checked' : ''} required>公開中 
                  <input type="radio" name="PUBLIC_STATUS" value="非公開" ${updateDto.publicStatus == '非公開' ? 'checked' : ''} required> 非公開
                </div>
              </div>
         
              <div class="flex-item">
                <label bgcolor="#c0c0c0">発売日時</label>
                  <input type="datetime-local" name="RELEASE_DATE" value="${updateDto.releaseDate}">
              </div>
            </div>
         
            <label bgcolor="#c0c0c0">商品画像ファイル</label>
              <br>
              <input type="file" name="ITEM_IMAGE" id="ITEM_IMAGE">
              <input type="hidden" name="EXISTING_IMAGE" value="<c:out value='${updateDto.itemImage}' />">
           
          </form>
        </div>
        <%--何も選択されていない時用　ShowAllDataのjsでも対策済み --%>
      </c:when>
      <c:otherwise>
        <p>編集するデータが選択されていません。</p>
          <a href="ShowAllData">戻る</a>
      </c:otherwise>
    </c:choose>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ItemUpdate-blankChk.js"></script>
  </body>
</html>