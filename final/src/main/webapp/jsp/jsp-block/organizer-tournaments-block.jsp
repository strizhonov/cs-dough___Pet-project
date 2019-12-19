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
                <h2 style="margin-bottom: 20px;">${organizer.name}&nbsp<fmt:message key="events"/>:</h2>
            </div>
            <table class="generic-table">
                <thead>
                <tr>
                    <th></th>
                    <th><fmt:message key="name"/></th>
                    <th><fmt:message key="prize.pool.usd"/></th>
                    <th><fmt:message key="buy.in.usd"/></th>
                    <th><fmt:message key="slots"/></th>
                    <th><fmt:message key="registered"/></th>
                    <th><fmt:message key="status"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="tournament" items="${requestScope.tournaments}">
                    <tr>
                        <th>
                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_TOURNAMENT_PAGE}${tournament.id}">
                                <img class="generic-table-img"
                                     src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_TOURNAMENT_LOGO}${tournament.id}"
                                     alt="">
                            </a>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_TOURNAMENT_PAGE}${tournament.id}">${tournament.name}</a>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_TOURNAMENT_PAGE}${tournament.id}">${tournament.prizePool}</a>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_TOURNAMENT_PAGE}${tournament.id}">${tournament.buyIn}</a>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_TOURNAMENT_PAGE}${tournament.id}">${tournament.playersNumber}</a>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_TOURNAMENT_PAGE}${tournament.id}">${tournament.participantsIds.size()}</a>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_TOURNAMENT_PAGE}${tournament.id}">${tournament.status}</a>
                        </th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>


