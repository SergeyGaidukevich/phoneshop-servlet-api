<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:master pageTitle="Quick order">
    <form method="post" action="<c:url value="/quickOrderEntry/"/>">
        <table>
            <tr>
                <td align="center" style="font-weight: 600">№</td>
                <td align="center" style="font-weight: 600">Code</td>
                <td align="center" style="font-weight: 600">Quantity</td>
            </tr>
            <c:forEach var="i" begin="0" end="9" varStatus="status">
                <tr>
                    <td style="font-weight: 600">${status.index + 1}</td>
                    <td>
                        <input name="code" placeholder="code"
                               value="${paramValues["code"][status.index]}">
                        <c:if test="${not empty requestScope.codeErrors[status.index]}">
                            <p class="error">${requestScope.codeErrors[status.index]}</p>
                        </c:if>
                    </td>
                    <td>
                        <input name="quantity" class="number" placeholder="quantity"
                               value="${paramValues["quantity"][status.index ]}">
                        <c:if test="${not empty requestScope.quantityErrors[status.index]}">
                            <p class="error">${requestScope.quantityErrors[status.index]}</p>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <button>Add to cart</button>
        <c:if test="${not empty requestScope.quantityErrors}">
            <p class="error">Failed to update</p>
        </c:if>
        <c:if test="${not empty param.message}">
            <p class="success">${param.message}</p>
        </c:if>
    </form>
</tags:master>
