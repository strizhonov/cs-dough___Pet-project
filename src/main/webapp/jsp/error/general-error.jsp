<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title><fmt:message key="error"/></title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="error">
    <div class="error">
        <div class="inner-error">
            <h1><fmt:message key="oops"/></h1>
            <h2><fmt:message key="something.wrong"/></h2>
        </div>
        <a href="${pageContext.request.contextPath}/?command=to_home_page"><fmt:message key="to.home.page"/></a>
    </div>
</div>
</body>
</html>
