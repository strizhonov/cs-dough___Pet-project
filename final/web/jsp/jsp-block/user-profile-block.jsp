<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<div class="container">
    <div class="col-md-6">
        <h4>User Profile</h4>
    </div>
    <div class="row">
        <div class="col-md-6">
            <img src="">IMG
        </div>
        <div class="col-md-6">
            <div>${requestScope.user_to_show.username}AAA</div>
            <div>${requestScope.user_to_show.email}AAA</div>
            <div>${requestScope.user_to_show.language}AAA</div>
            <div>
                <div>${requestScope.wallet.balance}&nbsp;${requestScope.wallet.curency}AAA</div>
                <div>
                    <a href="">
                        <img src="wallet">AAA
                    </a>
                </div>
            </div>
        </div>
        <div>
            <button name="update">
                <a href="">
                    <img src="">AAA
                </a>
            </button>
            <button name="delete">
                <a href="">
                    <img src="">AAA
                </a>
            </button>
        </div>
    </div>
    <div class="row">
        <div>
            <a href="">
                <img src="tournaments">AAA
            </a>
        </div>
        <div>
            <a href="">
                <img src="games">AAA
            </a>
        </div>
    </div>
</div>
