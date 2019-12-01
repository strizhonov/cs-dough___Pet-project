<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="content"/>
<div class="row">
    <div class="image-holder col-md-6">
        <img src="?command=get_picture&game_id=${game.id}&team_id${player.id}" alt="">
    </div>
    <div>
        <div><fmt:message key="nickname"/></div>
        <div><fmt:message key="first.name"/></div>
        <div><fmt:message key="surname"/></div>
        <div></div>
    </div>
</div>
        

<div class="row">
    <form action="?command=update_game&game_id=${game.id}" class="col-md-6">
        <div name="start_time"><fmt:message key="start.time"/> ${game.startTime}</div>
        <div>
            <button><img src="" alt=""><button>
        </div>
    </div>   
</div>

<div class="row">
    <button>
        <fmt:message key="add.point"/>
    </button >
    <button>
        <fmt:message key="add.point"/>
    </button>
</div>


<div class="row">
    <button>
        <fmt:message key="get.server"/>
    </button>
    <button class="game-button">
        <fmt:message key="translation"/>
    </button>
</div>