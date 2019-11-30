<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<div class="container">
    <div class="generic-list">
        <h2>Players:</h2>
        <table class="generic-table">
            <thead>
            <tr class="generic-table-head">
                <th></th>
                <th><fmt:message key="players"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="player" items="${requestScope.players}">
                <tr>
                    <th>${player.name}</th>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
