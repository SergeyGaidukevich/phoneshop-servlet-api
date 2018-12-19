<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.Cart" scope="session"/>
<jsp:useBean id="order" type="com.es.phoneshop.model.Order" scope="request"/>
<tags:master pageTitle="OrderOverview">
    <h1>Thank you for your order!</h1>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td class="price">Price</td>
            <td class="number">Quantity</td>
        </tr>
        </thead>
        <c:forEach var="item" items="${order.cartItems}" varStatus="status">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">
                </td>
                <td>
                        ${item.product.description}
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
            <c:if test="${not empty order}">
                <td></td>
                <td></td>
                <td>Total: <fmt:formatNumber value="${order.totalPrice}" type="currency"
                                             currencySymbol="USD"/>
                </td>
            </c:if>
        </tr>
        <p>Name: ${order.name}</p>
        <p>Delivery address: ${order.deliveryAddress}</p>
        <p>Phone: ${order.phone}</p>
    </table>
</tags:master>