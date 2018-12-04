<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 19.11.2018
  Time: 7:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.Product" scope="request"/>
<html>
<head>
    <title>Product ${product.description}</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
<div>
    <jsp:include page="header.jsp"/>
</div>
<main>
    <p>${sessionScope.cart}</p>
    <table>
        <tr>
            <td>
                <img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td>${product.description}</td>
            <td class="price">
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
            <td>
                <form method="post" action="${pageContext.servletContext.contextPath}/products/${product.id}">
                    Quantity: <input name="quantity" value="${not empty param.quantity ? param.quantity : 1}"
                                     class="number" title="enter order quantity">
                    <button>Add</button>
                    <c:if test="${not empty param.message}">
                        <p class="success">${param.message}</p>
                    </c:if>
                    <c:if test="${not empty quantityError}">
                        <p class="error">${quantityError}</p>
                    </c:if>
                </form>
            </td>
        </tr>
    </table>
    <div class="viewedProduct">
        <strong>Recently viewed</strong>
        <table>
            <tr>
                <c:forEach var="viewedProduct" items="${sessionScope.viewedProducts.viewedProducts}">
                    <td>
                        <p><img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${viewedProduct.imageUrl}"></p>
                        <a href="${pageContext.servletContext.contextPath}/products/${viewedProduct.id}">${viewedProduct.description}</a>
                        <p><fmt:formatNumber value="${viewedProduct.price}" type="currency" currencySymbol="${viewedProduct.currency.symbol}"/></p>
                    </td>
                </c:forEach>
            </tr>
        </table>
    </div>
    <form>
        <input type="button" value="Back" onclick="history.back();"/>
    </form>
    <form>
        <input type="button" value="To main page"
               onClick='location.href="${pageContext.servletContext.contextPath}/products"'>
    </form>
</main>
<div>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>