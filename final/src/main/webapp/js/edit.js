$('.edit-btn-one').click(function(){
    $('.edit-one').animate({height: "toggle", opacity: "toggle"}, 0);
    $('.input-one').animate({height: "toggle", opacity: "toggle"}, 'slow');
});

$('.edit-btn-two').click(function(){
    $('.edit-two').animate({height: "toggle", opacity: "toggle"}, 0);
    $('.input-two').animate({height: "toggle", opacity: "toggle"}, 'slow');
});

function checkUsername() {
    var username = document.getElementById("username");
    var valid = new RegExp(username.getAttribute("pattern")).test(username.value);
    if (valid) {
        username.setCustomValidity('');
        username.style.background =  "";
    } else {
        username.setCustomValidity("Sorry, only letters and digits.");
        username.style.background =  "#6f4b44";
        username.click();
    }
}