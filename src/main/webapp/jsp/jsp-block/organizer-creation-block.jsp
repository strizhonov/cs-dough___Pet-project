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
            <div class="col-sm-2">
            </div>
            <form id="upload"
                  action="${pageContext.request.contextPath}${PathsContainer.COMMAND_CREATE_ORGANIZER}"
                  method="POST"
                  enctype='multipart/form-data'>
                <div class="col-sm-8" style="margin-top: 30px">
                    <div class="row" style="margin: 10px">
                        <h2 class="col-sm-12" style="margin-left: 0;"><fmt:message key="organizer.creation"/></h2>
                    </div>
                    <div class="row col-sm-12" style="margin: 0; padding: 0;">
                        <div class="org-logo col-sm-4">
                            <img id="logo" src="${pageContext.request.contextPath}/img/blank-logo.jpg"
                                 alt="">
                            <div>
                                <div id="upload-btn" onclick="getFile()"><fmt:message key="click.to.upload"/></div>
                                <div style='height: 0;width: 0; overflow:hidden;'>
                                    <input name="image" id="logo-input" type="file"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-7" style="padding: 0; float: right;">
                            <div>
                                <div class="info-block col-sm-11">
                                    <script>
                                        function checkName() {
                                            var name = document.getElementById("name");
                                            var submit = document.getElementById("submit");
                                            var valid = new RegExp(name.getAttribute("pattern")).test(name.value);
                                            if (valid) {
                                                name.setCustomValidity('');
                                                name.style.background = "";
                                            } else {
                                                var message = "<fmt:message key="wrong.organizer.name"/>";
                                                name.setCustomValidity(message);
                                                name.style.background = "#d0968a";
                                                submit.click();
                                            }
                                        }
                                    </script>
                                    <input id="name"
                                           name="name"
                                           class="info-field col-sm-12"
                                           pattern="${ValidationRegexp.ORGANIZER_NAME_REGEXP}"
                                           type="text"
                                           placeholder="<fmt:message key="organizer.name"/>"
                                           oninput="checkName();"
                                           style="display: block; margin-bottom: 0;"/>
                                </div>
                                <button id="submit" type="submit" class="long-button col-sm-11"
                                        style="padding: 0; margin: 10px; float: right;">
                                    <img src="${pageContext.request.contextPath}/img/icon/submit.png" alt="">
                                    <span class="button-tooltip"><fmt:message key="submit"/></span>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <div class="col-sm-2">
            </div>
        </div>
    </div>
</div>