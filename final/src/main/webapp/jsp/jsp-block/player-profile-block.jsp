<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="content"/>

<div class="container">
    <div class="row">
        <div class="profile-page-holder container">
            <div class="col-sm-1">
            </div>
            <div class="col-sm-10">
                <div class="row" style="margin: 0;">
                    <h2 class="col-sm-12"><fmt:message key="player.profile"/></h2>
                </div>
                <div class="profile-avatar col-sm-5">
                    <img src="${pageContext.request.contextPath}/?command=get_player_photo&id=${player.id}"
                         alt="">
                </div>
                <div class="profile-info col-sm-6">
                    <div class="profile-info-field col-sm-12">
                        ${player.nickname}
                    </div>
                    <div class="profile-info-field col-sm-12">
                        ${player.name}
                    </div>
                    <div class="profile-info-field col-sm-12">
                        ${player.surname}
                    </div>
                    <div class="profile-info-field col-sm-12">
                        ${player.country}
                    </div>
                    <div class="profile-info-field col-sm-12">
                        ${player.totalWon}, USD
                    </div>
                </div>
            </div>

        </div>
        <div class="row">
            LATEST_GAMES
        </div>
    </div>


</div>