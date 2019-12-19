<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n"/>
<div>
    <h2>Users:</h2>
    <table>
        <thead>
            <tr>
                <th></th>
                <th>Team</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="userDto" items="${requestScope.userDto}">
            <tr>
                    <th>${userDto.name}</th>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

