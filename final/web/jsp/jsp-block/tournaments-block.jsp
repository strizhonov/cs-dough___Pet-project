<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="generic-list">
    <div>
        <h2>Our events:</h2>
    </div>
    <div>
        <a href="">
            <img src="add">
        </a>
    </div>
    <table>
        <thead>
            <tr>
                <th></th>
                <th>Tournament</th>
                <th>Prize pool</th>
                <th>Status</th>
                <th>Total Teams</th>
                <th>Teams Registered</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="tournament" items="${requestScope.tournaments}">
                <tr>
                    <th><a href="${pageContext.request.contextPath}/?command=to_tournament_page&id=${tournament.id}">${tournament.id}</a></th>
                    <th><a href="${pageContext.request.contextPath}/?command=to_tournament_page&id=${tournament.id}">${tournament.name}</a></th>
                    <th><a href="${pageContext.request.contextPath}/?command=to_tournament_page&id=${tournament.id}">${tournament.prizePool}</a></th>
                    <th><a href="${pageContext.request.contextPath}/?command=to_tournament_page&id=${tournament.id}">${tournament.status}</a></th>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
