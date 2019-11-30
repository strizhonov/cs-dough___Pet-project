<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="content"/>
<footer class="text-center">
    <div class="row">
        <div class="main-footer container">
            <ul class="contact">
                <li><a href="linked in">LinkedIn</a></li>
                <li><a href="https://github.com/strizhonov">Github</a></li>
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 copyright">
            <p><fmt:message key="copyright"/> &copy; 2019 <a href="https://bitbucket.org/strizhonov/java-web-training/src/master/final/">CS:DOUGH</a></p>
        </div>
    </div>
</footer>