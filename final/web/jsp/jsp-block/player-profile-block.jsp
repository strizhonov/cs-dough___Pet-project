<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<div class="container">
    <div class="row">
        <div class="col-md-6">
                <div class="col-md-6">
                    <div class="our-team">
                        <div class="pic">
                            <img src="${pageContext.request.contextPath}/img/1.jpg" alt="">
                        </div>
                        <div class="latest-games">
                            LATEST_GAMES
                        </div>
                        <div class="team-prof">
                            <h3 class="post-title">
                                <a href="#">${requestScope.player.name} ${requestScope.player.surname}</a>
                                <small>${requestScope.player.nickname}</small>
                            </h3>
                            <div>${requestScope.player.totalWon}</div>
                        </div>
                    </div>
                </div>
        </div>
    </div>
</div>