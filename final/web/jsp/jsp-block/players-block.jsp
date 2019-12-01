<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>

<div>
    <div>
        <div>
            <h2>Players:</h2>
        </div>
        <div>
            <button>ADD</button>
        </div>
        <h2>Players:</h2>
        <table>
            <thead>
            <tr>
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
