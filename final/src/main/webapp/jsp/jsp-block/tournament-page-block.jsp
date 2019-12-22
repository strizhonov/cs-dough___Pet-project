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
            <div class="col-sm-1">
            </div>
            <div class="col-sm-10">
                <div class="notice">${requestScope.message}</div>
                <div class="row">
                    <div class="col-sm-12" style="padding: 0">
                        <h2 style="margin-left: 0; margin-right: 0; padding-left: 15px">${tournament.name}&nbsp<fmt:message
                                key="tournament"/></h2>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <c:if test="${tournament.participantsIds.size() == 0}">
                            <div class="col-sm-1">
                                <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_DELETE_TOURNAMENT}${tournament.id}">
                                    <div class="update-button"
                                         style="width: 100%; text-align: center; padding: 5px;">
                                        <img src="${pageContext.request.contextPath}/img/icon/delete.png" alt=""
                                             style="max-width: available; max-height: 100%">
                                        <span class="button-tooltip"><fmt:message key="delete.tournament"/></span>
                                    </div>
                                </a>
                            </div>
                        </c:if>
                        <div class="col-sm-4">
                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_SHOW_PARTICIPANTS}${tournament.id}">
                                <div class="info-link" style="text-align: center;">
                                    <fmt:message key="participants"/>
                                </div>
                            </a>
                        </div>
                        <c:if test="${user.playerAccountId != 0}">
                            <c:choose>
                                <c:when test="${tournament.participantsIds.contains(user.playerAccountId) && tournament.status == 'UPCOMING'}">
                                    <div class="col-sm-3" style="float: right">
                                        <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_LEAVE_TOURNAMENT}${tournament.id}">
                                            <div class="join-button">
                                                <fmt:message key="leave"/>
                                            </div>
                                        </a>
                                    </div>
                                </c:when>
                                <c:when test="${!tournament.participantsIds.contains(user.playerAccountId) && tournament.status == 'UPCOMING'}">

                                    <div class="col-sm-3" style="float: right">
                                        <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_JOIN_TOURNAMENT}${tournament.id}">
                                            <div class="join-button">
                                                <fmt:message key="join"/>
                                            </div>
                                        </a>
                                    </div>
                                </c:when>
                            </c:choose>
                        </c:if>
                        <div class="col-sm-4">
                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_BRACKET_PAGE}${tournament.id}">
                                <div class="info-link" style="text-align: center;">
                                    <fmt:message key="bracket"/>
                                </div>
                            </a>
                        </div>

                    </div>
                </div>
                <div class="avatar-block col-sm-5" style="margin-left: 0">
                    <img src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_TOURNAMENT_LOGO}${tournament.id}"
                         alt="">
                </div>
                <div class="info-block col-sm-6">
                    <div class="info-field col-sm-12">
                        <fmt:message key="prize.pool.usd"/>:&nbsp${tournament.prizePool}
                    </div>
                    <div class="info-field col-sm-12">
                        <fmt:message key="buy.in.usd"/>:&nbsp${tournament.buyIn}
                    </div>
                    <div class="info-field col-sm-12">
                        <fmt:message key="slots"/>:&nbsp${tournament.playersNumber}
                    </div>
                    <div class="info-field col-sm-12">
                        <fmt:message key="registered"/>:&nbsp${tournament.participantsIds.size()}
                    </div>
                    <div class="info-field col-sm-12">
                        <fmt:message key="status"/>:&nbsp${tournament.status}
                    </div>
                    <div class="info-field col-sm-12">
                        <fmt:message key="organizer"/>:&nbsp<a
                            href="${pageContext.request.contextPath}${PathsContainer.COMMAND_SHOW_ORGANIZER}${organizer.id}">${organizer.name}</a>
                    </div>
                </div>
            </div>
            <div class="col-sm-1">
            </div>
        </div>
    </div>
</div>

