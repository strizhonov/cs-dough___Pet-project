<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>

<div>
    <form>
        <img src="" alt="">
        <button class="for-logo">Upload logo</button>
    </form>
</div>
<div>
    <form class="register-form" action="${pageContext.request.contextPath}/?command=create_player" method="post">
        <h3>New player creation form</h3>
        <div>
            <input name="name" type="text" placeholder="<fmt:message key="name"/>">
        </div>
        <div>
            <input name="surname" type="text" placeholder="<fmt:message key="surname"/>">
        </div>
        <div>
            <input name="nickname" type="text" placeholder="<fmt:message key="nickname"/>">
        </div>
        <div>
            <input name="country" type="text" placeholder="<fmt:message key="country"/>">
        </div>
        <button>
            <fmt:message key="create"/>
        </button>
</form>
    
