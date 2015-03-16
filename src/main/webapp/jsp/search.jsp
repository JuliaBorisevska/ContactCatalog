<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/edit.css" rel="stylesheet" type="text/css" />
<title>Поиск контактов</title>
</head>
<body onload="validate('left')">
<jsp:useBean id="helper" scope="page" class="com.itechart.contactcatalog.helper.ViewHelper" />
<div class="main">
  <c:import url="header.jsp" />
  <div class="clr"></div>
  <div class="content">
    <div class="content_resize">
    <p class="page-info">Поиск контактов</p>
      <form class="contactform"  name="contactForm" action="${pageContext.request.contextPath}/findContacts.do" method="post" onsubmit="return validateOnSearchSubmit('left')">
      <div class="left" id="left">
		<div class="fieldwrapper">
			<label for="firstname" class="styled">Имя:</label>
			<div class="thefield">
				<input type="text" id="firstname" name="firstname" value="" regex="^[A-ZЁА-Яa-zёа-я]{1,20}$" onfocus="getHint(this)" onblur="hideHint(this)">
				<span class="hidden">Имя должно содержать только буквы (до 20 символов)</span>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="lastname" class="styled">Фамилия:</label>
			<div class="thefield">
				<input type="text" id="lastname" name="lastname" value="" regex="^[A-ZЁА-Яa-zёа-я]{1,20}$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Фамилия может содержать только буквы и дефис (до 30 символов)</span>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="middlename" class="styled">Отчество:</label>
			<div class="thefield">
				<input type="text" id="middlename" name="middlename" value="" regex="^[A-ZЁА-Яa-zёа-я]{1,20}$" onfocus="getHint(this)" onblur="hideHint(this)" /><br />
				<span class="hidden">Отчество должно содержать до 20 букв</span>
			</div>
		</div>
		<div class="fieldwrapper">
			<span class="hidden" onclick="hideHint(this)">Должны быть заполнены обе даты</span>
			<label for="year" class="styled">Дата рождения:</label>
			<div class="thefield">
				<div>
				<label for="more">Больше:</label><br />
				<input type="text" id="more" name="more" value="" regex="^[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Дата должна иметь формат ГГГГ-ММ-ДД</span>
				</div>
				<div>
				<label for="less">Меньше:</label><br />
				<input type="text" id="less" name="less" value="" regex="^[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Дата должна иметь формат ГГГГ-ММ-ДД</span>
				</div>
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
				<input type="text" id="citizenship" name="citizenship" value="" regex="^[A-ZЁА-Яa-zёа-я]{1,20}$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле может содержать только буквы (до 20 символов)</span>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="matrial" class="styled">Семейное положение:</label>
			<div class="thefield">
			<br/>
				<select id="matrial" name="status">
					<option value="0">не задано</option>
					<c:forEach var="elem" items="${helper.takeMaritalStatusList()}" varStatus="status">
						<option value="${elem.id}">${elem.title}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="address" class="styled">Адрес:</label>
			<div class="thefield">
				<div>
				<label for="country">Страна:</label><br />
				<input type="text" id="country" name="country" value="" regex="^[A-ZЁА-Яa-zёа-я]{1,20}$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле обязательно для заполнения и может содержать только буквы (до 20 символов)</span>
				</div>
				<div>
				<label for="town">Город:</label><br />
				<input type="text" id="town" name="town" value="" regex="^[A-ZЁА-Яa-zёа-я]{1,20}$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле обязательно для заполнения и может содержать только буквы (до 20 символов)</span>
				</div>
				<div>
				<label for="street">Улица:</label><br />
				<input type="text" id="street" name="street" value="" regex="^[A-ZА-ЯЁa-zёа-я\s-\d\.]{1,20}$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле может содержать до 20 символов (допустимы буквы, цифры, пробелы, знак ".")</span>
				</div>
				<div>
				<label for="house">Дом:</label><br />
				<input type="text" id="house" name="house" value="" regex="^\d+$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле может содержать только цифры</span>
				</div>
				<div>
				<label for="flat">Квартира:</label><br />
				<input type="text" id="flat" name="flat" value="" regex="^\d+$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле может содержать только цифры</span>
				</div>
				<div>
				<label for="flat">Индекс:</label><br />
				<input type="text" id="index" name="index" value="" regex="^\d+$" onfocus="getHint(this)" onblur="hideHint(this)"/><br />
				<span class="hidden">Данное поле должно содержать только цифры</span>
				</div>
			</div>
		</div>	
	</div>
	<div class="clr"></div>
	<div class="buttonsdiv" >
		<input type="submit" name="search" value="Найти" id="savecan" /> 
		<br/>
	</div>
</form>
      <div class="clr"></div>
    </div>
  </div>
  <c:import url="footer.jsp" />
</div>
<script src="${pageContext.request.contextPath}/js/validation.js"  type="text/javascript"></script>
</body>
</html>