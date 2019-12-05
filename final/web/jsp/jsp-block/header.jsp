<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="content"/>
<header class="site-header container">
    <div class="row">
        <form action="${pageContext.request.contextPath}" method="post">
            <div class="col-md-3">
                <div class="site-branding">
                    <a href="${pageContext.request.contextPath}/?command=to_home_page"><h1>CS:DOUGH</h1></a>
                    <h6><fmt:message key="app.description"/></h6>
                </div>
            </div>
            <div class="col-md-9">
                <nav class="main-navigation">
                    <ul class="main-menu">
                        <li class="languages">
                            <a href="${pageContext.request.contextPath}/?command=change_language_to_<fmt:message
                                    key="lang"/>"><fmt:message
                                    key="lang"/></a>
                        </li>
                        <li><a class="menu-button"
                               href="${pageContext.request.contextPath}/?command=to_home_page"><fmt:message
                                key="home"/></a></li>
                        <c:if test="${sessionScope.userRole eq 'ADMIN'}">
                            <li><a class="menu-button"
                                   href="${pageContext.request.contextPath}/?command=list_users"><fmt:message
                                    key="users"/></a></li>
                        </c:if>
                        <li><a class="menu-button"
                               href="${pageContext.request.contextPath}/?command=list_games"><fmt:message
                                key="games"/></a>
                        </li>
                        <li><a class="menu-button"
                               href="${pageContext.request.contextPath}/?command=list_tournaments"><fmt:message
                                key="tournaments"/></a>
                        </li>
                        <li><a class="menu-button"
                               href="${pageContext.request.contextPath}/?command=list_players"><fmt:message
                                key="players"/></a>
                        </li>
                        <c:if test="${sessionScope.user == null}">
                            <li><a class="login-button"
                                   href="${pageContext.request.contextPath}/?command=to_login_page"><fmt:message
                                    key="sign.in"/></a></li>
                        </c:if>
                        <c:if test="${sessionScope.user != null}">
                                <li class="ico-dropdown">
                                        <img class="small-avatar" src="${pageContext.request.contextPath}/?command=get_user_photo&id=${sessionScope.user.id}" alt="">
                                        <div class="ico-dropdown-content">
                                            <a href="${pageContext.request.contextPath}/?command=to_user_page&id=${sessionScope.user.id}"><fmt:message
                                                    key="profile"/></a>
                                            <a href=${pageContext.request.contextPath}/?command=log_out"><fmt:message
                                                    key="log.out"/></a>
                                        </div>
                                    </a>

                                </li>
                        </c:if>
                    </ul>
                </nav>

            </div>
        </form>
    </div>
</header>

