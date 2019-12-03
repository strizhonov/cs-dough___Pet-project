<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty language ? language : param.language ? param.language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="content"/>
<html>
<head>
    <meta charset="utf-8">
    <title><fmt:message key="player.profile"/></title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="/jsp/jsp-block/header.jsp"/>

<%--CONTENT--%>
<jsp:include page="/jsp/jsp-block/player-profile-block.jsp"/>


<jsp:include page="/jsp/jsp-block/footer.jsp"/>
</body>
</html>
