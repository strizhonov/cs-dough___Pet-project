<%@ page contentType="text/html;charset=utf-8"
         pageEncoding="utf-8"
         import="by.training.resourse.PathsContainer,
                 by.training.resourse.ValidationRegexp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language"
       value="${not empty language ? language : param.language ? param.language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n"/>
<div class="login-holder container">
    <div class="one-block-page">
        <div class="notice">${requestScope.message}</div>
        <div class="center-block">
            <form class="register-form" action="${pageContext.request.contextPath}${PathsContainer.COMMAND_SIGN_UP}"
                  method="post">
                <script>
                    function checkUsername() {
                        var username = document.getElementById("username");
                        var submit = document.getElementById("submit");
                        var valid = new RegExp(username.getAttribute("pattern")).test(username.value);
                        if (valid) {
                            username.setCustomValidity('');
                            username.style.background = "";
                        } else {
                            var message = "<fmt:message key="wrong.username"/>";
                            username.setCustomValidity(message);
                            username.style.background = "#d0968a";
                            submit.click();
                        }
                    }
                </script>
                <input id="username"
                       name="username"
                       type="text"
                       placeholder="<fmt:message key="username"/>"
                       pattern="${ValidationRegexp.USERNAME_REGEXP}"
                       oninput="checkUsername();"/>
                <script>
                    function checkPassword() {
                        var password = document.getElementById("password");
                        var submit = document.getElementById("submit");
                        var valid = new RegExp(password.getAttribute("pattern")).test(password.value);
                        if (valid) {
                            password.setCustomValidity('');
                            password.style.background = "";
                        } else {
                            var message = "<fmt:message key="wrong.password"/>";
                            password.setCustomValidity(message);
                            password.style.background = "#d0968a";
                            submit.click();
                        }
                    }
                </script>
                <input id="password"
                       name="password"
                       type="password"
                       placeholder="<fmt:message key="password"/>"
                       pattern="${ValidationRegexp.PASSWORD_REGEXP}"
                       oninput="return checkPassword();"/>
                <script>
                    function checkPasswordsEquality() {
                        var pass = document.getElementById("password");
                        var submit = document.getElementById("submit");
                        var passConfirmation = document.getElementById("password_confirmation");
                        if (pass.value !== passConfirmation.value) {
                            var message = "<fmt:message key="wrong.password.confirmation"/>";
                            passConfirmation.setCustomValidity(message);
                            passConfirmation.style.background = "#d0968a";
                            submit.click();
                        } else {
                            passConfirmation.setCustomValidity("");
                            passConfirmation.style.background = "";
                        }
                    }
                </script>
                <input id="password_confirmation"
                       name="password_confirmation"
                       type="password"
                       placeholder="<fmt:message key="password.confirmation"/>"
                       oninput="checkPasswordsEquality();"
                />
                <script>
                    function checkEmail() {
                        var email = document.getElementById("email");
                        var submit = document.getElementById("submit");
                        var valid = new RegExp(email.getAttribute("pattern")).test(email.value);
                        if (valid) {
                            email.setCustomValidity('');
                            email.style.background = "";
                        } else {
                            var message = "<fmt:message key="wrong.email"/>";
                            email.setCustomValidity(message);
                            email.style.background = "#d0968a";
                            submit.click();
                        }
                    }
                </script>
                <input id="email"
                       name="email"
                       type="text"
                       pattern="${ValidationRegexp.EMAIL_REGEXP}"
                       oninput="checkEmail();"
                       placeholder="<fmt:message key="email"/>"/>
                <button id="submit">Register</button>
                <p class="message">Already registered? <a href="#">Sign In</a></p>
            </form>
            <form class="login-form" action="${pageContext.request.contextPath}${PathsContainer.COMMAND_LOGIN}"
                  method="post">
                <input type="text" placeholder="<fmt:message key="username"/>" name="username"/>
                <input type="password" placeholder="<fmt:message key="password"/>" name="password"/>
                <button>Log In</button>
                <p class="message"><fmt:message key="not.registered"/>&nbsp;<a href="#"><fmt:message
                        key="create.account"/></a></p>
            </form>
        </div>
    </div>
</div>
