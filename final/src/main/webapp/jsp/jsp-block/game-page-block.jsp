<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="content"/>
<div class="container">
    <div class="row">
        <div class="profile-page-holder container">
            <div class="col-sm-12">
                <div class="row" style="margin: 0;">
                    <h2 class="col-sm-12"><fmt:message key="game"/></h2>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="col-sm-6">
                            <div style="font-size: 40px; float: right; margin: 15px">0</div>
                        </div>
                        <div class="col-sm-6">
                            <div style="font-size: 40px; margin: 15px">0</div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-6">
                        <div class="profile-avatar col-sm-5">
                            <img id="logo" src="" alt="">
                            AVATAR1
                        </div>
                        <div class="profile-info col-sm-5">
                            <div class="profile-info-field col-sm-12">
                                <fmt:message key="name"/>
                            </div>
                            <div class="profile-info-field col-sm-12">
                                <fmt:message key="surname"/>
                            </div>
                            <div class="profile-info-field col-sm-12">
                                <fmt:message key="nickname"/>
                            </div>
                            <div class="profile-info-field col-sm-12">
                                <fmt:message key="country"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="profile-info col-sm-5" style="float: left;">
                            <div class="profile-info-field col-sm-12">
                                <fmt:message key="name"/>
                            </div>
                            <div class="profile-info-field col-sm-12">
                                <fmt:message key="surname"/>
                            </div>
                            <div class="profile-info-field col-sm-12">
                                <fmt:message key="nickname"/>
                            </div>
                            <div class="profile-info-field col-sm-12">
                                <fmt:message key="country"/>
                            </div>
                        </div>
                        <div class="profile-avatar col-sm-5">
                            <img id="logo1" src="" alt="">
                            AVATAR2
                        </div>
                    </div>
                </div>


            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">

            <div class="col-sm-2">
            </div>
            <div class="col-sm-8">
                <div class="update-button col-sm-1" style="float: left">
                    <img src="${pageContext.request.contextPath}/img/arrow-up.png" alt="">
                    <span class="button-tooltip"><fmt:message key="increase.first.player.count"/></span>
                </div>
                <div class="col-sm-10">
                    <div class="col-sm-3">
                    </div>
                    <div class="col-sm-6 profile-info-link">
                        <a href="">
                            <fmt:message key="get.server"/>
                        </a>
                    </div>
                    <div class="col-sm-3">
                    </div>
                </div>

                <div class="update-button col-sm-1" style="float: left;">
                    <img src="${pageContext.request.contextPath}/img/arrow-up.png" alt="">
                    <span class="button-tooltip"><fmt:message key="increase.first.player.count"/></span>
                </div>
            </div>
            <div class="col-sm-2">
            </div>
        </div>
    </div>

</div>
<div class="row">
    <div class="col-sm-3">

    </div>
    <div class="col-sm-6">
        <iframe class="translation" src="https://player.twitch.tv/?channel=esl_csgo&muted=true&autoplay=false"></iframe>
    </div>
    <div class="col-sm-3">

    </div>
</div>

</div>