$('form .message a').click(function(){
    $('.register-form').animate({height: "toggle", opacity: "toggle"}, "slow");
    $('.login-form').animate({height: "toggle", opacity: "toggle"}, "slow");
});


function checkUsername() {
    var username = document.getElementById("username");
    var valid = new RegExp(username.getAttribute("pattern")).test(username.value);
    if (valid) {
        username.setCustomValidity('');
        username.style.background =  "";
    } else {
        username.setCustomValidity("Sorry, only letters and digits.");
        username.style.background =  "#d0968a";
        username.click();
    }
}

function checkPassword() {
    var password = document.getElementById("password");
    var valid = new RegExp(password.getAttribute("pattern")).test(password.value);
    if (valid) {
        password.setCustomValidity('');
        password.style.background =  "";
    } else {
        password.setCustomValidity("6 characters at least; letters, digits or !@#$%^&");
        password.style.background =  "#d0968a";
        password.click();
    }
}

function checkPasswordsEquality() {
    var pass = document.getElementById("password");
    var passConfirmation = document.getElementById("pass-confirmation");
    if (pass.value === passConfirmation.value) {
        passConfirmation.setCustomValidity('');
    } else {
        passConfirmation.setCustomValidity("Passwords don't match.");
    }
}

function checkEmail() {
    var email = document.getElementById("email");
    var valid = new RegExp(email.getAttribute("pattern")).test(email.value);
    if (valid) {
        email.setCustomValidity('');
        email.style.background =  "";
    } else {
        email.setCustomValidity("Wrong email, check your input.");
        email.style.background =  "#d0968a";
        email.click();
    }
}

