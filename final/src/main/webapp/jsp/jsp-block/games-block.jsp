<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <div>
                <h2 style="margin-bottom: 20px;">Our events:</h2>
            </div>
            <div>
                <a href="">

                </a>
            </div>
            <table class="generic-table">
                <thead>
                <tr>
                    <th>STARTTIME</th>
                    <th>ENDTIME</th>
                    <th>AVA1</th>
                    <th>Player1 Nickname</th>
                    <th>Player1 count</th>
                    <th>Player2 count</th>
                    <th>Player2 Nickname</th>
                    <th>AVA2</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="game" items="${requestScope.games}">
                    <tr>
                        <th><a href="${pageContext.request.contextPath}/?command=to_game_page&id=${game.id}">STARTTIME</a></th>
                        <th><a href="${pageContext.request.contextPath}/?command=to_game_page&id=${game.id}">ENDTIME</a></th>
                        <th><a href="${pageContext.request.contextPath}/?command=to_game_page&id=${game.id}">AVA1</a></th>
                        <th><a href="${pageContext.request.contextPath}/?command=to_game_page&id=${game.id}">NICKNAME1</a></th>
                        <th><a href="${pageContext.request.contextPath}/?command=to_game_page&id=${game.id}">COUNT1</a></th>
                        <th><a href="${pageContext.request.contextPath}/?command=to_game_page&id=${game.id}">COUNT2</a></th>
                        <th><a href="${pageContext.request.contextPath}/?command=to_game_page&id=${game.id}">NICKNAME2</a></th>
                        <th><a href="${pageContext.request.contextPath}/?command=to_game_page&id=${game.id}">AVA2</a></th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</div>


