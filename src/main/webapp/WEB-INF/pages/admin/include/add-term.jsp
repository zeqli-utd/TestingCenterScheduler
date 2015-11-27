<h2>${newTerm}</h2>
<h2>Step One</h2>
<p>General Information</p>
<form>
    <div class="info-column">
        Open Time
        <label>
            <input type="time" class="input-info" name="open">
        </label>
        <input type="submit" class="submit-button" value="Change">
        Close Time
        <label>
            <input type="time" class="input-info" name="close">
        </label>
        <input type="submit" class="submit-button" value="Change">
    </div>
    <div class="info-column">
        Number of Seats
        <label>
            <input type="text" class="input-info" name="numSeats" placeholder="Number of Seats">
        </label>
        <input type="submit" class="submit-button" value="Change">
    </div>
    <div class="info-column">
        Number of Set-aside Seats
        <label>
            <input type="text" class="input-info" name="numSetAsideSeats" placeholder="Set Aside Seats">
        </label>
        <input type="submit" class="submit-button" value="Change">
    </div>
    <div class="info-column">
        Gap Time
        <label>
            <input type="text" class="input-info" name="gap" placeholder="minutes">
        </label>
        <input type="submit" class="submit-button" value="Change">
    </div>
    <div class="info-column">
        Reminder Interval
        <label>
            <input type="text" class="input-info" name="reminderInterval" placeholder="minutes">
        </label>
        <input type="submit" class="submit-button" value="Change">
    </div>
</form>