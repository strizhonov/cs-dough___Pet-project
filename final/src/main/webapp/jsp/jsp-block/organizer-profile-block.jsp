<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="content"/>
<div class="container">
    <div class="row">
        <div class="profile-page-holder container">
            <div class="col-sm-2">
            </div>

            <div class="col-sm-8" style="margin-top: 30px">
                <div class="row" style="margin: 10px">

                    <h2 class="col-sm-12" style="margin-left: 0;"><fmt:message key="organizer.profile"/></h2>
                </div>
                <div class="row col-sm-12" style="margin: 0; padding: 0;">
                    <div class="org-logo col-sm-4">
                        <img src="${pageContext.request.contextPath}/?command=get_organizer_logo&id=${organizer.id}"
                             alt="">
                    </div>
                    <div class="col-sm-7" style="padding: 0; float: right;">
                        <div>
                            <div class="profile-info col-sm-11">
                                <div class="profile-info-field col-sm-12">
                                    ${organizer.name}
                                </div>
                            </div>
                        </div>
                    </div>

                </div>


            </div>

            <div class="col-sm-2">
            </div>
        </div>
        <div>
            ORGANIZER TOURNAMENTS
        </div>
    </div>
</div>