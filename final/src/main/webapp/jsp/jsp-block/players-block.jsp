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
                <h2 style="margin-bottom: 20px;"><fmt:message key="all.players"/>:</h2>
            </div>
            <table class="generic-table">
                <thead>
                <tr>
                    <th></th>
                    <th><fmt:message key="nickname"/></th>
                    <th><fmt:message key="total.won"/></th>
                    <th><fmt:message key="name"/></th>
                    <th><fmt:message key="surname"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="player" items="${requestScope.players}">
                    <tr>
                        <th>
                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_SHOW_PLAYER}${player.id}">
                                <img class="generic-table-img"
                                     src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_PLAYER_PHOTO}${player.id}"
                                     alt="">
                            </a>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_SHOW_PLAYER}${player.id}">${player.nickname}</a>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_SHOW_PLAYER}${player.id}">${player.totalWon}</a>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_SHOW_PLAYER}${player.id}">${player.name}</a>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_SHOW_PLAYER}${player.id}">${player.surname}</a>
                        </th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>


