<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="model.ShowAllDataDto" %>

<%--
ファイル名：detailDisplay 
item_infoテーブルから条件に合致するデータを抽出し、選択した商品の詳細画面(HTML)を出力する
--%>


<html>
  <head>
    <title>商品詳細表示</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/detailDisplay.css" type="text/css">
  </head>
  <body>
     <div class="top-banner">
      <div class="banner-content">
        <h2>商品詳細表示</h2>
        <div class="button-group">
          <a href="ShowAllData" class="cancel-btn">戻る</a>
        </div>
      </div>
    </div>
    <div class="container">
      <table class="surbey_list" border=1>
        <tr bgcolor="#c0c0c0">
          <th class="idData">商品ID</th>
          <th>商品名</th>
          <th>価格</th>
          <th>商品カテゴリ</th>
          <th>公開状況</th>
          <th>商品画像</th>
          <th>商品説明</th>
          <th>発売日時</th>
        </tr>
          <tr>
            <td class="text-right"><c:out value="${DETAIL_DISPLAY.itemId}" /></td> 
            <td><c:out value="${DETAIL_DISPLAY.itemName}" /></td>
            <td class="text-right">${DETAIL_DISPLAY.price}</td>
            <td><c:out value="${DETAIL_DISPLAY.itemCategory}"/></td>
            <td>${DETAIL_DISPLAY.publicStatus}</td>
            <td><c:choose>
              <%-- 画像が存在する場合 (if) --%>
              <c:when test="${not empty DETAIL_DISPLAY.itemImage}">
                <img src="${pageContext.request.contextPath}/img/${DETAIL_DISPLAY.itemImage}" 
                 width="50" height="50" style="object-fit: cover;">
              </c:when>
              <%-- 画像がない場合 (else) --%>
              <c:otherwise>
                <span>画像なし</span>
              </c:otherwise>
            </c:choose></td>
            <td class="col-description"><c:out value="${DETAIL_DISPLAY.itemDescription}"/></td>
            <td class="text-right" name="RELEASE-DATE"><c:out value="${fn:replace(DETAIL_DISPLAY.releaseDate, 'T', ' ')}"/></td><%--datetime型のTの表示を置き換える --%>
          </tr>
      </table>
    </div>
  </body>
</html>