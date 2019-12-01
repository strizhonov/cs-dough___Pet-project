<div class="creation-form">
    <div class="col-sm-6">
        <div class="image-holder">
            <img src="images/registration-form-1.jpg" alt="">
            <button class="for-logo">Upload logo</button>
        </div>
    </div>
    <div class="col-sm-6">
        <form class="register-form" action="${pageContext.request.contextPath}/?command=create_tournament" method="post">
        <h3>New tournament creation form</h3>
            <div class="form-group">
                <input name="name" type="text" placeholder="Tournament Name" class="form-control">
            </div>
            <div class="form-group">
                <input name="prize_pool" type="text" placeholder="Prize Pool" class="form-control">
            </div>
            <div class="form-wrapper">
                <select name="players_number" class="form-control">
                    <option value="" disabled selected>Players number</option>
                    <option value="2">2</option>
                    <option value="4">4</option>
                    <option value="8">8</option>
                    <option value="16">16</option>
                    <option value="32">32</option>
                </select>
            </div>
            <div class="form-wrapper">
                <select name="game_type" class="form-control">
                    <option value="" disabled selected>Default Game Type (You'll be able to change every game type individually later)</option>
                    <option value="BO1">BO1</option>
                    <option value="BO3">BO3</option>
                </select>
            </div>
            <div class="form-group">
                <input name="start_date" type="date" placeholder="Start Date" class="form-control">
            </div>
            <div class="form-group">
                <input name="end_date" type="date" placeholder="End Date" class="form-control">
            </div>
            <button>Create<i class="zmdi zmdi-arrow-right"></i></button>
        </form>
    </div>
</div>