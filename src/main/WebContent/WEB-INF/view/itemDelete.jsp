<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"      %>
<%@ page import="model.ShowAllDataDto" %>

<%--
ファイル名:itemDelete
item_infoテーブルから条件に合致するデータを抽出し、DBから削除する。確認用画面
--%>

<%
//surveyリストからデータを全件抽出
List<ShowAllDataDto> list = (List<ShowAllDataDto>)request.getAttribute("ALL_SURVEY_LIST");

%>

<html>
  <head>
    <title>商品削除</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ItemDelete.css" type="text/css">
  </head>
  <body>
    <%--更新・キャンセルのバナー表示 --%>
      <div class="top-banner">
        <div class="banner-content">
          <h2>商品削除確認</h2>
        <div class="button-group">
          <a href="ShowAllData" class="cancel-btn">キャンセル</a>
          <input type="submit" value="削除する" form="deleteForm" id="ID_SUBMIT" class="submit-btn">
        </div>
      </div>
    </div>
    <%--削除する商品の詳細表示 --%>
    <div class="container">
      <h2>以下の商品を削除します。よろしいですか？</h2>
      <form action="ExecuteDelete" method="post" id="deleteForm">
        <table class="surbey_list" border=1>
          <tr bgcolor="#c0c0c0">
            <th>商品ID</th>
            <th>商品名</th>
            <th>価格</th>
            <th>商品カテゴリ</th>
            <th>商品画像</th>
            <th>商品説明</th>
            <th>発売日時</th>
          </tr>
        
          <c:forEach var="dto" items="${DELETE_LIST}"> 
        
          <tr>
            <td>
              <c:out value="${dto.itemId}" />
              <%-- 削除処理(ExecuteDelete)に送るための隠しフィールド --%>
              <input type="hidden" name="deleteIds" value="${dto.itemId}">
            </td>
            <td>
              <c:out value="${dto.itemName}" />
            </td>
            <td class="text-right">
              ${dto.price}
            </td>
            <td>
              <c:out value="${dto.itemCategory}"/>
            </td>
            <%--画像の有無で分岐 --%>
            <td>
              <c:choose>
                <c:when test="${not empty dto.itemImage}">
                  <img src="${pageContext.request.contextPath}/img/${dto.itemImage}" width="50">
                </c:when>
                <c:otherwise>画像なし</c:otherwise>
              </c:choose>
            </td>
            <td>
                <c:out value="${dto.itemDescription}"/>
            </td>
            <td>
              <c:out value="${fn:replace(dto.releaseDate, 'T', ' ')}"/><%--datetime型のTの表示を置き換える --%>
            </td>
          </tr>
          </c:forEach>
        </table>
      </form>
    </div>
  </body>
</html>