<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="content"/>
<div class="row">
    <div class="wrapper col-md-6">
        <div class="inner">
            <div class="image-holder col-md-6">
                <img src="images/registration-form-1.jpg" alt="">
            </div>
            <form action="" class="col-md-6">
                <div class="form-group">
                    <!— <input type="text" placeholder="<fmt:message key="nickname"/>" class="form-control">-->
                    <!— <input type="text" placeholder="<fmt:message key="first.name"/>" class="form-control">-->
                    <!— <input type="text" placeholder="<fmt:message key="surname"/>" class="form-control">-->
                    <div class="form-control"><fmt:message key="nickname"/></div>
                    <div class="form-control"><fmt:message key="first.name"/></div>
                    <div class="form-control"><fmt:message key="surname"/></div>
                </div>
            </form>
        </div>
    </div>
    <div class="wrapper col-md-6">
        <div class="inner">
            <div class="image-holder col-md-6">
                <img src="images/registration-form-1.jpg" alt="">
            </div>
            form action="" class="col-md-6">
            <div class="form-group">
                <!— <input type="text" placeholder="<fmt:message key="nickname"/>" class="form-control">-->
                <!— <input type="text" placeholder="<fmt:message key="first.name"/>" class="form-control">-->
                <!— <input type="text" placeholder="<fmt:message key="surname"/>" class="form-control">-->
                <div class="form-control"><fmt:message key="nickname"/></div>
                <div class="form-control"><fmt:message key="first.name"/></div>
                <div class="form-control"><fmt:message key="surname"/></div>
            </div>
            </form>
        </div>
    </div>
</div>

<div class="row">
    <div class="info">Date, time</div>
    <div class="info">Result</div>
</div>

<div class="row buttons">
    <button class="game-button">GET SERVER PATH
        <i class="zmdi zmdi-arrow-right"></i>
    </button>
</div>
<div class="row buttons">
    <button class="game-button">INCREASE
        <i class="zmdi zmdi-arrow-right"></i>
    </button >
    <button class="game-button">INCREASE
        <i class="zmdi zmdi-arrow-right"></i>
    </button>
</div>
<div class="row buttons">
    <button class="game-button">WATCH TRANSLATION
        <i class="zmdi zmdi-arrow-right"></i>
    </button>
</div>