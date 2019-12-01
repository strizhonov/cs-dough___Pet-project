<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <h2>Users:</h2>
    <table>
        <thead>
            <tr>
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

