$('form .message a').click(function () {
    $('.register-form').animate({height: "toggle", opacity: "toggle"}, "slow");
    $('.login-form').animate({height: "toggle", opacity: "toggle"}, "slow");
});
