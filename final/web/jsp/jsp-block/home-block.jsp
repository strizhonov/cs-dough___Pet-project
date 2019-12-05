<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="content"/>
<div class="container">
    <div class="home-page">
        <div class="row">
            <div class="col-md-6 col-sm-12">
                <div class="video-container">
                <div class="ongoing-tournaments">
                    <h2><fmt:message key="latest.results"/></h2>
                    <c:forEach var="latest_game" items="${requestScope.latest_games}">
                        <tr>
                            <th><a href="${pageContext.request.contextPath}/?command=to_tournament_page&id=${latest_game.endTime}">${latest_game.endTime}</a></th>
                        </tr>
                    </c:forEach>
                </div>
                </div>
            </div>
            <form action="${pageContext.request.contextPath}" method="post">
            <div class="col-md-6 col-sm-12">
                <div class="row">
                    <div class="col-md-6 col-sm-6">
                        <div class="home-item">
                            <img src="${pageContext.request.contextPath}/img/1.jpg" alt="">
                            <a href="${pageContext.request.contextPath}/?command=to_player_creation" class="overlay">
                                <h4><fmt:message key="become.player"/></h4>
                            </a>
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-6">
                        <div class="home-item">
                            <img src="${pageContext.request.contextPath}/img/2.jpg" alt="">
                            <a href="${pageContext.request.contextPath}/?command=to_tournament_creation" class="overlay">
                            <h4><fmt:message key="create.tournament"/></h4>
                            </a>
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-6">
                        <div class="home-item">
                            <img src="${pageContext.request.contextPath}/img/3.jpg" alt="">
                            <a href="${pageContext.request.contextPath}/?command=to_organizer_creation" class="overlay">
                            <h4><fmt:message key="become.organizer"/></h4>
                            </a>
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-6">
                        <div class="home-item">
                            <img src="${pageContext.request.contextPath}/img/4.jpg" alt="">
                            <a href="#" class="overlay">
                                <h4><fmt:message key="join.tournament"/></h4>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            </form>
        </div>
    </div>
</div>
