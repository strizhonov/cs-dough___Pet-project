<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="content"/>

<div class="container">
    <div class="row">
        <div class="profile-page-holder container">
            <div class="col-sm-1">
            </div>
            <div class="col-sm-10">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="col-sm-4">
                            <h2 style="margin-left: 0; margin-right: 0;"><fmt:message key="tournament"/></h2>
                        </div>
                        <div class="col-sm-4" style="float: right">
                            <a href="${pageContext.request.contextPath}/?command=join_tournament&id=${tournament.id}">
                                <div class="join-button">
                                    <fmt:message key="join"/>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="profile-avatar col-sm-5">
                    <img src="${pageContext.request.contextPath}/?command=get_tournament_logo&id=${tournament.id}"
                         alt="">
                </div>
                <div class="profile-info col-sm-6">
                    <div class="profile-info-field col-sm-12">
                        ${tournament.name}
                    </div>
                    <div class="profile-info-field col-sm-12">
                        ${tournament.prizePool}
                    </div>
                    <div class="profile-info-field col-sm-12">
                        ${tournament.buyIn}
                    </div>
                    <div class="profile-info-field col-sm-12">
                        ${tournament.participantsIds.size()}
                    </div>
                    <div class="profile-info-field col-sm-12">
                        ${tournament.startDate}
                    </div>
                    <div class="profile-info-field col-sm-12">
                        ${tournament.endDate}
                    </div>
                    <div class="profile-info-field col-sm-12">
                        ${tournament.status}
                    </div>
                    <div class="profile-info-field col-sm-12">
                        ${organizer.name}
                    </div>
                </div>
            </div>

        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div class="col-sm-4">
                <a href="${pageContext.request.contextPath}/?command=show_participants&id=${tournament.id}">
                    <div class="profile-info-link">
                        <fmt:message key="participants"/>
                    </div>
                </a>
            </div>
            <div class="col-sm-4">
                <c:if test="${tournament.playersNumber == 2}">
                    <a href="${pageContext.request.contextPath}/?command=to_one_game_bracket&id=${tournament.id}">
                        <div class="profile-info-link">
                            <fmt:message key="bracket"/>
                        </div>
                    </a>
                </c:if>
            </div>
            <div class="col-sm-4">
                <a href="${pageContext.request.contextPath}/?command=show_prize_pool_distribution&id=${tournament.id}">
                    <div class="profile-info-link">
                        <fmt:message key="prize.pool.distribution"/>
                    </div>
                </a>
            </div>
        </div>


    </div>

</div>

