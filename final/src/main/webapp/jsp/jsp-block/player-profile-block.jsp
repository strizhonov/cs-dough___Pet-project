<%@ page contentType="text/html;charset=utf-8"
         pageEncoding="utf-8"
         import="by.training.resourse.PathsContainer" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n"/>
<div class="container">
    <div class="row">
        <c:if test="${requestScope.message != null}">
            <div class="notice">${requestScope.message}</div>
        </c:if>
        <div class="main-holder container">
            <div class="col-sm-1">
            </div>
            <div class="col-sm-10">
                <div class="row" style="margin: 0;">
                    <h2 class="col-sm-12">${player.nickname}&nbsp<fmt:message key="profile"/></h2>
                </div>
                <div class="avatar-block col-sm-5">
                    <img src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_PLAYER_PHOTO}${player.id}"
                         alt="">
                </div>
                <div class="info-block col-sm-6">
                    <div class="info-field col-sm-12">
                        <fmt:message key="name"/>:&nbsp${player.name}
                    </div>
                    <div class="info-field col-sm-12">
                        <fmt:message key="surname"/>:&nbsp${player.surname}
                    </div>
                    <div class="info-field col-sm-12">
                        <fmt:message key="total.won"/>:&nbsp${player.totalWon}, USD
                    </div>
                    <div>
                        <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_LIST_PLAYER_GAMES}${player.id}">
                            <div class="info-link col-sm-12">
                                <fmt:message key="player.games"/>
                            </div>
                        </a>
                    </div>
                    <div>
                        <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_LIST_PLAYER_TOURNAMENTS}${player.id}">
                            <div class="info-link col-sm-12" style="margin-bottom: 0;">
                                <fmt:message key="player.tournaments"/>
                            </div>
                        </a>
                    </div>
                    <c:if test="${player.id == user.playerAccountId}">
                        <div>
                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_PLAYER_EDITING}">
                                <div class="info-link col-sm-12" style="margin-bottom: 0; margin-top: 20px;">
                                    <fmt:message key="edit.player"/>
                                </div>
                            </a>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>