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
                      action="${pageContext.request.contextPath}${PathsContainer.COMMAND_CREATE_TOURNAMENT}"
                      method="POST"
                      enctype='multipart/form-data'>
                    <div class="row" style="margin: 0;">
                        <h2 class="col-sm-12"><fmt:message key="tournament.creation"/></h2>
                    </div>
                    <div class="avatar-block col-sm-5">
                        <img id="logo" src="${pageContext.request.contextPath}/img/def-tournament.png"
                             alt="">
                        <div>
                            <div id="upload-btn" onclick="getFile()"><fmt:message key="click.to.upload"/></div>
                            <input name="image" id="logo-input" type="file" style="display: none;"/>
                        </div>
                    </div>
                    <div class="info-block col-sm-6">
                        <script>
                            function checkName() {
                                var name = document.getElementById("name");
                                var submit = document.getElementById("submit");
                                var valid = new RegExp(name.getAttribute("pattern")).test(name.value);
                                if (valid) {
                                    name.setCustomValidity('');
                                    name.style.background = "";
                                } else {
                                    var message = "<fmt:message key="wrong.tournament.name"/>";
                                    name.setCustomValidity(message);
                                    name.style.background = "#d0968a";
                                    submit.click();
                                }
                            }
                        </script>
                        <input id="name"
                               name="name"
                               class="info-field col-sm-12"
                               pattern="${ValidationRegexp.TOURNAMENT_NAME_REGEXP}"
                               type="text"
                               placeholder="<fmt:message key="name"/>"
                               oninput="checkName();"/>
                        <input id="prize"
                               name="prize"
                               class="info-field col-sm-12"
                               type="number"
                               min="0" max="100"
                               step="0.1"
                               placeholder="<fmt:message key="organizer.reward"/>"/>
                        <input id="bonus"
                               name="bonus"
                               class="info-field col-sm-12"
                               type="number"
                               min="0"
                               step="0.1"
                               placeholder="<fmt:message key="organizer.bonus"/>"/>
                        <input id="buy_in"
                               name="buy_in"
                               class="info-field col-sm-12"
                               type="number"
                               step="0.1"
                               placeholder="<fmt:message key="buy.in.usd"/>"/>
                        <select required name="players_number" class="col-sm-12 players-number">
                            <option value="" disabled selected><fmt:message key="players.number"/></option>
                            <option value="2">2</option>
                            <option value="4">4</option>
                        </select>
                    </div>
                    <button id="submit" type="submit" class="long-button col-sm-6"
                            style="padding: 0; margin: 10px; float: right;">
                        <img src="${pageContext.request.contextPath}/img/icon/submit.png" alt="">
                        <span class="button-tooltip"><fmt:message key="submit"/></span>
                    </button>
                </form>
                <div class="col-sm-1">
                </div>
            </div>
        </div>
    </div>
</div>




