<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<div class="creation-form">
    <div class="col-sm-6">
        <div class="image-holder">
            <img src="images/registration-form-1.jpg" alt="">
            <button class="for-logo">Upload logo</button>
        </div>
    </div>
    <div class="col-sm-6">
        <form class="register-form" action="${pageContext.request.contextPath}/app?command=create_player" method="post">
            <h3>New player creation form</h3>
            <div class="form-group">
                <input name="name" type="text" placeholder="<fmt:message key="name"/>" class="form-control">
            </div>
            <div class="form-group">
                <input name="surname" type="text" placeholder="<fmt:message key="surname"/>" class="form-control">
            </div>
            <div class="form-group">
                <input name="nickname" type="text" placeholder="<fmt:message key="nickname"/>" class="form-control">
            </div>
            <div class="form-group">
                <input name="country" type="text" placeholder="<fmt:message key="country"/>" class="form-control">
            </div>
            <button><fmt:message key="create"/><i class="zmdi zmdi-arrow-right"></i></button>
        </form>
    </div>
</div>