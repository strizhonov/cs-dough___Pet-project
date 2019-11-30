<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="generic-list">
        <h2>Teams:</h2>
        <table class="generic-table">
            <thead>
            <tr class="generic-table-head">
                <th></th>
                <th>Team</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="userDto" items="${requestScope.userDto}">
                <tr>
                    <th>${userDto.name}</th>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
