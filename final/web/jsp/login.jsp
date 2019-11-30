<jsp:include page="/jsp/jsp-block/import.jsp"/>
<html>
<head>
    <jsp:include page="/jsp/jsp-block/head.jsp"/>
</head>
<body>
<jsp:include page="/jsp/jsp-block/header.jsp"/>

<%--CONTENT--%>
<jsp:include page="/jsp/jsp-block/login-block.jsp"/>


<jsp:include page="/jsp/jsp-block/footer.jsp"/>
<jsp:include page="/jsp/jsp-block/common-block.jsp"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/login.js" type="text/javascript"></script>
</body>
</html>