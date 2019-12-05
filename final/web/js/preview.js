document.getElementById("logo-input").onchange = function () {
    var reader = new FileReader();

    reader.onload = function (e) {
        document.getElementById("logo").src = e.target.result;
    };

    reader.readAsDataURL(this.files[0]);
};
function getFile() {
    document.getElementById('logo-input').click();
}