<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form>
        <input type="button" value="Cart"
               onClick='location.href="${pageContext.servletContext.contextPath}/cart"'>
    </form>
    <form>
        <input type="search" name="search" value="${param.search}" size="39" placeholder="Input description phone">
        <input type="hidden" name="sortProperty" value="${param.sortProperty}">
        <input type="hidden" name="sortMode" value="${param.sortMode}">
        <button>Search</button>
    </form>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description
                <a href="<c:url value="/products?sortProperty=DESCRIPTION&sortMode=ASCENDING&search=${param.search}"/>">asc</a>
                <a href="<c:url value="/products?sortProperty=DESCRIPTION&sortMode=DESCENDING&search=${param.search}"/>">desc</a>
            </td>
            <td class="price">Price
                <a href="<c:url value="/products?sortProperty=PRICE&sortMode=ASCENDING&search=${param.search}"/>">asc</a>
                <a href="<c:url value="/products?sortProperty=PRICE&sortMode=DESCENDING&search=${param.search}"/>">desc</a>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td>
                    <a href="<c:url value="/products/${product.id}"/>">${product.description}</a>
                </td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>