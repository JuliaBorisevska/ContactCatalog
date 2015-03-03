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
    <p class="page-info">Создание контакта</p>
      <form class="contactform" enctype="multipart/form-data" method="post">
      <div class="left">
      	<div class="fieldwrapper">
      		<div id="blanket" style="display:none;"></div>
      		
      		<div id="popUpDivImage" style="display:none;"> 
      			<p class="page-info"><span>Выбор фото</span></p> 
      			<div id="imageFile">
      				<input type="file" name="foto" accept="image/*" size="50">
      			</div>
   				<div class="buttonsdiv" >
					<input type="submit" value="Сохранить" id="savebutton" onclick="popup('popUpDivImage', 200, 400)"/> 
					<input type="submit" value="Отменить" onclick="popup('popUpDivImage', 200, 400)"/>
				</div>
			</div>
			
			<c:choose>
				<c:when test="${ contact.image!=null}" >
					<img src="../${contact.image}" onclick="popup('popUpDivImage', 200, 400)" />
				</c:when>
				<c:otherwise>
					<img src="../images/grey_man.png" onclick="popup('popUpDivImage', 200, 400)" />
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
	
    <div id="popUpDivPhone" style="display:none;">  
    	<p class="page-info"><span>Создание/редактирование телефона</span></p>
		<div class="fieldwrapper">
			<label for="countryCode" class="styled">Код страны:</label>
			<div class="thefield">
				<input type="text" id="countryCode" value="" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="operatorCode" class="styled">Код оператора:</label>
			<div class="thefield">
				<input type="text" id="operatorCode" value="" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="number" class="styled">Телефонный номер:</label>
			<div class="thefield">
				<input type="text" id="number" value="" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="phoneType" class="styled">Пол:</label>
			<div class="thefield">
				<ul>
					<c:forEach var="elem" items="${helper.takePhoneTypes()}" varStatus="status">
					<li><input type="radio" name="phoneType" value="${elem.id }"/>${elem.title}</li>
					</c:forEach>
				</ul>
			</div>
		</div> 
		<div class="fieldwrapper">
			<label for="comment" class="styled">Коментарий:</label>
			<div class="thefield">
				<textarea name="phoneComment" ></textarea>
			</div>
		</div>
   		<div class="buttonsdiv" >
			<input type="submit" value="Сохранить" id="savebutton" onclick="popup('popUpDivPhone', 450, 600)"/> 
			<input type="submit" value="Отменить" onclick="popup('popUpDivPhone', 450, 600)"/>
		</div>		
	</div>
	
		<div id="boxtab-blue">
			<ul>
				<li class="active"><a href="#"><span>Удалить</span></a></li>
				<li class="last"><a href="#" onclick="popup('popUpDivPhone', 450, 600)"><span>Создать</span></a></li>
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
                            	<a href="${pageContext.request.contextPath}/editContact.do?id=${elem.id}" class="name" onclick="popup('popUpDivPhone', 450, 600)">+${elem.countryCode} (${elem.operatorCode}) ${elem.basicNumber}</a>
                  
                            </td>
                            <td>
                                ${elem.type} 
                            </td>
                            <td>
                                ${elem.userComment}
                            </td>
                    </tr>
                </c:forEach>
                <tr>
                            <td>
                            	<input type="checkbox">
                            </td>
                            <td>
                            	<a href="#" class="name" onclick="popup('popUpDivPhone', 450, 600)">+375 (29) 2886668</a>
                  
                            </td>
                            <td>
                                мобильный
                            </td>
                            <td>
                                
                            </td>
                    </tr>
            </table>
		<br/><br/><br/>
		
		<div id="popUpDivAttach" style="display:none;">  
    		<a href="#" onclick="popup('popUpDivAttach', 400, 600)">Закрыть PopUp</a><br />
    		<input type="text"  value="" />		
		</div>
		
		<div id="boxtab-blue">
			<ul>
				<li class="active"><a href="#"><span>Удалить</span></a></li>
				<li class="last"><a href="#" onclick="popup('popUpDivAttach', 400, 600)"><span>Создать</span></a></li>
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
                            	<a href="${pageContext.request.contextPath}/editAttachment.do?id=${elem.id}" class="name" onclick="popup('popUpDivAttach', 400, 600)">${elem.title}</a>
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
