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
                    <h2 class="col-sm-12"><fmt:message key="user.profile"/></h2>
                </div>
                <div class="profile-avatar col-sm-5">
                    <img src="${pageContext.request.contextPath}/?command=get_user_photo&id=${sessionScope.user.id}"
                         alt="">
                    <div>
                        <div id="upload-btn" onclick="getFile()"><fmt:message
                                key="click.to.upload"/></div>
                        <div style='height: 0;width: 0; overflow:hidden;'>
                            <form id="upload"
                                  action="${pageContext.request.contextPath}/?command=upload_user_photo"
                                  method="POST"
                                  enctype='multipart/form-data'>
                                <input name="image" id="upfile" type="file" onchange="sub()"/>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="profile-info col-sm-6">
                    <div class="row">
                        <div>
                            <div class="profile-info-field col-sm-10 edit-one">
                                ${requestScope.user.username}
                                <span class="field-tooltip"><fmt:message
                                        key="username"/></span>
                            </div>
                            <div id="edit-name-btn" class="update-button col-sm-1 edit-one edit-btn-one">
                                <img src="${pageContext.request.contextPath}/img/edit.png" alt="">
                                <span class="button-tooltip"><fmt:message
                                        key="edit.username"/></span>
                            </div>
                            <form id="user-form"
                                  name="user-form"
                                  action="${pageContext.request.contextPath}/?command=change_username"
                                  method="POST">
                                <input id="username"
                                       class="profile-info-field col-sm-10 input-one"
                                       pattern="^[A-Za-z0-9]+$"
                                       type="text"
                                       placeholder="${requestScope.user.username}"
                                       oninput="checkUsername();"/>
                                <button type="submit" class="update-button col-sm-1 input-one input-btn"
                                        style="padding-top: 0;">
                                    <img src="${pageContext.request.contextPath}/img/submit.png" alt="">
                                    <span class="button-tooltip"><fmt:message
                                            key="submit"/></span>
                                </button>
                            </form>
                        </div>
                        <div>
                            <div class="profile-info-field col-sm-10 edit-two">
                                ${requestScope.user.email}
                                <span class="field-tooltip"><fmt:message
                                        key="email"/></span>
                            </div>
                            <div class="update-button col-sm-1 edit-two edit-btn-two">
                                <img src="${pageContext.request.contextPath}/img/edit.png" alt="">
                                <span class="button-tooltip"><fmt:message
                                        key="edit.email"/></span>
                            </div>
                            <form action="${pageContext.request.contextPath}/?command=change_email" method="POST">
                                <input class="profile-info-field col-sm-10 input-two"
                                       type="email"
                                       pattern="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}$"
                                       placeholder="${requestScope.user.email}">
                                <button type="submit" class="update-button col-sm-1 input-two input-btn"
                                        style="padding-top: 0;">
                                    <img src="${pageContext.request.contextPath}/img/submit.png" alt="">
                                    <span class="button-tooltip"><fmt:message
                                            key="submit"/></span>
                                </button>
                            </form>
                        </div>

                        <div class="col-sm-10" style="padding-left: 0; padding-right: 0">
                            <div class="profile-info-field col-sm-5">
                                ${requestScope.user.language}
                                <span class="field-tooltip"><fmt:message
                                        key="language"/></span>
                            </div>
                            <div class="dropdown col-sm-1">
                                <div class="update-button" style="float: left; padding: 15px">
                                    <img src="${pageContext.request.contextPath}/img/drop-down-arrow.png" alt="">
                                    <div class="dropdown-content">
                                        <a href="${pageContext.request.contextPath}/?command=change_language_to_en">EN</a>
                                        <a href=${pageContext.request.contextPath}/?command=change_language_to_ru">RU</a>
                                    </div>
                                </div>
                            </div>
                            <div class="profile-info-field col-sm-4" style="float: right;">
                                <div class="col-sm-6" style="text-align: right; padding-right: 3px">
                                    ${requestScope.user.balance}
                                </div>
                                <div class="col-sm-6" style="text-align: left; padding-left: 3px">
                                    ${requestScope.user.currency}
                                </div>
                                <span class="field-tooltip"><fmt:message
                                        key="balance"/></span>
                            </div>
                        </div>
                        <a href="${pageContext.request.contextPath}/?command=wallet_page">
                            <div class="update-button col-sm-1">
                                <img src="${pageContext.request.contextPath}/img/wallet.png" alt="">
                                <span class="button-tooltip"><fmt:message
                                        key="wallet.operation"/></span>
                            </div>
                        </a>

                        <div>
                            <a href="${pageContext.request.contextPath}/?command=player_page&id=${sessionScope.user.playerAccountId}">
                                <div class="profile-info-link col-sm-12">
                                    <fmt:message
                                            key="player.profile"/>
                                </div>
                            </a>
                        </div>
                        <div>
                            <a href="${pageContext.request.contextPath}/?command=organizer_page&id=${sessionScope.user.organizerId}">
                                <div class="profile-info-link col-sm-12">
                                    <fmt:message
                                            key="organizer.profile"/>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-1">
            </div>
        </div>
    </div>
</div>
