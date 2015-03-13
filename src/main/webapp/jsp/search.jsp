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
<body>
<jsp:useBean id="helper" scope="page" class="com.itechart.contactcatalog.helper.ViewHelper" />
<div class="main">
  <c:import url="header.jsp" />
  <div class="clr"></div>
  <div class="content">
    <div class="content_resize">
    <p class="page-info">Поиск контактов</p>
      <form class="contactform"  name="contactForm" action="${pageContext.request.contextPath}/findContacts.do" method="post">
      <div class="left">
		<div class="fieldwrapper">
			<label for="firstname" class="styled">Имя:</label>
			<div class="thefield">
				<input type="text" id="firstname" name="firstname" value="" />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="lastname" class="styled">Фамилия:</label>
			<div class="thefield">
				<input type="text" id="lastname" name="lastname" value="" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="middlename" class="styled">Отчество:</label>
			<div class="thefield">
				<input type="text" id="middlename" name="middlename" value="" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="year" class="styled">Дата рождения:</label>
			<div class="thefield">
				<label for="more">Больше:</label><br />
				<input type="text" id="more" name="more" value="" /><br />
				<label for="less">Меньше:</label><br />
				<input type="text" id="less" name="less" value="" /><br />
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
				<input type="text" id="citizenship" name="citizenship" value="" /><br />
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
				<label for="country">Страна:</label><br />
				<input type="text" id="country" name="country" value="" /><br />
				<label for="town">Город:</label><br />
				<input type="text" id="town" name="town" value="" /><br />
				<label for="street">Улица:</label><br />
				<input type="text" id="street" name="street" value="" /><br />
				<label for="house">Дом:</label><br />
				<input type="text" id="house" name="house" value="" /><br />
				<label for="flat">Квартира:</label><br />
				<input type="text" id="flat" name="flat" value="" /><br />
				<label for="flat">Индекс:</label><br />
				<input type="text" id="index" name="index" value="" /><br />
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
</body>
</html>