<%@ page contentType="text/html;charset=utf-8"
         pageEncoding="utf-8"
         import="by.training.resourse.PathsContainer" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n"/>
<header class="site-header container">
    <div class="row">
        <form action="${pageContext.request.contextPath}" method="post">
            <div class="col-md-3">
                <div class="site-branding">
                    <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_HOME_PAGE}"><h1>CS:DOUGH</h1>
                    </a>
                    <h6><fmt:message key="app.description"/></h6>
                </div>
            </div>
            <div class="col-md-9">
                <nav class="main-navigation">
                    <ul class="main-menu">
                        <li class="languages">
                            <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_CHANGE_SESSION_LANGUAGE}<fmt:message key="lang"/>">
                                <fmt:message key="lang"/>
                            </a>
                        </li>
                        <li><a class="menu-button"
                               href="${pageContext.request.contextPath}${PathsContainer.COMMAND_TO_HOME_PAGE}"><fmt:message
                                key="home"/></a></li>
                        <li><a class="menu-button"
                               href="${pageContext.request.contextPath}${PathsContainer.COMMAND_LIST_GAMES}"><fmt:message
                                key="games"/></a>
                        </li>
                        <li><a class="menu-button"
                               href="${pageContext.request.contextPath}${PathsContainer.COMMAND_LIST_TOURNAMENTS}"><fmt:message
                                key="tournaments"/></a>
                        </li>
                        <li><a class="menu-button"
                               href="${pageContext.request.contextPath}${PathsContainer.COMMAND_LIST_PLAYERS}"><fmt:message
                                key="players"/></a>
                        </li>
                        <c:if test="${sessionScope.user == null}">
                            <li><a class="login-button"
                                   href="${pageContext.request.contextPath}/jsp/login.jsp"><fmt:message
                                    key="sign.in"/></a></li>
                        </c:if>
                        <c:if test="${sessionScope.user != null}">
                            <li class="ico-dropdown">
                                <img class="small-avatar"
                                     src="${pageContext.request.contextPath}${PathsContainer.COMMAND_GET_USER_PHOTO}${sessionScope.user.id}"
                                     alt="">
                                <div class="ico-dropdown-content">
                                    <a href="${pageContext.request.contextPath}/jsp/user-profile.jsp"><fmt:message
                                            key="profile"/></a>
                                    <a href="${pageContext.request.contextPath}${PathsContainer.COMMAND_LOG_OUT}"><fmt:message
                                            key="log.out"/></a>
                                </div>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </form>
    </div>
</header>

