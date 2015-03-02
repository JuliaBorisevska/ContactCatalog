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
<link href="css/edit.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="main">
  <c:import url="header.jsp" />
  <div class="clr"></div>
  <div class="content">
    <div class="content_resize">
    <p class="page-info"><span>Отправка Email</span></p>
      <form class="contactform">
		<div class="fieldwrapper">
			<label for="whom" class="styled">Кому:</label>
			<div class="thefield">
				<textarea name="maillist" readonly></textarea>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="topic" class="styled">Тема:</label>
			<div class="thefield">
				<input type="text" id="topic" value="" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="patt" class="styled">Шаблон:</label>
			<div class="thefield">
			<br/>
				<select id="matrial">
					<option value="">Напоминание</option>
					<option value="">Поздравление</option>
				</select>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="letter" class="styled">Текст:</label>
			<div class="thefield">
				<textarea name="maillist" ></textarea>
			</div>
		</div>
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
