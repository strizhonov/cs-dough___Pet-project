<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <div>
        <h2></h2>
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
                <th>Nickname</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="participant" items="${requestScope.participants}">
                <tr>
                    <th><a href=""></a></th>
                    <th><a href="${pageContext.request.contextPath}/?command=to_player_page&id=${participant.id}">${participant.nickname}</a></th>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>