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
<body>
<div class="main">
  <c:import url="jsp/header.jsp" />
  <div class="clr"></div>
  <div class="content">
    <div class="content_resize">
    <p class="page-info"><span>Список контактов</span></p>
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
                            	<input type="checkbox">
                            </td>
                            <td>
                                <input type="submit" name="${elem.id}" value="${elem.lastName} ${elem.firstName} ${elem.middleName} " class="name"/>
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
        <div class="pageart">
          <p>Page 1 of 2 <span class="butons"><a href="#">3</a><a href="#">2</a> <a href="#" class="active">1</a></span></p>
        </div>
      </div>
      <div class="sidebar">
        <div class="gadget">
          <ul class="sb_menu">
            <li>
            	<form action="createContact.do" method="post">
            		<input type="submit" name="create" value="Создать контакт" />
            	</form>
            </li>
            <li>
            	<form action="./ContactServlet" method="post">
            		<input type="submit" name="delete" value="Удалить контакт" />
            	</form>
            </li>
            <li><input type="submit" name="find" value="Найти контакт" /></li>
            <li><input type="submit" name="send" value="Отправить Email" /></li>
          </ul>
        </div>
      </div>
      <div class="clr"></div>
    </div>
  </div>
  <c:import url="jsp/footer.jsp" />
</div>

</body>
</html>
