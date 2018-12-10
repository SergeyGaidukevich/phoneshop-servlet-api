<%@attribute name="pageTitle" type="java.lang.String" required="true" %>
<%@attribute name="pageClass" type="java.lang.String" required="false" %>

<html>
<head>
    <title>${pageTitle}</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="${pageClass}">
<div>
    <header>
        <a href="${pageContext.servletContext.contextPath}">
            <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
            PhoneShop
        </a>
    </header>
</div>
<main>
    <jsp:doBody/>
</main>
<div>
    <footer style="padding: 10px; background: aquamarine">
        &copy; made by Sergey Gaidukevich
    </footer>
</div>
</body>
</html>