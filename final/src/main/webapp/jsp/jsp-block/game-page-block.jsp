<%@ page contentType="text/html;charset=utf-8"
         pageEncoding="utf-8"
         import="by.training.resourse.PathsContainer" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n"/>
<div class="container">
    <div class="row">
        <div class="main-holder container">
            <div class="col-sm-12">
                <div class="row" style="margin: 0;">
                    <h2 class="col-sm-12" style="text-align: center"><fmt:message key="game.page"/></h2>
                </div>
                <div class="row" style="margin: 0;">
                    <div class="info-field col-sm-4">
                        <fmt:message key="game.starts"/>:&nbsp
                        <c:choose>
                            <c:when test="${game.startTime != null}">
                                ${game.startTime}
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="tbd"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-sm-4">
                        <c:if test="${user != null
                        && (user_type == 'ADMIN'
                        || user.organizerId == game.tournament.organizerId
                        || user.playerAccountId == game.firstPlayer.id
                        || user.playerAccountId == game.secondPlayer.id)}">
                            <script>
                                function hide() {
                                    var button = document.getElementById("server-button");
                                    var info = document.getElementById("server-info");
                                    button.style.display = "none";
                                    info.style.display = "block";
                                }
                            </script>
                            <button id="server-button" class="info-link col-sm-12" onclick="hide();">
                                <fmt:message key="get.server.path"/>
                            </button>
                            <div id="server-info" class="info-field col-sm-12" style="display: none;">
                                <fmt:message key="server.path"/>&nbsp:${game.gameServer.path}
                            </div>
                        </c:if>
                    </div>
                    <div class="col-sm-4">
                        <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_TOURNAMENT_PAGE}${game.tournament.id}">
                            <div class="info-link col-sm-12">
                                <fmt:message key="to.tournament"/>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="col-sm-4">
                            <div class="info-block" style="float: left">
                                <c:choose>
                                    <c:when test="${game.firstPlayer != null}">
                                        <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_SHOW_PLAYER}${game.firstPlayer.id}">
                                            <img style="max-height: 200px;"
                                                 src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_PLAYER_PHOTO}${game.firstPlayer.id}">
                                        </a>
                                        <div class="info-field">
                                                ${game.firstPlayer.nickname}
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <img style="max-height: 200px;"
                                             src="${pageContext.request.contextPath}/img/unknown.png">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="col-sm-1" style="padding-top: 70px">
                            <c:if test="${user_type == 'ADMIN' || game.tournament.organizerId == user.organizerId}">
                                <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_INCREASE_FIRST_PLAYER_COUNT}${game.id}">
                                    <div class="update-button col-sm-1" style="float: left">
                                        <img src="${pageContext.request.contextPath}/img/icon/arrow-up.png" alt="">
                                        <span class="button-tooltip"><fmt:message
                                                key="increase.first.player.count"/></span>
                                    </div>
                                </a>
                            </c:if>
                        </div>
                        <div class="col-sm-1">
                            <div style="font-size: 100px; float: right; margin: 15px">${game.gameServer.playerOnePoints}</div>
                        </div>
                        <div class="col-sm-1">
                            <div style="font-size: 100px; margin: 15px">${game.gameServer.playerTwoPoints}</div>
                        </div>
                        <div class="col-sm-1" style="padding-top: 70px">
                            <c:if test="${user_type == 'ADMIN' || game.tournament.organizerId == user.organizerId}">
                                <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_INCREASE_SECOND_PLAYER_COUNT}${game.id}">
                                    <div class="update-button col-sm-1" style="float: left;">
                                        <img src="${pageContext.request.contextPath}/img/icon/arrow-up.png" alt="">
                                        <span class="button-tooltip"><fmt:message
                                                key="increase.second.player.count"/></span>
                                    </div>
                                </a>
                            </c:if>
                        </div>
                        <div class="col-sm-4">
                            <div class="info-block" style="float: right">
                                <c:choose>
                                    <c:when test="${game.secondPlayer != null}">
                                        <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_SHOW_PLAYER}${game.secondPlayer.id}">
                                            <img style="max-height: 200px;"
                                                 src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_PLAYER_PHOTO}${game.secondPlayer.id}">
                                        </a>
                                        <div class="info-field">
                                                ${game.secondPlayer.nickname}
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <img style="max-height: 200px;"
                                             src="${pageContext.request.contextPath}/img/unknown.png">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="row" style="text-align: center; margin-bottom: 40px;">
    <iframe class="translation" src="${PathsContainer.PATH_FAKE_GAME_TRANSLATION}"></iframe>
</div>