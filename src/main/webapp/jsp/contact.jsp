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
<body onload="fillContact('${contact.sex.id}', '${contact.maritalStatus.id}' ); convertDate(); validate('left') ">
<jsp:useBean id="helper" scope="page" class="com.itechart.contactcatalog.helper.ViewHelper" />
<div class="main">
  <c:import url="header.jsp" />
  <div class="clr"></div>
  <div class="content">
    <div class="content_resize">
    <p class="page-info">Создание/редактирование контакта</p>
      <form class="contactform"  name="contactForm" action="${pageContext.request.contextPath}/changeContact.do" enctype="multipart/form-data" method="post" onsubmit="return validateOnFormSubmit('left', true)">
      <input type="hidden" name="contactId" value="${contact.id}" />
      <input type="hidden" name="image" value="${contact.image}" />
      <div id="blanket" style="display:none;"></div>
      <div id="popUpDivImage" style="display:none;"> 
      		<p class="page-info"><span>Выбор фото</span></p> 
      		<div id="imageFile">
      			<input type="file" name="photo" id="photo" accept="image/*" size="50">
      		</div>
   			<div class="buttonsdiv" >
   				<a href="#" id="savecan" onclick="popup('popUpDivImage', 200, 400); savePhoto('${pageContext.request.contextPath}')">Сохранить</a>
				<a href="#" id="savecan" onclick="popup('popUpDivImage', 200, 400); clearPhoto()">Отменить</a>
			</div>
	  </div>
      <div class="left" id="left">
      	<div class="fieldwrapper">
			<c:choose>
				<c:when test="${contact.image!=null}" >
					<img id="photoImage" src="${pageContext.request.contextPath}/images/${contact.image}" onclick="popup('popUpDivImage', 200, 400)" />
				</c:when>
				<c:otherwise>
					<img id="photoImage" src="${pageContext.request.contextPath}/images/grey_man.png" onclick="popup('popUpDivImage', 200, 400)" />
				</c:otherwise>
			</c:choose>
      	</div>
		<div class="fieldwrapper">
			<label for="firstname" class="styled">Имя:</label>
			<div class="thefield">
				<input type="text" id="firstname" name="firstname" value="${contact.firstName}" necessary regex="^[A-ZА-Я]{1}[a-zёа-я]{2,20}$" onfocus="getHint(this)" onblur="hideHint(this)">
				<span class="hidden">Имя обязательно для заполнения и должно содержать только буквы (до 20 символов)</span>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="lastname" class="styled">Фамилия:</label>
			<div class="thefield">
				<input type="text" id="lastname" name="lastname" value="${contact.lastName}" necessary regex="^[A-ZА-Я]{1}[a-zёа-я]{2,19}-?([A-ZА-Я]{1}[a-zёа-я]{2,18})?$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Фамилия обязательна для заполнения и может содержать только буквы и дефис (до 40 символов)</span>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="middlename" class="styled">Отчество:</label>
			<div class="thefield">
				<input type="text" id="middlename" name="middlename" value="${contact.middleName}" regex="^[A-ZА-Я]{1}[a-zёа-я]{2,20}$" onfocus="getHint(this)" onblur="hideHint(this)" /><br />
				<span class="hidden">Отчество должно содержать до 20 букв</span>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="year" class="styled">Дата рождения:</label>
			<div class="thefield">
				<input type="text" id="year" name="year" value="${contact.birthDate}" necessary regex="^[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Дата рождения обязательна для заполнения и должна иметь формат ГГГГ-ММ-ДД</span>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="sex" class="styled">Пол:</label>
			<div class="thefield">
			<span class="hidden">Пол обязательно должен быть выбран</span>
				<ul>
					<c:forEach var="elem" items="${helper.takeSexList()}" varStatus="status">
					<li><input type="radio" name="sex" value="${elem.id }" onchange="hideHint(this.parentNode.parentNode)"/>${elem.title }</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="citizenship" class="styled">Гражданство:</label>
			<div class="thefield">
				<input type="text" id="citizenship" name="citizenship" value="${contact.citizenship}" regex="^[A-ZА-Яa-zа-я]{2,20}$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле может содержать только буквы и пробелы (до 20 символов)</span>
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
				<input type="text" id="site" name="site" value="${contact.website}" regex="^((https?|ftp)\:\/\/)?([a-z0-9]{1})((\.[a-z0-9-])|([a-z0-9-]))*\.([a-z]{2,6})(\/?)$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Образец: www.google.com, http://www.my-site.com</span>			
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="email" class="styled">Email:</label>
			<div class="thefield">
				<input type="text" id="email" name="email" value="${contact.email}" regex="^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Образец: contact@gmail.com</span>			
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="job" class="styled">Место работы:</label>
			<div class="thefield">
				<input type="text" id="job" name="job" value="${contact.company}" regex="^[\dA-ZА-ЯЁa-zёа-я\s-]{2,20}$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле может содержать только буквы, цифры, пробелы и дефис (до 20 символов)</span>			
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="address" class="styled">Адрес:</label>
			<div class="thefield">
				<div>
				<label for="country">Страна:</label><br />
				<input type="text" id="country" name="country" value="${contact.address.country}" necessary regex="^[A-ZА-ЯЁa-zёа-я\s-]{2,20}$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле обязательно для заполнения и может содержать только буквы, дефис и пробелы (до 20 символов)</span>
				</div>
				<div>
				<label for="town">Город:</label><br />
				<input type="text" id="town" name="town" value="${contact.address.town}" necessary regex="^[A-ZА-ЯЁa-zёа-я\s-]{2,20}$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле обязательно для заполнения и может содержать только буквы, дефис и пробелы (до 20 символов)</span>
				</div>
				<div>
				<label for="street">Улица:</label><br />
				<input type="text" id="street" name="street" value="${contact.address.street}" regex="^[A-ZА-ЯЁa-zёа-я\s-\d\.]{2,20}$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле может содержать до 20 символов (допустимы буквы, цифры, пробелы, знак ".")</span>
				</div>
				<div>
				<label for="house">Дом:</label><br />
				<input type="text" id="house" name="house" value="${contact.address.house}" regex="^\d+$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле может содержать только цифры</span>
				</div>
				<div>
				<label for="flat">Квартира:</label><br />
				<input type="text" id="flat" name="flat" value="${contact.address.flat}" regex="^\d+$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле может содержать только цифры</span>
				</div>
				<div>
				<label for="flat">Индекс:</label><br />
				<input type="text" id="index" name="index" value="${contact.address.indexValue}" regex="^\d{6}$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле должно содержать 6 цифр</span>
				</div>
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
				<input type="text" id="countryCode" name="countryCode" value="" necessary regex="^\d{3}$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле обязательно для заполнения и должно содержать 3 цифры</span>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="operatorCode" class="styled">Код оператора/города:</label>
			<div class="thefield">
				<input type="text" id="operatorCode" name="operatorCode" value="" necessary regex="^\d{2,3}$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле обязательно для заполнения и должно содержать то 2-х до 3-х цифр</span>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="number" class="styled">Телефонный номер:</label>
			<div class="thefield">
				<input type="text" id="number" name="number" value="" necessary regex="^\d{5,7}$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле обязательно для заполнения и должно содержать 5-7 цифр</span>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="phoneType" class="styled">Описание:</label>
			<div class="thefield">
				<span class="hidden">Тип телефона обязательно должен быть выбран</span>
				<ul>
					<c:forEach var="elem" items="${helper.takePhoneTypes()}" varStatus="status">
					<li><input type="radio" name="phoneType" value="${elem.title}" onchange="hideHint(this.parentNode.parentNode)"/>${elem.title}</li>
					</c:forEach>
				</ul>
			</div>
		</div> 
		<div class="fieldwrapper">
			<label for="comment" class="styled">Коментарий:</label>
			<div class="thefield">
				<textarea name="phoneComment" regex="^[A-ZЁА-Яa-zёа-я\s_,;:!?()\d]{1,100}$" onfocus="getHint(this)" onblur="hideHint(this)"></textarea>
				<span class="hidden">Данное поле может содержать латиницу и кириллицу, цифры, пробелы, следующие знаки: , ; : ! ? () (всего максимум 100 символов)</span>
			</div>
		</div>
   		<div class="buttonsdiv" >
			<a href="#" id="savecan" onclick="validateOnPhoneButtonClick('popUpDivPhone')">Сохранить</a>
			<a href="#" id="savecan" onclick="popup('popUpDivPhone', 450, 600)">Отменить</a>
		</div>		
	</div>
	
		<div id="boxtab-blue">
			<ul>
				<li class="active"><a href="#" onclick="deleteRow('phoneTable')"><span>Удалить</span></a></li>
				<li class="last"><a href="#" onclick="popup('popUpDivPhone', 450, 600); clearFields(); validate('popUpDivPhone')"><span>Создать</span></a></li>
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
                            	<input type="hidden" name="phone" value="${elem.id}#${elem.countryCode}#${elem.operatorCode}#${elem.basicNumber}#${elem.type.title}#${elem.userComment}" >
								<a href="#" class="name" onclick="popup('popUpDivPhone', 450, 600); fillPhone(this); validate('popUpDivPhone')">+${elem.countryCode} (${elem.operatorCode}) ${elem.basicNumber}</a>
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
					<input type="text" id="attachName" name="attachName" value="" necessary regex="^[A-ZЁА-Яa-zёа-я\s_;:!?()\d]{1,20}$" onfocus="getHint(this)" onblur="hideHint(this)" /><br />
					<span class="hidden">Данное поле может содержать латиницу и кириллицу, цифры, пробелы, следующие знаки: , ; : ! ? () (всего максимум 20 символов)</span>
			</div>
			</div>
			<div class="fieldwrapper">
				<label for="attachComment" class="styled">Коментарий:</label>
				<div class="thefield">
					<textarea name="attachComment" regex="^[A-ZЁА-Яa-zёа-я\s_;:!?()\d]{1,100}$" onfocus="getHint(this)" onblur="hideHint(this)"></textarea>
					<span class="hidden">Данное поле может содержать латиницу и кириллицу, цифры, пробелы, следующие знаки: , ; : ! ? () (всего максимум 100 символов)</span>
			</div>
			</div>
			<div id="attachFileDiv">
      			<input type="file" name="attach" id="attach"  size="50" onclick="hideHint(this)">
      			<span class="hidden">Файл обязательно должен быть выбран</span>
      		</div>
   		<div class="buttonsdiv" >
   			<a href="#" onclick="validateOnAttachmentButtonClick('popUpDivAttach')" id="savecan">Сохранить</a>
			<a href="#" onclick="popup('popUpDivAttach', 300, 600)" id="savecan">Отменить</a>
		</div>
		</div>
		
		<div id="boxtab-blue">
			<ul>
				<li class="active"><a href="#" onclick="deleteRow('attachTable')"><span>Удалить</span></a></li>
				<li class="last"><a href="#" onclick="popup('popUpDivAttach', 300, 600); clearAttach(); validate('popUpDivAttach')"><span>Создать</span></a></li>
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
                            	<a href="#" class="name" onclick="popup('popUpDivAttach', 300, 600); fillAttach(this); validate('popUpDivAttach')">${elem.title}</a>
                            </td>
                            <td>${elem.uploads.toString("yyyy-MM-dd-HH-mm-ss")}</td>
                            <td>${elem.userComment}</td>
                            <td><a href="${pageContext.request.contextPath}/downloadAttachment.do?file=${elem.path}" class="download">Скачать</a></td>
                    </tr>
                </c:forEach>
            </table>
	</div>
	<div class="clr"></div>
	<div class="buttonsdiv" >
			<input type="submit" name="saveContact" value="Сохранить" id="savecan"/> 
			<a href="${pageContext.request.contextPath}/index.jsp" id="savecan" >Отменить</a>
	</div>
	<br/>
</form>
      <div class="clr"></div>
    </div>
  </div>
  <c:import url="footer.jsp" />
</div>
<script src="${pageContext.request.contextPath}/js/editcontact.js"  type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/validation.js"  type="text/javascript"></script>

</body>
</html>
