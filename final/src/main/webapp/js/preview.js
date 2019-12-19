document.getElementById("logo-input").onchange = function () {
    var reader = new FileReader();

    reader.onload = function (e) {
        document.getElementById("logo").src = e.target.result;
    };
};

function getFile() {
    document.getElementById('logo-input').click();
}