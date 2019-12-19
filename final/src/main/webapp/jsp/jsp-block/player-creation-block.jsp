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
                <form id="upload"
                      action="${pageContext.request.contextPath}${PathsContainer.COMMAND_CREATE_PLAYER}"
                      method="POST"
                      enctype='multipart/form-data'
                >
                    <div class="row" style="margin: 0;">
                        <h2 class="col-sm-12"><fmt:message key="player.creation"/></h2>
                    </div>
                    <div class="avatar-block col-sm-5">
                        <img id="logo" src="${pageContext.request.contextPath}/img/nobody.jpg"
                             alt="">
                        <div>
                            <div id="upload-btn" onclick="getFile()"><fmt:message
                                    key="click.to.upload"/></div>
                            <input name="logo-input" id="logo-input" type="file" style="display: none;"/>
                        </div>
                    </div>
                    <div class="info-block col-sm-6">
                        <script>
                            function checkNickname() {
                                var nickname = document.getElementById("nickname");
                                var submit = document.getElementById("submit");
                                var valid = new RegExp(nickname.getAttribute("pattern")).test(nickname.value);
                                if (valid) {
                                    nickname.setCustomValidity('');
                                    nickname.style.background = "";
                                } else {
                                    var message = "<fmt:message key="wrong.nickname"/>";
                                    nickname.setCustomValidity(message);
                                    nickname.style.background = "#d0968a";
                                    submit.click();
                                }
                            }
                        </script>
                        <input id="nickname"
                               name="nickname"
                               class="info-field col-sm-12"
                               pattern="${ValidationRegexp.NICKNAME_REGEXP}"
                               type="text"
                               placeholder="<fmt:message key="nickname"/>"
                               oninput="checkNickname();"
                        />
                        <script>
                            function checkName() {
                                var name = document.getElementById("name");
                                var submit = document.getElementById("submit");
                                var valid = new RegExp(name.getAttribute("pattern")).test(name.value);
                                if (valid) {
                                    name.setCustomValidity('');
                                    name.style.background = "";
                                } else {
                                    var message = "<fmt:message key="wrong.player.name"/>";
                                    name.setCustomValidity(message);
                                    name.style.background = "#d0968a";
                                    submit.click();
                                }
                            }
                        </script>
                        <input id="name"
                               name="name"
                               class="info-field col-sm-12"
                               pattern="${ValidationRegexp.PLAYER_NAME_REGEXP}"
                               type="text"
                               placeholder="<fmt:message key="name"/>"
                               oninput="checkName();"/>
                        <script>
                            function checkSurname() {
                                var surname = document.getElementById("surname");
                                var submit = document.getElementById("submit");
                                var valid = new RegExp(surname.getAttribute("pattern")).test(surname.value);
                                if (valid) {
                                    surname.setCustomValidity('');
                                    surname.style.background = "";
                                } else {
                                    var message = "<fmt:message key="wrong.player.surname"/>";
                                    surname.setCustomValidity(message);
                                    surname.style.background = "#d0968a";
                                    submit.click();
                                }
                            }
                        </script>
                        <input id="surname"
                               name="surname"
                               class="info-field col-sm-12"
                               pattern="${ValidationRegexp.PLAYER_SURNAME_REGEXP}"
                               type="text"
                               placeholder="<fmt:message key="surname"/>"
                               oninput="checkSurname();"
                               style="margin-bottom: 0;"/>

                    </div>
                    <button id="submit" type="submit" class="long-button col-sm-6"
                            style="padding: 0; margin: 10px; float: right;">
                        <img src="${pageContext.request.contextPath}/img/icon/submit.png" alt="">
                        <span class="button-tooltip"><fmt:message key="submit"/></span>
                    </button>
                </form>
            </div>
            <div class="col-sm-1">
            </div>
        </div>
    </div>
</div>
