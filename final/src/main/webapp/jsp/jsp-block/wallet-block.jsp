<%@ page contentType="text/html;charset=utf-8"
         pageEncoding="utf-8"
         import="by.training.resourse.PathsContainer" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language"
       value="${not empty language ? language : param.language ? param.language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n"/>
<div class="login-holder container">
    <c:if test="${requestScope.message != null}">
        <div class="notice">${requestScope.message}</div>
    </c:if>
    <div class="one-block-page">
        <div class="center-block">
            <div class="info-field">
                ${user.wallet.balance} ${user.wallet.currency}
            </div>
            <div>
                <form class="input-form" action="${pageContext.request.contextPath}${PathsContainer.COMMAND_DEPOSIT}"
                      method="post">
                    <input type="number" min="0" max="1000" step="0.1"
                           placeholder="<fmt:message key="value.to.deposit"/>" name="deposit"/>
                    <button><fmt:message key="deposit"/></button>
                </form>
            </div>
            <div>
                <form class="input-form" action="${pageContext.request.contextPath}${PathsContainer.COMMAND_WITHDRAW}"
                      method="post">
                    <input type="number" min="0" max="1000" step="0.1"
                           placeholder="<fmt:message key="value.to.withdraw"/>"
                           name="withdraw"/>
                    <button><fmt:message key="withdraw"/></button>
                </form>
            </div>
        </div>
    </div>
</div>
