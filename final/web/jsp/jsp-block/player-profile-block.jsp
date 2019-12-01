<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>

<div>
    <div>
        <img src="${pageContext.request.contextPath}">
    </div>
    <div>
        LATEST_GAMES
    </div>
    <div>
        <a href="#">${requestScope.player.name} ${requestScope.player.surname}</a>
        <small>${requestScope.player.nickname}</small>

        <div>${requestScope.player.totalWon}</div>
    </div>
</div>
