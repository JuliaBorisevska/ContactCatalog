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
<script src="js/popup.js"  type="text/javascript"></script>
</head>
<body>
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
      		<img src="images/grey_man.png" onclick="popup('popUpDivImage')" />
      	</div>
		<div class="fieldwrapper">
			<label for="firstname" class="styled">Имя:</label>
			<div class="thefield">
				<input type="text" id="firstname" value="" />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="lastname" class="styled">Фамилия:</label>
			<div class="thefield">
				<input type="text" id="lastname" value="" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="midlename" class="styled">Отчество:</label>
			<div class="thefield">
				<input type="text" id="midlename" value="" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="year" class="styled">Дата рождения:</label>
			<div class="thefield">
				<input type="text" id="year" value="" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="sex" class="styled">Пол:</label>
			<div class="thefield">
				<ul>
					<li><input type="radio" name="sex" value=""/>женский</li>
					<li><input type="radio" name="sex" value=""/>мужской</li>
				</ul>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="citizenship" class="styled">Гражданство:</label>
			<div class="thefield">
				<input type="text" id="email" value="" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="matrial" class="styled">Семейное положение:</label>
			<div class="thefield">
			<br/>
				<select id="matrial">
					<option value="">замужем</option>
					<option value="">не замужем</option>
				</select>
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="site" class="styled">Сайт:</label>
			<div class="thefield">
				<input type="text" id="site" value="" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="email" class="styled">Email:</label>
			<div class="thefield">
				<input type="text" id="email" value="" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="job" class="styled">Место работы:</label>
			<div class="thefield">
				<input type="text" id="job" value="" /><br />
			</div>
		</div>
		<div class="fieldwrapper">
			<label for="address" class="styled">Адрес:</label>
			<div class="thefield">
				<label for="country">Страна:</label><br />
				<input type="text" id="country" value="" /><br />
				<label for="town">Город:</label><br />
				<input type="text" id="town" value="" /><br />
				<label for="street">Улица:</label><br />
				<input type="text" id="street" value="" /><br />
				<label for="house">Дом:</label><br />
				<input type="text" id="house" value="" /><br />
				<label for="flat">Квартира:</label><br />
				<input type="text" id="flat" value="" /><br />
			</div>
		</div>
		
	</div>
	<div class="right">
		<div id="boxtab-blue">
			<ul>
				<li class="first"><a href="#"><span>Удалить</span></a></li>
				<li class="active"><a href="#"><span>Редактировать</span></a></li>
				<li class="last"><a href="#"><span>Создать</span></a></li>
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
                <%-- <c:forEach var="elem" items="${contacts}" varStatus="status"> --%>
                    <tr>
                            <td>
                            	<input type="checkbox">
                            </td>
                            <td>
                                номер
                            </td>
                            <td>
                                дата 
                            </td>
                            <td>
                                адрес
                            </td>
                    </tr>
                    <tr>
                            <td>
                            	<input type="checkbox">
                            </td>
                            <td>
                                номер
                            </td>
                            <td>
                                дата 
                            </td>
                            <td>
                                адрес
                            </td>
                    </tr>
                    <tr>
                            <td>
                            	<input type="checkbox">
                            </td>
                            <td>
                                номер
                            </td>
                            <td>
                                дата 
                            </td>
                            <td>
                                адрес
                            </td>
                    </tr>
                <%--</c:forEach> --%>
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
                <c:forEach var="elem" items="${contacts}" varStatus="status">
                    <tr>
                            <td>
                            	<input type="checkbox">
                            </td>
                            <td>
                                файл
                            </td>
                            <td>
                                ${elem.birthDate} 
                            </td>
                            <td>
                                ${elem.address} 
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
