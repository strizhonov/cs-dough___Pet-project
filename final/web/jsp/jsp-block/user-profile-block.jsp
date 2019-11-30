<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<div class="container">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-heading"><h4>User Profile</h4></div>
            <div class="panel-body">
                <div class="col-md-4 col-xs-12 col-sm-6 col-lg-4">
                    <img alt="User Pic"
                         src="https://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg"
                         id="profile-image1" class="img-circle img-responsive">
                </div>
                <div class="col-md-8 col-xs-12 col-sm-6 col-lg-8">
                    <div class="container">
                        <div class="form-control">${requestScope.user_to_show.username}</div>
                        <div class="form-control">${requestScope.user_to_show.email}</div>
                        <div class="form-control">${requestScope.user_to_show.language}</div>
                        <div class="form-control">${requestScope.wallet.balance}&nbsp;${requestScope.wallet.curency}</div>
                        <div class="form-control">${requestScope.playerName}</div>
                        <div class="form-control">${requestScope.organizerName}</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

