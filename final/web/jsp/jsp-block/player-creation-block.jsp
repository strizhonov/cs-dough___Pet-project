<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="content"/>
<form id="upload"
      action="${pageContext.request.contextPath}/?command=create_player"
      method="POST"
      enctype='multipart/form-data'>
    <div class="container">
        <div class="row">
            <div class="profile-page-holder container">
                <div class="col-sm-1">
                </div>
                <div class="col-sm-10">
                    <div class="row" style="margin: 0;">
                        <h2 class="col-sm-12"><fmt:message key="player.creation"/></h2>
                    </div>
                    <div class="profile-avatar col-sm-5">
                        <img id="logo" src="${pageContext.request.contextPath}/img/nobody.jpg"
                             alt="">
                        <div>

                            <div id="upload-btn" onclick="getFile()"><fmt:message
                                    key="click.to.upload"/></div>
                            <input name="image" id="upfile" type="file" style="display: none;"/>
                        </div>
                    </div>

                    <div class="profile-info col-sm-6">
                        <input id="name"
                               name="name"
                               class="profile-info-field col-sm-12"
                               pattern="^[A-Za-z]+$"
                               type="text"
                               placeholder="<fmt:message key="name"/>"
                               oninput="checkName();"/>
                        <input id="surname"
                               name="surname"
                               class="profile-info-field col-sm-12"
                               pattern="^[A-Za-z]+$"
                               type="text"
                               placeholder="<fmt:message key="surname"/>"
                               oninput="checkName();"/>
                        <input id="nickname"
                               name="nickname"
                               class="profile-info-field col-sm-12"
                               pattern="^[A-Za-z0-9]+$"
                               type="text"
                               placeholder="<fmt:message key="nickname"/>"
                               oninput="checkNickame();"/>
                        <input id="country"
                               name="country"
                               class="profile-info-field col-sm-12"
                               pattern="^[A-Za-z]+$"
                               type="text"
                               placeholder="<fmt:message key="country"/>"
                               oninput="checkName();"
                               style="margin-bottom: 0;"/>
                    </div>
                    <button type="submit" class="long-button col-sm-6"
                            style="padding: 0; margin: 10px; float: right;">
                        <img src="${pageContext.request.contextPath}/img/submit.png" alt="">
                        <span class="button-tooltip"><fmt:message key="submit"/></span>
                    </button>
                </div>
                <div class="col-sm-1">
                </div>
            </div>
        </div>
    </div>
</form>