<%@ page contentType="text/html;charset=utf-8"
         pageEncoding="utf-8"
         import="by.training.resourse.PathsContainer" %>
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
            <div class="col-sm-8">
                <div class="row">
                    <h2 style="margin: 35px 35px 35px 25px;"><fmt:message key="organizer"/>&nbsp${organizer.name}</h2>
                </div>
                <div class="row col-sm-12" style="margin: 0; padding: 0;">
                    <div class="org-logo col-sm-4">
                        <img src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_ORGANIZER_LOGO}${organizer.id}"
                             alt="">
                    </div>
                    <div class="col-sm-7" style="padding: 0; float: right;">
                        <div>
                            <div class="info-block col-sm-11">
                                <c:if test="${organizer.id == user.organizerId}">
                                    <div>
                                        <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_ORGANIZER_EDITING}">
                                            <div class="info-link col-sm-12">
                                                <fmt:message key="edit.organizer"/>
                                            </div>
                                        </a>
                                    </div>
                                    <div>
                                        <a href="${pageContext.request.contextPath}/jsp/tournament-creation.jsp">
                                            <div class="info-link col-sm-12">
                                                <fmt:message key="create.tournament"/>
                                            </div>
                                        </a>
                                    </div>
                                </c:if>
                                <div>
                                    <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_ORGANIZER_TOURNAMENTS}${organizer.id}">
                                        <div class="info-link col-sm-12" style="margin-bottom: 0">
                                            <fmt:message key="organizer.tournaments"/>
                                        </div>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
            </div>
        </div>
    </div>
</div>