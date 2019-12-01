<div>
    <div>
        <img src="">
        <button>Upload logo</button>
    </div>
    <div class="col-sm-6">
        <form action="${pageContext.request.contextPath}/?command=create_tournament" method="post">
            <h3>New tournament creation form</h3>
            <div>
                <input name="name" type="text" placeholder="Tournament Name">
            </div>
            <div>
                <input name="prize_pool" type="text" placeholder="Prize Pool">
            </div>
            <div>
                <select name="players_number">
                    <option value="" disabled selected>Players number</option>
                    <option value="2">2</option>
                    <option value="4">4</option>
                    <option value="8">8</option>
                    <option value="16">16</option>
                    <option value="32">32</option>
                </select>
            </div>
            <div>
                <input name="start_date" type="date" placeholder="Start Date">
            </div>
            <div class="form-group">
                <input name="end_date" type="date" placeholder="End Date">
            </div>
            <button>Create</button>
        </form>
    </div>
</div>