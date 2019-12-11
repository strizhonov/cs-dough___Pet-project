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
                    <th>AVA</th>
                    <th>NICKNAME</th>
                    <th>TOTAL WON</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Country</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="player" items="${requestScope.players}">
                    <tr>
                        <th><a href="${pageContext.request.contextPath}/?command=show_player&id=${player.id}">AVA</a></th>
                        <th><a href="${pageContext.request.contextPath}/?command=show_player&id=${player.id}">NICKNAME</a></th>
                        <th><a href="${pageContext.request.contextPath}/?command=show_player&id=${player.id}">TOTALWON</a></th>
                        <th><a href="${pageContext.request.contextPath}/?command=show_player&id=${player.id}">NAME</a></th>
                        <th><a href="${pageContext.request.contextPath}/?command=show_player&id=${player.id}">SURNAME</a></th>
                        <th><a href="${pageContext.request.contextPath}/?command=show_player&id=${player.id}">COUNTRY</a></th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</div>


