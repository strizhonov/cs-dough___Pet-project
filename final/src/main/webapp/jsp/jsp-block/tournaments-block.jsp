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
                        <th><a href="${pageContext.request.contextPath}/?command=to_tournament_page&id=${tournament.id}">AVA</a></th>
                        <th><a href="${pageContext.request.contextPath}/?command=to_tournament_page&id=${tournament.id}">${tournament.name}</a></th>
                        <th><a href="${pageContext.request.contextPath}/?command=to_tournament_page&id=${tournament.id}">${tournament.prizePool}</a></th>
                        <th><a href="${pageContext.request.contextPath}/?command=to_tournament_page&id=${tournament.id}">${tournament.status}</a></th>
                        <th><a href="${pageContext.request.contextPath}/?command=to_tournament_page&id=${tournament.id}">2</a></th>
                        <th><a href="${pageContext.request.contextPath}/?command=to_tournament_page&id=${tournament.id}">2</a></th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</div>


