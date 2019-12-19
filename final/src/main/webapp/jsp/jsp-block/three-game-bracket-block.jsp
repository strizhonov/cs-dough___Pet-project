<%@ page contentType="text/html;charset=utf-8"
         pageEncoding="utf-8"
         import="by.training.resourse.PathsContainer" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n"/>
<div class="container">
    <div class="row">
        <div class="col-sm-12" style="margin-bottom: 50px; height: 300px">
            <h2>Tournament Bracket</h2>
            <div class="col-sm-3">
            </div>
            <div class="col-sm-3">
                <div class="bracket-level">
                    <div style="text-align: center"><fmt:message key="semis"/></div>
                    <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${games[1].id}">
                        <div class="bracket-match-up">
                            <div class="bracket-team">
                                <div class="col-sm-9 bracket-name">
                                    <c:choose>
                                        <c:when test="${games[1].firstPlayer == null}">
                                            <fmt:message key="tbd"/>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="col-sm-3">
                                                <img class="generic-table-img"
                                                     src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_PLAYER_PHOTO}${games[1].firstPlayer.id}"
                                                     alt="">
                                            </div>
                                            <div class="col-sm-9">
                                                    ${games[1].firstPlayer.nickname}
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="col-sm-3 bracket-score"
                                     style="float: right">${games[1].gameServer.playerOnePoints}</div>
                            </div>
                            <div class="bracket-team " style="border-bottom: 3px solid #a1a1a1;">
                                <div class="col-sm-9 bracket-name">
                                    <c:choose>
                                        <c:when test="${games[1].secondPlayer == null}">
                                            <fmt:message key="tbd"/>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="col-sm-3">
                                                <img class="generic-table-img"
                                                     src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_PLAYER_PHOTO}${games[1].secondPlayer.id}"
                                                     alt="">
                                            </div>
                                            <div class="col-sm-9">
                                                    ${games[1].secondPlayer.nickname}
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="col-sm-3 bracket-score"
                                     style="float: right">${games[1].gameServer.playerTwoPoints}</div>
                            </div>
                        </div>
                    </a>
                    <div style="height: 40px"></div>
                    <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${games[2].id}">
                        <div class="bracket-match-up">
                            <div class="bracket-team ">
                                <div class="col-sm-9 bracket-name">
                                    <c:choose>
                                        <c:when test="${games[2].firstPlayer == null}">
                                            <fmt:message key="tbd"/>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="col-sm-3">
                                                <img class="generic-table-img"
                                                     src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_PLAYER_PHOTO}${games[2].firstPlayer.id}"
                                                     alt="">
                                            </div>
                                            <div class="col-sm-9">
                                                    ${games[2].firstPlayer.nickname}
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="col-sm-3 bracket-score"
                                     style="float: right">${games[2].gameServer.playerOnePoints}</div>
                            </div>
                            <div class="bracket-team " style="border-bottom: 3px solid #a1a1a1;">
                                <div class="col-sm-9 bracket-name">
                                    <c:choose>
                                        <c:when test="${games[2].secondPlayer == null}">
                                            <fmt:message key="tbd"/>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="col-sm-3">
                                                <img class="generic-table-img"
                                                     src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_PLAYER_PHOTO}${games[2].secondPlayer.id}"
                                                     alt="">
                                            </div>
                                            <div class="col-sm-9">
                                                    ${games[2].secondPlayer.nickname}
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="col-sm-3 bracket-score"
                                     style="float: right">${games[2].gameServer.playerTwoPoints}</div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="bracket-level">
                    <div style="text-align: center"><fmt:message key="final"/></div>
                    <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_GAME_PAGE}${games[0].id}">
                        <div class="bracket-match-up">
                            <div class="bracket-team ">
                                <div class="col-sm-9 bracket-name">
                                    <c:choose>
                                        <c:when test="${games[0].firstPlayer == null}">
                                            <fmt:message key="tbd"/>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="col-sm-3">
                                                <img class="generic-table-img"
                                                     src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_PLAYER_PHOTO}${games[0].firstPlayer.id}"
                                                     alt="">
                                            </div>
                                            <div class="col-sm-9">
                                                    ${games[0].firstPlayer.nickname}
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="col-sm-3 bracket-score"
                                     style="float: right">${games[0].gameServer.playerOnePoints}</div>
                            </div>
                            <div class="bracket-team " style="border-bottom: 3px solid #a1a1a1;">
                                <div class="col-sm-9 bracket-name">
                                    <c:choose>
                                        <c:when test="${games[0].secondPlayer == null}">
                                            <fmt:message key="tbd"/>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="col-sm-3">
                                                <img class="generic-table-img"
                                                     src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_PLAYER_PHOTO}${games[0].secondPlayer.id}"
                                                     alt="">
                                            </div>
                                            <div class="col-sm-9">
                                                    ${games[0].secondPlayer.nickname}
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="col-sm-3 bracket-score"
                                     style="float: right">${games[0].gameServer.playerTwoPoints}</div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-sm-3">
            </div>
        </div>
    </div>
</div>


