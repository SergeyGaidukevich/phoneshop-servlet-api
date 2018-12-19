<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="pageTitle" type="java.lang.String" required="true" %>
<%@attribute name="pageClass" type="java.lang.String" required="false" %>
<html>
<head>
    <title>${pageTitle}</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="<c:url value="/styles/main.css"/>">
</head>
<body class="${pageClass}">
<div>
    <header>
        <a href="<c:url value="/products"/>">
            <img src="<c:url value="/images/logo.svg"/>"/>
            PhoneShop
        </a>
    </header>
</div>
<main>
    <a href="<c:url value="/cart"/>">
        <img src="<c:url value="/images/shopping-cart.svg"/>" style="max-width: 34px;">
        <span>Cart: ${cart.totalPrice}</span>
    </a>
    <jsp:doBody/>
</main>
<div>
    <footer style="padding: 10px; background: aquamarine">
        &copy; made by Sergey Gaidukevich
    </footer>
</div>
</body>
</html>