<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, mySocialUserDetails-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <form action="/fb/login" method="POST">
        <input type="submit">
        <input type="hidden"
               name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
    <p>
        ${principal}
    </p>
    <sec:authorize access="isAuthenticated()">
        pussy
    </sec:authorize>
</body>
</html>