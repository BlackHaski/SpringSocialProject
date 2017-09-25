<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: BlackDoggy
  Date: 13.09.2017
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shalom</title>
</head>
<body>
    <c:forEach var="image" items="${images}">
        <img src="${image}" height="200px" width="200px"> <br>
    </c:forEach>
</body>
</html>
