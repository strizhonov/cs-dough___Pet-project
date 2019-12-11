<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty language ? language : param.language ? param.language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="content"/>
<html>
<head>
    <meta charset="utf-8">
    <title><fmt:message key="user.profile"/></title>
    <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="/jsp/jsp-block/header.jsp"/>

<%--CONTENT--%>
<jsp:include page="/jsp/jsp-block/user-profile-block.jsp"/>


<jsp:include page="/jsp/jsp-block/footer.jsp"/>
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/edit.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/uploading.js" type="text/javascript"></script>
</body>
</html>
