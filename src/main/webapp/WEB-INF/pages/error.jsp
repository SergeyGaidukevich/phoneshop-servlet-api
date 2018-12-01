<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 19.11.2018
  Time: 7:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Error page</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
<main>
    <div>error 404: ${pageContext.exception.message}</div>
    <p>Go to:</p>
    <ul>
        <li>
            <form>
                <input type="button" value="Back" onclick="history.back();"/>
            </form>
        <li>
            <form>
                <input type="button" value="To main page"
                       onClick='location.href="${pageContext.servletContext.contextPath}/products"'>
            </form>
    </ul>
</main>
</body>
</html>
