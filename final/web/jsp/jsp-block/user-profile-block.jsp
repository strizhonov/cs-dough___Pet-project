<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>

<div>
    <h4>User Profile</h4>
</div>
<div>
    <div>
        <img src="">
    </div>
    <div>
        <div>${requestScope.user_to_show.username}</div>
        <div>${requestScope.user_to_show.email}</div>
        <div>${requestScope.user_to_show.language}</div>
        <div>
            <div>${requestScope.wallet.balance}&nbsp;${requestScope.wallet.curency}</div>
            <div>
                <a href="">
                    <img src="wallet">
                </a>
            </div>
        </div>
    </div>
    <div>
        <button name="update">
            <a href="">
                <img src="">
            </a>
        </button>
        <button name="delete">
            <a href="">
                <img src="">
            </a>
        </button>
    </div>
</div>
<div>
    <div>
        <a href="">
            <img src="tournaments">
        </a>
    </div>
    <div>
        <a href="">
            <img src="games">
        </a>
    </div>
</div>

