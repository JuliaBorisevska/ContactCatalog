<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/edit.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/js/popup.js"  type="text/javascript"></script>
</head>
<body onLoad="fillContact('${contact.sex.id}', '${contact.maritalStatus.id}' ); convertDate() ">
<jsp:useBean id="helper" scope="page" class="com.itechart.contactcatalog.helper.ViewHelper" />
<div class="main">
  <c:import url="header.jsp" />
  <div class="clr"></div>
  <div class="content">
    <div class="content_resize">
    <p class="page-info">Создание/редактирование контакта</p>
      <form class="contactform"  name="contactForm" action="${pageContext.request.contextPath}/changeContact.do" enctype="multipart/form-data" method="post">
      <input type="hidden" name="contactId" value="${contact.id}" />
      <input type="hidden" name="image" value="${contact.image}" />
      <div class="left">
      	<div class="fieldwrapper">
      		<div id="blanket" style="display:none;"></div>
 
      		<div id="popUpDivImage" style="display:none;"> 
      			<p class="page-info"><span>Выбор фото</span></p> 
      			<div id="imageFile">
      				<input type="file" name="photo" id="photo" accept="image/*" size="50">
      			</div>
   				<div class="buttonsdiv" >
   					<a href="#" onclick="popup('popUpDivImage', 200, 400)">Сохранить</a>
					<a href="#" onclick="popup('popUpDivImage', 200, 400); clearPhoto()">Отменить</a>
				</div>
			</div>
			
			<c:choose>
				<c:when test="${contact.image!=null}" >
					<img src="${pageContext.request.contextPath}/images/${contact.image}" onclick="popup('popUpDivImage', 200, 400)" />
				</c:when>
				<c:otherwise>
					<img src="${pageContext.request.contextPath}/images/grey_man.png" onclick="popup('popUpDivImage', 200, 400)" />
				</c:otherwise>
			</c:choose>
      	</div>
		<div class="fieldwrapper">
			<label for="firstname" class="styled">Имя:</label>
			<div class="thefield">
				<input type="text" id="firstname" name="firstname" value="${contact.firstName}" />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="lastname" class="styled">Фамилия:</label>
			<div class="thefield">
				<input type="text" id="lastname" name="lastname" value="${contact.lastName}" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="middlename" class="styled">Отчество:</label>
			<div class="thefield">
				<input type="text" id="middlename" name="middlename" value="${contact.middleName}" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="year" class="styled">Дата рождения:</label>
			<div class="thefield">
				<input type="text" id="year" name="year" value="${contact.birthDate}" /><br />
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
				<input type="text" id="citizenship" name="citizenship" value="${contact.citizenship}" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="matrial" class="styled">Семейное положение:</label>
			<div class="thefield">
			<br/>
				<select id="matrial" name="status">
					<c:forEach var="elem" items="${helper.takeMaritalStatusList()}" varStatus="status">
						<option value="${elem.id}">${elem.title}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="site" class="styled">Сайт:</label>
			<div class="thefield">
				<input type="text" id="site" name="site" value="${contact.website}" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="email" class="styled">Email:</label>
			<div class="thefield">
				<input type="text" id="email" name="email" value="${contact.email}" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="job" class="styled">Место работы:</label>
			<div class="thefield">
				<input type="text" id="job" name="job" value="${contact.company}" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="address" class="styled">Адрес:</label>
			<div class="thefield">
				<label for="country">Страна:</label><br />
				<input type="text" id="country" name="country" value="${contact.address.country}" /><br />
				<label for="town">Город:</label><br />
				<input type="text" id="town" name="town" value="${contact.address.town}" /><br />
				<label for="street">Улица:</label><br />
				<input type="text" id="street" name="street" value="${contact.address.street}" /><br />
				<label for="house">Дом:</label><br />
				<input type="text" id="house" name="house" value="${contact.address.house}" /><br />
				<label for="flat">Квартира:</label><br />
				<input type="text" id="flat" name="flat" value="${contact.address.flat}" /><br />
				<label for="flat">Индекс:</label><br />
				<input type="text" id="index" name="index" value="${contact.address.indexValue}" /><br />
			</div>
		</div>
		
	</div>
	<div class="right">
	
    <div id="popUpDivPhone" style="display:none;">  
    	<input type="hidden" id="phoneId" name="phoneId" value="0" />
    	<input type="hidden" id="editRow" name="editRow" value="0" />
    	<input type="hidden" id="dateField" name="dateField" value="" />
    	<p class="page-info"><span>Создание/редактирование телефона</span></p>
		<div class="fieldwrapper">
			<label for="countryCode" class="styled">Код страны:</label>
			<div class="thefield">
				<input type="text" id="countryCode" name="countryCode" value="" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="operatorCode" class="styled">Код оператора:</label>
			<div class="thefield">
				<input type="text" id="operatorCode" name="operatorCode" value="" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="number" class="styled">Телефонный номер:</label>
			<div class="thefield">
				<input type="text" id="number" name="number" value="" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="phoneType" class="styled">Описание:</label>
			<div class="thefield">
				<ul>
					<c:forEach var="elem" items="${helper.takePhoneTypes()}" varStatus="status">
					<li><input type="radio" name="phoneType" value="${elem.title}"/>${elem.title}</li>
					</c:forEach>
				</ul>
			</div>
		</div> 
		<div class="fieldwrapper">
			<label for="comment" class="styled">Коментарий:</label>
			<div class="thefield">
				<textarea name="phoneComment"></textarea>
			</div>
		</div>
   		<div class="buttonsdiv" >
			<a href="#" onclick="popup('popUpDivPhone', 450, 600); editPhone()">Сохранить</a>
			<a href="#" onclick="popup('popUpDivPhone', 450, 600)">Отменить</a>
		</div>		
	</div>
	
		<div id="boxtab-blue">
			<ul>
				<li class="active"><a href="#" onclick="deleteRow('phoneTable')"><span>Удалить</span></a></li>
				<li class="last"><a href="#" onclick="popup('popUpDivPhone', 450, 600); clearFields()"><span>Создать</span></a></li>
			</ul>
		</div>
		<p class="table-info"><span>Список контактных телефонов</span></p>
          <table id="phoneTable">
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
                            <td><input type="checkbox"></td>
                            <td>
                            	<input type="hidden" name="phone" value="${elem.id}:${elem.countryCode}:${elem.operatorCode}:${elem.basicNumber}:${elem.type.title}:${elem.userComment}" >
								<a href="#" class="name" onclick="popup('popUpDivPhone', 450, 600); fillPhone(this)">+${elem.countryCode} (${elem.operatorCode}) ${elem.basicNumber}</a>
                            </td>
                            <td> ${elem.type.title} </td>
                            <td>${elem.userComment}</td>
                    </tr>
                </c:forEach>
            </table>
		<br/><br/><br/>
		
		<div id="popUpDivAttach" style="display:none;">  
			<input type="hidden" name="attachId"  id="attachId" value="0" />
			<input type="hidden" id="editAttach" name="editAttach" value="0" />
    		<p class="page-info"><span>Создание/редактирование присоединений</span></p>
			<div class="fieldwrapper">
				<label for="attachName" class="styled">Название:</label>
				<div class="thefield">
					<input type="text" id="attachName" name="attachName" value="" /><br />
				</div>
			</div>
			<div class="fieldwrapper">
				<label for="attachComment" class="styled">Коментарий:</label>
				<div class="thefield">
					<textarea name="attachComment" ></textarea>
				</div>
			</div>
			<div id="attachFileDiv">
      			<input type="file" name="attach" id="attach"  size="50">
      		</div>
   		<div class="buttonsdiv" >
   			<a href="#" onclick="popup('popUpDivAttach', 300, 600); editAttach()">Сохранить</a>
			<a href="#" onclick="popup('popUpDivAttach', 300, 600)">Отменить</a>
		</div>
		</div>
		
		<div id="boxtab-blue">
			<ul>
				<li class="active"><a href="#" onclick="deleteRow('attachTable')"><span>Удалить</span></a></li>
				<li class="last"><a href="#" onclick="popup('popUpDivAttach', 300, 600); clearAttach()"><span>Создать</span></a></li>
			</ul>
		</div>
		<p class="table-info"><span>Список присоединений</span></p>
          <table id="attachTable">
                <tr>
                    <th>&nbsp;</th>
                    <th>
                        Название
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
                            <td><input type="checkbox"></td>
                            <td>
                            	<input type="hidden" name="attachment" value="${elem.id}#${elem.title}#${elem.uploads.toString('yyyy-MM-dd-HH-mm-ss')}#${elem.userComment}">
                            	<a href="#" class="name" onclick="popup('popUpDivAttach', 300, 600); fillAttach(this)">${elem.title}</a>
                            </td>
                            <td>${elem.uploads.toString("yyyy-MM-dd-HH-mm-ss")}</td>
                            <td>${elem.userComment}</td>
                    </tr>
                </c:forEach>
            </table>
	</div>
	<div class="clr"></div>
	<div class="buttonsdiv" >
			<input type="submit" name="saveContact" value="Сохранить" id="savebutton" /> 
			<a href="${pageContext.request.contextPath}/index.jsp">Отменить</a>
			<!-- <input type="submit" name="cancelContact" value="Отменить" /> -->
	</div>
	<br/>
</form>
      <div class="clr"></div>
    </div>
  </div>
  <c:import url="footer.jsp" />
</div>
<script src="${pageContext.request.contextPath}/js/editcontact.js"  type="text/javascript"></script>
</body>
</html>
