<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<link href="../css/edit.css" rel="stylesheet" type="text/css" />
<script src="../js/popup.js"  type="text/javascript"></script>
</head>
<body>
<jsp:useBean id="helper" scope="page" class="com.itechart.contactcatalog.helper.ViewHelper" />
<div class="main">
  <c:import url="header.jsp" />
  <div class="clr"></div>
  <div class="content">
    <div class="content_resize">
    <p class="page-info"><span>Создание контакта</span></p>
      <form class="contactform" enctype="multipart/form-data" method="post">
      <div class="left">
      	<div class="fieldwrapper">
      		<div id="blanket" style="display:none;"></div>
      		<div id="popUpDivImage" style="display:none;">  
    			<a href="#" onclick="popup('popUpDivImage')">Закрыть PopUp</a>
    			<p><input type="file" name="f">
   				<input type="submit" value="Отправить"></p>
			</div>
			<c:choose>
				<c:when test="${ contact.image!=null}" >
					<img src="../${contact.image}" onclick="popup('popUpDivImage')" />
				</c:when>
				<c:otherwise>
					<img src="../images/grey_man.png" onclick="popup('popUpDivImage')" />
				</c:otherwise>
			</c:choose>
      	</div>
		<div class="fieldwrapper">
			<label for="firstname" class="styled">Имя:</label>
			<div class="thefield">
				<input type="text" id="firstname" value="${contact.firstName}" />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="lastname" class="styled">Фамилия:</label>
			<div class="thefield">
				<input type="text" id="lastname" value="${contact.lastName}" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="midlename" class="styled">Отчество:</label>
			<div class="thefield">
				<input type="text" id="midlename" value="${contact.middleName}" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="year" class="styled">Дата рождения:</label>
			<div class="thefield">
				<input type="text" id="year" value="${contact.birthDate}" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="sex" class="styled">Пол:</label>
			<div class="thefield">
				<ul>
					<c:forEach var="elem" items="${helper.takeSexList()}" varStatus="status">
					<li><input type="radio" name="sex" value="${elem.id }"/>${elem.title }</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="citizenship" class="styled">Гражданство:</label>
			<div class="thefield">
				<input type="text" id="email" value="${contact.citizenship}" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="matrial" class="styled">Семейное положение:</label>
			<div class="thefield">
			<br/>
				<select id="matrial">
					<c:forEach var="elem" items="${helper.takeMaritalStatusList()}" varStatus="status">
						<option value="${elem.id}">${elem.title}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="site" class="styled">Сайт:</label>
			<div class="thefield">
				<input type="text" id="site" value="${contact.website}" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="email" class="styled">Email:</label>
			<div class="thefield">
				<input type="text" id="email" value="${contact.email}" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="job" class="styled">Место работы:</label>
			<div class="thefield">
				<input type="text" id="job" value="${contact.company}" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="address" class="styled">Адрес:</label>
			<div class="thefield">
				<label for="country">Страна:</label><br />
				<input type="text" id="country" value="${contact.address.country}" /><br />
				<label for="town">Город:</label><br />
				<input type="text" id="town" value="${contact.address.town}" /><br />
				<label for="street">Улица:</label><br />
				<input type="text" id="street" value="${contact.address.street}" /><br />
				<label for="house">Дом:</label><br />
				<input type="text" id="house" value="${contact.address.house}" /><br />
				<label for="flat">Квартира:</label><br />
				<input type="text" id="flat" value="${contact.address.flat}" /><br />
				<label for="flat">Index:</label><br />
				<input type="text" id="index" value="${contact.address.indexValue}" /><br />
			</div>
		</div>
		
	</div>
	<div class="right">
	<!-- <div id="blanket" style="display:none;"></div> -->
    <div id="popUpDivPhone" style="display:none;">  
    	<a href="#" onclick="popup('popUpDivPhone')">Закрыть PopUp</a><br />
    	<input type="text"  value="" />		
	</div>
		<div id="boxtab-blue">
			<ul>
				<li class="first"><a href="#" ><span>Удалить</span></a></li>
				<li class="active"><a href="#" onclick="popup('popUpDivPhone')"><span>Редактировать</span></a></li>
				<li class="last"><a href="#" onclick="popup('popUpDivPhone')"><span>Создать</span></a></li>
			</ul>
		</div>
		<p class="table-info"><span>Список контактных телефонов</span></p>
          <table>
                <tr>
                    <th>&nbsp;</th>
                    <th>
                        Полный номер
                    </th>
                    <th>
                        Описание номера
                    </th>
                    <th>
                        Коментарий
                    </th>
                </tr>
                <c:forEach var="elem" items="${contact.phones}" varStatus="status">
                    <tr>
                            <td>
                            	<input type="checkbox">
                            </td>
                            <td>
                                +${elem.countryCode} (${elem.operatorCode}) ${elem.basicNumber}
                            </td>
                            <td>
                                ${elem.type} 
                            </td>
                            <td>
                                ${elem.userComment}
                            </td>
                    </tr>
                </c:forEach>
            </table>
		<br/><br/><br/>
		<div id="boxtab-blue">
			<ul>
				<li class="first"><a href="#"><span>Удалить</span></a></li>
				<li class="active"><a href="#"><span>Редактировать</span></a></li>
				<li class="last"><a href="#"><span>Создать</span></a></li>
			</ul>
		</div>
		<p class="table-info"><span>Список присоединений</span></p>
          <table>
                <tr>
                    <th>&nbsp;</th>
                    <th>
                        Имя файла
                    </th>
                    <th>
                        Дата загрузки
                    </th>
                    <th>
                        Коментарий
                    </th>
                </tr>
                <c:forEach var="elem" items="${contact.attachments}" varStatus="status">
                    <tr>
                            <td>
                            	<input type="checkbox">
                            </td>
                            <td>
                                ${elem.tirle}
                            </td>
                            <td>
                                ${elem.uploads} 
                            </td>
                            <td>
                                ${elem.userComment} 
                            </td>
                    </tr>
                </c:forEach>
            </table>
	</div>
	<div class="clr"></div>
	<div class="buttonsdiv" >
			<input type="submit" value="Сохранить" id="savebutton" /> 
			<input type="reset" value="Отменить" />
		</div>
	<br/>
</form>
      <div class="clr"></div>
    </div>
  </div>
  <c:import url="footer.jsp" />
</div>

</body>
</html>
