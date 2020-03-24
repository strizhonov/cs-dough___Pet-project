<%@ page contentType="text/html;charset=utf-8"
         pageEncoding="utf-8"
         import="by.training.resourse.PathsContainer" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n"/>
<footer class="text-center">
    <div class="row">
        <div class="main-footer container">
            <ul class="contact">
                <li><a href="${PathsContainer.PATH_LINKED_IN}">LinkedIn</a></li>
                <li><a href="${PathsContainer.PATH_GITHUB}">Github</a></li>
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 copyright">
            <p><fmt:message key="copyright"/> &copy; 2019 <a
                    href="${PathsContainer.PATH_BITBUCKET}">CS:DOUGH</a></p>
        </div>
    </div>
</footer>