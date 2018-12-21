<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.Cart" scope="session"/>
<tags:master pageTitle="Checkout">
    <h1>Your Cart:</h1>
    <form method="post" action="<c:url value="/checkout"/>">
        <button>Place order</button>
        <c:if test="${not empty requestScope.errorMessage}">
            <p class="error">${requestScope.errorMessage}</p>
        </c:if>
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td class="price">Price</td>
                <td class="number">Quantity</td>
            </tr>
            </thead>
            <c:forEach var="item" items="${cart.cartItems}" varStatus="status">
                <tr>
                    <td>
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">
                    </td>
                    <td>
                        <a href="<c:url value="/products/${item.product.id}"/>">${item.product.description}</a>
                    </td>
                    <td class="price">
                        <fmt:formatNumber value="${item.product.price}" type="currency"
                                          currencySymbol="${item.product.currency.symbol}"/>
                    </td>
                    <td>
                        <p class="price">${item.quantity}</p>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <c:if test="${not empty cart}">
                    <td></td>
                    <td>Total:</td>
                    <td><fmt:formatNumber value="${cart.totalPrice}" type="currency"
                                          currencySymbol="USD"/>
                    </td>
                </c:if>
            </tr>
        </table>
        <input name="name" placeholder="name">
        <br>
        <input name="deliveryAddress" placeholder="delivery address">
        <br>
        <input name="phone" placeholder="phone">
        <br>
        <button>Place order</button>
        <c:if test="${not empty requestScope.errorMessage}">
            <p class="error">${requestScope.errorMessage}</p>
        </c:if>
    </form>
</tags:master>
