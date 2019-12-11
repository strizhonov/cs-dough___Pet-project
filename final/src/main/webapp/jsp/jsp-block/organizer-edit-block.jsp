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
            <form id="upload"
                  action="${pageContext.request.contextPath}/?command=create_organizer"
                  method="POST"
                  enctype='multipart/form-data'>
                <div class="col-sm-8" style="margin-top: 30px">
                    <div class="row" style="margin: 10px">

                        <h2 class="col-sm-12" style="margin-left: 0;"><fmt:message
                                key="organizer.creation"/></h2>
                    </div>
                    <div class="row col-sm-12" style="margin: 0; padding: 0;">
                        <div class="org-logo col-sm-4">
                            <img id="logo" src="${pageContext.request.contextPath}/img/blank-logo.jpg"
                                 alt="">
                            <div>
                                <div id="upload-btn" onclick="getFile()"><fmt:message key="click.to.upload"/></div>
                                <div style='height: 0;width: 0; overflow:hidden;'>
                                    <input name="image" id="logo-input" type="file"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-7" style="padding: 0; float: right;">
                            <div>
                                <div class="profile-info col-sm-11">
                                    <input id="username"
                                           class="profile-info-field col-sm-12"
                                           pattern="^[A-Za-z0-9]+$"
                                           type="text"
                                           placeholder="<fmt:message key="organizer.name"/>"
                                           oninput="checkUsername();"
                                           style="display: block; margin-bottom: 0;"/>
                                </div>
                                <button type="submit" class="long-button col-sm-11"
                                        style="padding: 0; margin: 10px; float: right;">
                                    <img src="${pageContext.request.contextPath}/img/submit.png" alt="">
                                    <span class="button-tooltip"><fmt:message key="submit"/></span>
                                </button>
                            </div>
                        </div>

                    </div>


                </div>
            </form>
            <div class="col-sm-2">
            </div>
        </div>
    </div>
</div>