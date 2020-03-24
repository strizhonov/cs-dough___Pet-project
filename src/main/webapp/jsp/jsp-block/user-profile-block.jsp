<%@ page contentType="text/html;charset=utf-8"
         pageEncoding="utf-8"
         import="by.training.resourse.PathsContainer,
                 by.training.resourse.ValidationRegexp" %>
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
                    <h2 class="col-sm-12"><fmt:message key="personal.info"/></h2>
                </div>
                <div class="avatar-block col-sm-5">
                    <img src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_USER_PHOTO}${sessionScope.user.id}"
                         alt="">
                    <div>
                        <script>
                            function getFile() {
                                document.getElementById('upfile').click();
                            }

                            function sub() {
                                document.getElementById('upload').submit();
                            }
                        </script>
                        <div id="upload-btn" onclick="getFile()"><fmt:message
                                key="click.to.upload"/></div>
                        <div style='height: 0;width: 0; overflow:hidden;'>
                            <form id="upload"
                                  action="${pageContext.request.contextPath}${PathsContainer.COMMAND_UPLOAD_USER_PHOTO}"
                                  method="POST"
                                  enctype='multipart/form-data'>
                                <input name="image" id="upfile" type="file" onchange="sub()"
                                       accept="image/png,image/gif,image/jpeg"/>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="info-block col-sm-6">
                    <div class="row">
                        <div>
                            <div class="info-field col-sm-10 edit-one">
                                ${sessionScope.user.username}
                                <span class="field-tooltip"><fmt:message
                                        key="username"/></span>
                            </div>


                            <div id="edit-name-btn" class="update-button col-sm-1 edit-one edit-btn-one">
                                <img src="${pageContext.request.contextPath}/img/icon/edit.png" alt="">
                                <span class="button-tooltip"><fmt:message
                                        key="edit.username"/></span>
                            </div>
                            <form id="user-form"
                                  name="user-form"
                                  action="${pageContext.request.contextPath}${PathsContainer.COMMAND_UPDATE_USERNAME}"
                                  method="POST">
                                <script>
                                    function checkUsername() {
                                        var username = document.getElementById("username");
                                        var submit = document.getElementById("submit");
                                        var valid = new RegExp(username.getAttribute("pattern")).test(username.value);
                                        if (valid) {
                                            username.setCustomValidity('');
                                            username.style.background = "";
                                        } else {
                                            var message = "<fmt:message key="wrong.username"/>";
                                            username.setCustomValidity(message);
                                            username.style.background = "#d0968a";
                                            submit.click();
                                        }
                                    }
                                </script>
                                <input id="username"
                                       name="username"
                                       class="info-field col-sm-10 input-one"
                                       pattern="${ValidationRegexp.USERNAME_REGEXP}"
                                       type="text"
                                       placeholder="${sessionScope.user.username}"
                                       oninput="checkUsername();"/>
                                <button id="submit" type="submit" class="update-button col-sm-1 input-one input-btn"
                                        style="padding-top: 0;">
                                    <img src="${pageContext.request.contextPath}/img/icon/submit.png" alt="">
                                    <span class="button-tooltip"><fmt:message key="submit"/></span>
                                </button>
                            </form>
                        </div>
                        <div>
                            <div class="info-field col-sm-10 edit-two">
                                ${sessionScope.user.email}
                                <span class="field-tooltip"><fmt:message key="email"/></span>
                            </div>
                            <div class="update-button col-sm-1 edit-two edit-btn-two">
                                <img src="${pageContext.request.contextPath}/img/icon/edit.png" alt="">
                                <span class="button-tooltip"><fmt:message key="edit.email"/></span>
                            </div>
                            <form action="${pageContext.request.contextPath}${PathsContainer.COMMAND_UPDATE_EMAIL}"
                                  method="POST">

                                <script>
                                    function checkEmail() {
                                        var email = document.getElementById("email");
                                        var submit = document.getElementById("submit_email");
                                        var valid = new RegExp(email.getAttribute("pattern")).test(email.value);
                                        if (valid) {
                                            email.setCustomValidity('');
                                            email.style.background = "";
                                        } else {
                                            email.setCustomValidity("<fmt:message key="wrong.email"/>");
                                            email.style.background = "#d0968a";
                                            submit.click();
                                        }
                                    }
                                </script>
                                <input id="email"
                                       class="info-field col-sm-10 input-two"
                                       type="email"
                                       name="email"
                                       pattern="${ValidationRegexp.EMAIL_REGEXP}"
                                       placeholder="${sessionScope.user.email}"
                                       oninput="checkEmail();">
                                <button id="submit_email" type="submit"
                                        class="update-button col-sm-1 input-two input-btn"
                                        style="padding-top: 0;">
                                    <img src="${pageContext.request.contextPath}/img/icon/submit.png" alt="">
                                    <span class="button-tooltip"><fmt:message key="submit"/></span>
                                </button>
                            </form>
                        </div>

                        <div class="col-sm-10" style="padding-left: 0; padding-right: 0">
                            <div class="info-field col-sm-4">
                                ${sessionScope.user.language}
                                <span class="field-tooltip"><fmt:message key="language"/></span>
                            </div>
                            <div class="dropdown col-sm-1">
                                <div id="arrow" class="update-button" style="float: left; padding: 15px">
                                    <img src="${pageContext.request.contextPath}/img/icon/drop-down-arrow.png" alt="">
                                    <div class="dropdown-content">
                                        <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_CHANGE_LANGUAGE}en">EN</a>
                                        <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_CHANGE_LANGUAGE}ru">RU</a>
                                    </div>
                                </div>
                            </div>
                            <div class="info-field col-sm-5" style="float: right;">
                                <div class="col-sm-6" style="text-align: right; padding-right: 3px">
                                    ${sessionScope.user.wallet.balance}
                                </div>
                                <div class="col-sm-6" style="text-align: left; padding-left: 3px">
                                    ${sessionScope.user.wallet.currency}
                                </div>
                                <span class="field-tooltip"><fmt:message key="balance"/></span>
                            </div>
                        </div>
                        <a href="${pageContext.request.contextPath}/jsp/wallet.jsp">
                            <div class="update-button col-sm-1">
                                <img src="${pageContext.request.contextPath}/img/icon/wallet.png" alt="">
                                <span class="button-tooltip"><fmt:message key="wallet.operation"/></span>
                            </div>
                        </a>

                        <div>
                            <c:choose>
                                <c:when test="${sessionScope.user.playerAccountId == 0}">
                                    <a href="${pageContext.request.contextPath}/jsp/player-creation.jsp">
                                        <div class="info-link col-sm-12">
                                            <fmt:message key="create.player.profile"/>
                                        </div>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_SHOW_PLAYER}${user.playerAccountId}">
                                        <div class="info-link col-sm-12">
                                            <fmt:message key="player.profile"/>
                                        </div>
                                    </a>
                                </c:otherwise>
                            </c:choose>

                        </div>
                        <div>
                            <c:choose>
                                <c:when test="${sessionScope.user.organizerId == 0}">
                                    <a href="${pageContext.request.contextPath}/jsp/organizer-creation.jsp">
                                        <div class="info-link col-sm-12">
                                            <fmt:message key="create.organizer.profile"/>
                                        </div>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_SHOW_ORGANIZER}${user.organizerId}">
                                        <div class="info-link col-sm-12">
                                            <fmt:message key="organizer.profile"/>
                                        </div>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-1">
            </div>
        </div>
    </div>
</div>
