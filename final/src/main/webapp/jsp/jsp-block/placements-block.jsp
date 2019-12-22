<%@ page contentType="text/html;charset=utf-8"
         pageEncoding="utf-8"
         import="by.training.resourse.PathsContainer" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n"/>
<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <div>
                <h2 style="margin-bottom: 20px;"><fmt:message key="participants.prizes"/></h2>
            </div>
            <table class="generic-table">
                <thead>
                <tr>
                    <th>#</th>
                    <th><fmt:message key="players"/></th>
                    <th><fmt:message key="prize.usd"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="placement" items="${requestScope.placements}">
                    <tr
                            <c:if test="${placement.finished}">
                                style="border: 2px solid #3c763d"
                            </c:if>
                    >
                        <th>${placement.placementNumber}</th>
                        <th>
                            <c:forEach var="player" items="${placement.playersOnPosition}">
                                <c:if test="${player != null}">
                                    <div style="display: inline-block;">
                                        <a
                                           href="${pageContext.request.contextPath}${PathsContainer.COMMAND_SHOW_PLAYER}${player.id}">${player.nickname}&nbsp</a>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </th>
                        <th>${placement.prize}</th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>


