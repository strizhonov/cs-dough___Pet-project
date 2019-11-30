<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<div class="creation-form">
    <div class="col-sm-6">
        <div class="image-holder">
            <img src="images/registration-form-1.jpg" alt="">
            <button class="for-logo"><fmt:message key="upload.logo"/></button>
        </div>
    </div>
    <div class="col-sm-6">
        <form class="register-form" action="${pageContext.request.contextPath}/app?command=create_organizer" method="post">
            <h3><fmt:message key="new.organizer.title"/></h3>
            <div class="form-group">
                <input name="name" type="text" placeholder="<fmt:message key="name"/>" class="form-control">
            </div>
            <button><fmt:message key="create"/><i class="zmdi zmdi-arrow-right"></i></button>
        </form>
    </div>
</div>