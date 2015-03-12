<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
</head>
<body onLoad="createPageLinks('${pageContext.request.contextPath}',${count}, ${pageNumber}); setCheckBoxes() ">
<div class="main">
  <c:import url="jsp/header.jsp" />
  <div class="clr"></div>
  <div class="content">
    <div class="content_resize">
    <p class="page-info">Список контактов</p>
      <div class="mainbar">
        <div class="article">
          <div class="clr"></div>
          <table>
                <tr>
                    <th>&nbsp;</th>
                    <th>
                        Полное имя
                    </th>
                    <th>
                        Дата рождения
                    </th>
                    <th>
                        Адрес
                    </th>
                    <th>
                        Место работы
                    </th>
                </tr>
                <c:forEach var="elem" items="${contacts}" varStatus="status">
                    <tr>
                            <td>
                            	<input type="checkbox" id="row" name="row" value="${elem.id}">
                            </td>
                            <td>
                            	<a href="${pageContext.request.contextPath}/editContact.do?id=${elem.id}" class="name" id="name">${elem.lastName} ${elem.firstName} ${elem.middleName}</a>
                            </td>
                            <td>
                                ${elem.birthDate} 
                            </td>
                            <td>
                                ${elem.address} 
                            </td>
                            <td>
                                ${elem.company} 
                            </td>
                    </tr>
                </c:forEach>
            </table>
		</div>
        <div id="pageart">
          <div id="pageLabel">Страницы: </div>
          <div id="buttons">
          </div>
        </div>
      </div>
      <div class="sidebar">
        <div class="gadget">
          <form action="deleteOrSendMail.do" method="post">
          <input type="hidden" id="checkedRows" name="checkedRows" value="${idString}">
          <ul class="sb_menu">
            <li>
            	<a href="jsp/contact.jsp">Создать контакт</a>
            </li>
            <li>
            	<input type="submit" name="delete" value="Удалить контакты" onclick="getIdString()"/>
            </li>
            <li><a href="jsp/search.jsp">Найти контакты</a></li>
            <li>
            	<input type="submit" name="send" value="Отправить Email" onclick="getIdString()"/>
            </li>
          </ul>
          </form>
        </div>
      </div>
      <div class="clr"></div>
    </div>
  </div>
  <c:import url="jsp/footer.jsp" />
</div>
<script src="${pageContext.request.contextPath}/js/navigation.js"  type="text/javascript"></script>
</body>
</html>
