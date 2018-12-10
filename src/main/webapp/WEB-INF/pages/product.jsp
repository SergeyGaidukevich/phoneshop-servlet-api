<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.Product" scope="request"/>
<tags:master pageTitle="${product.description}">
    <p>${sessionScope.cart}</p>
    <table>
        <tr>
            <td>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td>
                <h2>${product.description}</h2>
                <p>Code: ${product.code}</p>
                <p>Stock: ${product.stock}</p>
                <p>
                    Price: <fmt:formatNumber value="${product.price}" type="currency"
                                             currencySymbol="${product.currency.symbol}"/>
                </p>
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
                        <p><img class="product-tile"
                                src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${viewedProduct.imageUrl}">
                        </p>
                        <a href="${pageContext.servletContext.contextPath}/products/${viewedProduct.id}">${viewedProduct.description}</a>
                        <p><fmt:formatNumber value="${viewedProduct.price}" type="currency"
                                             currencySymbol="${viewedProduct.currency.symbol}"/></p>
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
</tags:master>