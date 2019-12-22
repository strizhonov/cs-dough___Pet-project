<%@ page contentType="text/html;charset=utf-8"
         pageEncoding="utf-8"
         import="by.training.resourse.PathsContainer" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n"/>
<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <div>
                <h2 style="margin-bottom: 20px;"><fmt:message key="games"/>:</h2>
            </div>
            <table class="generic-table">
                <thead>
                <tr>
                    <th><fmt:message key="tournament"/></th>
                    <th><fmt:message key="start.time"/></th>
                    <th></th>
                    <th><fmt:message key="first.player"/></th>
                    <th></th>
                    <th></th>
                    <th><fmt:message key="second.player"/></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="game" items="${requestScope.games}">
                    <c:if test="${game.firstPlayer != null && game.secondPlayer != null}">
                        <tr>
                            <th>
                                <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${game.id}">
                                    <img class="generic-table-img"
                                         src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_TOURNAMENT_LOGO}${game.tournament.id}"
                                         alt="">
                                </a>
                            </th>
                            <th>
                                <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${game.id}">
                                    <c:choose>
                                        <c:when test="${game.startTime == null}">
                                            <fmt:message key="tbd"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${game.startTime}" />
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                            </th>
                            <th>
                                <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${game.id}">
                                    <img class="generic-table-img"
                                         src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_PLAYER_PHOTO}${game.firstPlayer.id}">
                                </a>
                            </th>
                            <th>
                                <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${game.id}">${game.firstPlayer.nickname}</a>
                            </th>
                            <th>
                                <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${game.id}">${game.gameServer.playerOnePoints}</a>
                            </th>
                            <th>
                                <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${game.id}">${game.gameServer.playerTwoPoints}</a>
                            </th>
                            <th>
                                <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${game.id}">${game.secondPlayer.nickname}</a>
                            </th>
                            <th>
                                <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${game.id}">
                                    <img class="generic-table-img"
                                         src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_PLAYER_PHOTO}${game.secondPlayer.id}"
                                         alt="">
                                </a>
                            </th>
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>


