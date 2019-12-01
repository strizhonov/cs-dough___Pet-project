<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<div>
    <h3><fmt:message key="new.organizer.title"/></h3>
    <div> 
        <img src="" alt="">
        <button><fmt:message key="upload.logo"/></button>
    </div>
    <div>
        <form action="${pageContext.request.contextPath}/?command=create_organizer" method="post">
            <div>
                <input name="name" type="text" placeholder="<fmt:message key="name"/>">
                <button><fmt:message key="create"/><i class="zmdi zmdi-arrow-right"></i></button>
            </div>
        </form>
    </div>
</div>