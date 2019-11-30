<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<div class="container">
    <div class="home-page">
        <div class="row">
            <div class="col-md-6 col-sm-12">
                <div class="video-container">
                <div class="ongoing-tournaments">
                    <h2>LatestResults</h2>
<%--                    <iframe class="translation" src="https://player.twitch.tv/?channel=esl_csgo&muted=true&autoplay=false"></iframe>--%>
                </div>
                </div>
            </div>
            <form action="${pageContext.request.contextPath}/app" method="post">
            <div class="col-md-6 col-sm-12">
                <div class="row">
                    <div class="col-md-6 col-sm-6">
                        <div class="home-item">
                            <img src="${pageContext.request.contextPath}/img/1.jpg" alt="">
                            <a href="${pageContext.request.contextPath}/app?command=to_player_creation" class="overlay">
                                <h4><fmt:message key="become.player"/></h4>
                            </a>
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-6">
                        <div class="home-item">
                            <img src="${pageContext.request.contextPath}/img/2.jpg" alt="">
                            <a href="${pageContext.request.contextPath}/app?command=to_tournament_creation" class="overlay">
                            <h4><fmt:message key="create.tournament"/></h4>
                            </a>
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-6">
                        <div class="home-item">
                            <img src="${pageContext.request.contextPath}/img/3.jpg" alt="">
                            <a href="${pageContext.request.contextPath}/app?command=to_organizer_creation" class="overlay">
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
