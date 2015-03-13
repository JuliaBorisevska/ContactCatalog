<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/edit.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="main">
  <c:import url="header.jsp" />
  <div class="clr"></div>
  <div class="content">
    <div class="content_resize">
    <p class="page-info"><span>Отправка Email</span></p>
      <form class="contactform" action="${pageContext.request.contextPath}/sendMail.do" method="post">
		<div class="fieldwrapper">
			<label for="whom" class="styled">Кому:</label>
			<div class="thefield">
				<c:forEach var="elem" items="${contacts}" varStatus="status">
                     ${elem.email}<br/>
                     <input type="hidden" id="idContact" name="idContact" value="${elem.id}"> 
                </c:forEach>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="topic" class="styled">Тема:</label>
			<div class="thefield">
				<input type="text" id="topic" name="topic" value=""/><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="patt" class="styled">Шаблон:</label>
			<div class="thefield">
			<br/>
				<select id="patt"  name="template" onchange="getLetter(this)">
					<option value="letter">не задан</option>
					<c:forEach var="elem" items="${templates}" varStatus="status">
						<option value="${elem.template}">${elem.title}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="fieldwrapper" id="text">
			<label for="letter" class="styled">Текст:</label>
			<div class="thefield">
				<textarea name="letter" id="letter"></textarea>
				<c:forEach var="elem" items="${templates}" varStatus="status">
					<div id="${elem.template}" style="display:none;">
						${elem.takeText()}
					</div>
		</c:forEach>
			</div>
		</div>
		<div class="buttonsdiv" >
			<input type="submit" value="Отправить" id="savecan" /> 
			<a href="${pageContext.request.contextPath}/index.jsp" id="savecan">Отменить</a>
		</div>
		<br/>
</form>
      <div class="clr"></div>
    </div>
  </div>
  <c:import url="footer.jsp" />
</div>
<script src="${pageContext.request.contextPath}/js/email.js"  type="text/javascript"></script>
</body>
</html>
