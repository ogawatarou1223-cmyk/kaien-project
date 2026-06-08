<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"      %>
<%@ page import="model.ShowAllDataDto" %>

<%--
ファイル名：ShowAllData 
item_infoテーブルから条件に合致するデータを抽出し、回答一覧画面(HTML)を出力する
--%>

<%
//surveyリストからデータを全件抽出
List<ShowAllDataDto> list = (List<ShowAllDataDto>)request.getAttribute("SHOW_ALL_DATA");
%>

<html>
  <head>
    <title>商品一覧</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ShowAllData.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
  </head>
  <body>
    <%--上部のバナーで登録、キャンセル設定 --%>
    <div class="top-banner">
      <div class="banner-content">
        <h2>商品一覧</h2>
        <div class="button-group"><%--管理者権限あれば更新・削除のためのチェックボックス表示 --%>
          <c:if test="${USER_INFO.role == 1}"><%--管理者権限の識別　セッションにuser_roleの1があれば以下を表示 --%>
            <div class="edit">
              <input type="button" value="新商品登録" onclick="location.href='ItemInsert'" name="ItemInsert" class="banner-btn">
              <input type="button" value="更新画面へ" name="ItemUpdate" class="banner-btn" onclick="submitForm('ItemUpdate')"> 
              <input type="button" value="削除画面へ" name="ItemDelete" id="ItemDelete" class="banner-btn" onclick="submitForm('ItemDelete')">
            </div>
          </c:if>
          <%--ここから下は全アカウント共通表示 --%>
          <a href="ExecuteLogout" class="cancel-btn">ログアウト</a>
        </div>
      </div>
    </div>

    <div class="container">
      <form id="mainForm" action="ItemUpdate" method="post" onsubmit="return validateSelection()">
        <table class="surbey_list" border=1>
          <tr bgcolor="#c0c0c0">
            <c:if test="${USER_INFO.role == 1}">
              <th class="col-edit">更新<br>・<br>削除</th>
            </c:if>
            <th class="toDetail">商品ID<br>・<br>商品詳細ページ</th>
            <th>商品名</th>
            <th>価格</th>
            <th>商品<br>カテゴリ</th>
            <th class="publicData">公開状況</th>
            <th class="imgaeData">商品画像</th>
            <th>商品説明</th>
            <th class="dateData">発売日時</th>
          </tr>
          <c:forEach var="dto" items="${SHOW_ALL_DATA}">
          <tr><%--チェックボックスも管理者権限ありにのみ表示 --%>
            <c:if test="${USER_INFO.role == 1}">
              <td><input type="checkbox" name="selectedData" value="${dto.itemId}"></td>
            </c:if>
            <td class="text-right"><a href="${pageContext.request.contextPath}/DetailDisplay?itemId=${dto.itemId}"><c:out value="${dto.itemId}" /></a></td> 
            <td><c:out value="${dto.itemName}" /></td>
            <td class="text-right">${dto.price}</td>
            <td><c:out value="${dto.itemCategory}"/></td>
            <td>${dto.publicStatus}</td>
            <td><c:choose>
              <%-- 画像が存在する場合 (if) --%>
              <c:when test="${not empty dto.itemImage}">
                <img src="${pageContext.request.contextPath}/img/${dto.itemImage}" 
                 width="50" height="50" style="object-fit: cover;">
              </c:when>
              <%-- 画像がない場合 (else) --%>
              <c:otherwise>
                <span>画像なし</span>
              </c:otherwise>
            </c:choose></td>
            <td class="col-description"><c:out value="${dto.itemDescription}"/></td>
            <td class="text-right" name="RELEASE-DATE"><c:out value="${fn:replace(dto.releaseDate, 'T', ' ')}"/></td><%--datetime型のTの表示を置き換える --%>
          </tr>
          </c:forEach>
        </table>
      </form>
    </div>
       
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/brankCheck-ShowAllData.js"></script>
    <script type="text/javascript">
       function submitForm(actionTarget) {
         var form = document.getElementById('mainForm');
           
           // validateSelection関数が存在するかチェックし、未選択なら処理を中断
           if (typeof validateSelection === 'function' && !validateSelection()) {
             return false;
           }
           
           if (actionTarget === 'ItemDelete') {
             form.action = 'ItemDelete';
           } else {
             form.action = 'ItemUpdate';
           }
           form.submit();
         }
    </script>
  </body>
</html>