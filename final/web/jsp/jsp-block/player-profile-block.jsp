<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<div>
    <div>
        <img src="${pageContext.request.contextPath}">
    </div>
    <div>
        <div>${requestScope.player.img}</div>
        <div>${requestScope.player.nickname}</div>
        <div>
            <div>${requestScope.player.name}</div>
            <div>${requestScope.player.surname}</div>
        </div>
        <div>${requestScope.player.totalWon}</div>
    </div>
</div>
<div>
    LATEST_GAMES
</div>
