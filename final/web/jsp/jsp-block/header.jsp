<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="content"/>
<header class="site-header container">
    <div class="row">
        <div class="col-md-3">
            <div class="site-branding">
                <a href="#"><h1>CS:DOUGH</h1></a>
                <h6><fmt:message key="app.description"/></h6>
            </div>
        </div>
        <div class="col-md-9">
            <form action="${pageContext.request.contextPath}/app" method="post">
                <div class="languages">
                    <div>
                        Current (test): <fmt:message key="language"/>
                    </div>
                    <div>
                        <a href="${pageContext.request.contextPath}/app?command=change_language_to_en">en</a>
                    </div>
                    <div>
                        <a href="${pageContext.request.contextPath}/app?command=change_language_to_ru">ru</a>
                    </div>
                </div>
                <nav class="main-navigation">
                    <ul class="main-menu">
                        <li><a class="menu-button"
                               href="${pageContext.request.contextPath}/"><fmt:message key="home"/></a></li>
                        <c:if test="${sessionScope.userRole eq 'ADMIN'}">
                            <li><a class="menu-button"
                                   href="${pageContext.request.contextPath}/app?command=list_users"><fmt:message
                                    key="users"/></a></li>
                        </c:if>
                        <li><a class="menu-button"
                               href="${pageContext.request.contextPath}/app?command=list_games"><fmt:message
                                key="games"/></a>
                        </li>
                        <li><a class="menu-button"
                               href="${pageContext.request.contextPath}/app?command=list_tournaments"><fmt:message
                                key="tournaments"/></a>
                        </li>
                        <li><a class="menu-button"
                               href="${pageContext.request.contextPath}/app?command=list_players"><fmt:message
                                key="players"/></a>
                        </li>
                        <c:if test="${sessionScope.user == null}">
                            <li><a class="login-button"
                                   href="${pageContext.request.contextPath}/app?command=to_login_page"><fmt:message
                                    key="sign.in"/></a></li>
                        </c:if>
                        <c:if test="${sessionScope.user != null}">
                            <li><a href="${pageContext.request.contextPath}/app?command=to_user_page&id=${sessionScope.user.id}"> <img class="small-avatar"
                                     src="data:image/jpeg;base64,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"/></a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </form>
        </div>
    </div>
</header>

