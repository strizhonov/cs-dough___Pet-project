<%@ page contentType="text/html;charset=utf-8"
         pageEncoding="utf-8"
         import="by.training.resourse.PathsContainer" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n"/>
<div class="container">
    <div class="home-page">
        <div class="row">
            <div class="col-md-6 col-sm-12">
                <div class="ongoing-tournaments" style="padding: 0">
                    <div style="text-align: center;
                            width: 100%;
                            height: 140px;
                            margin: 0;
                            padding: 0;
                            background-image: url('${pageContext.request.contextPath}/img/home-banner.jpg');
                            background-repeat: no-repeat;
                            background-position: top;">
                    </div>
                    <div style="font-family: 'Montserrat', sans-serif;; font-size: 20px; padding:15px; text-align: center;">
                        <fmt:message key="welcome"/>
                    </div>
                    <c:if test="${requestScope.latest_games.size() > 0}">
                        <div style="padding: 30px 0; background: #3e4146;">
                            <h2><fmt:message key="latest.results"/></h2>
                            <table class="generic-table">
                                <tbody>
                                <c:forEach var="latest_game" items="${requestScope.latest_games}">
                                    <tr>
                                        <th>
                                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${latest_game.id}">
                                                <img class="generic-table-img"
                                                     src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_PLAYER_PHOTO}${latest_game.firstPlayer.id}"
                                                     alt="">
                                            </a>
                                        </th>
                                        <th>
                                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${latest_game.id}">${latest_game.firstPlayer.nickname}</a>
                                        </th>
                                        <th>
                                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${latest_game.id}">${latest_game.gameServer.playerOnePoints}</a>
                                        </th>
                                        <th>
                                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${latest_game.id}">&nbsp;&nbsp;</a>
                                        </th>
                                        <th>
                                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${latest_game.id}">${latest_game.gameServer.playerTwoPoints}</a>
                                        </th>
                                        <th>
                                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${latest_game.id}">${latest_game.secondPlayer.nickname}</a>
                                        </th>
                                        <th>
                                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${latest_game.id}">
                                                <img class="generic-table-img"
                                                     src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_PLAYER_PHOTO}${latest_game.secondPlayer.id}"
                                                     alt="">
                                            </a>
                                        </th>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>
            </div>
            <form action="${pageContext.request.contextPath}" method="post">
                <div class="col-md-6 col-sm-12">
                    <div class="row">
                        <div class="col-md-6 col-sm-6">
                            <div class="home-item">
                                <img src="${pageContext.request.contextPath}/img/home-1.jpg" alt="">
                                <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_PLAYER_CREATION}"
                                   class="overlay">
                                    <h4><fmt:message key="become.player"/></h4>
                                </a>
                            </div>
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <div class="home-item">
                                <img src="${pageContext.request.contextPath}/img/home-2.jpg" alt="">
                                <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_TOURNAMENT_CREATION}"
                                   class="overlay">
                                    <h4><fmt:message key="create.tournament"/></h4>
                                </a>
                            </div>
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <div class="home-item">
                                <img src="${pageContext.request.contextPath}/img/home-3.jpg" alt="">
                                <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_ORGANIZER_CREATION}"
                                   class="overlay">
                                    <h4><fmt:message key="become.organizer"/></h4>
                                </a>
                            </div>
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <div class="home-item">
                                <img src="${pageContext.request.contextPath}/img/home-4.jpg" alt="">
                                <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_LIST_TOURNAMENTS}"
                                   class="overlay">
                                    <h4><fmt:message key="join.tournament"/></h4>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>