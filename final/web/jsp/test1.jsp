<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form class="login-form" action="${pageContext.request.contextPath}/?command=login" method="post">
    <input type="text" placeholder="<fmt:message key="username"/>" name="username"/>
    <input type="password" placeholder="<fmt:message key="password"/>" name="password"/>
    <button>Log In</button>
    <p class="message"><fmt:message key="not.registered"/>&nbsp;<a href="#"><fmt:message key="create.account"/></a></p>
</form>