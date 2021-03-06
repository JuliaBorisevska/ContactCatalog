<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="pagecontent" var="rb" />
<!DOCTYPE HTML>
<html>
<head>
<title>Error Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="main">
  <c:import url="header.jsp" />
  <div class="clr"></div>
  <div class="content">
    <div class="content_resize">
    <p class="page-info">Ошибка</p>
      <fmt:message key="${customerror}" bundle="${ rb }" />
      <br/><br/><br/>
    </div>
  </div>
  <c:import url="footer.jsp" />
</div>

</body>
</html>
