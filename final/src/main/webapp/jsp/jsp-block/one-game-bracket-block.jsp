<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="content"/>
<html>
<head>
    <style>

        .bracket-level{
            margin: 30px;
        }


        .bracket-matchup{
            margin: auto;
            max-height: 50px;
            height: 90%;
            display: block;
            flex-direction: column;
            justify-content: space-between;

        }

        .bracket-team{
            height: 50%;
            width: 100%;
            background-color: #F5F5F5;
            box-shadow: rgba(0, 0, 0, 0.3) 0 1px 3px;
            display: flex;
            flex-direction: row;
            transition: all ease .5s;
        }

        .bracket-name{
            font-family: 'Open Sans', sans-serif;
            width: 75%;
            font-size: .75em;
            padding: .2em;
            line-height: 1.5em;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            color: #2B2B2B;
            text-align: center;
        }

        .bracket-score{
            font-family: 'Open Sans', sans-serif;
            font-size: .75em;
            padding: .2em;
            line-height: 1.5em;
            white-space: nowrap;
            overflow: hidden;
            color: #2B2B2B;
            text-align: center;
        }

        .winner>.bracket-name, .winner>.bracket-score{
            font-weight: bold;
            color: #D07030;
        }


    </style>
    <title></title>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-sm-12" style="margin-bottom: 50px">
            <h2>Tournament Bracket</h2>
            <div class="col-sm-4">

            </div>
            <div class="col-sm-4">

                <div class="bracket-level">
                    <div>FINAL:</div>
                    <a href="${pageContext.request.contextPath}/?command=to_game_page&id=${final_game.id}">
                        <div class="bracket-matchup">
                            <div class="bracket-team winner">
                                <div class="bracket-name">PLAYER1</div>
                                <div class="bracket-score">0</div>
                            </div>
                            <div class="bracket-team loser">
                                <div class="bracket-name">PLAYER2</div>
                                <div class="bracket-score">0</div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-sm-4">

            </div>
        </div>
    </div>

</div>
</body>
</html>

