<form class="edit-info" action="schedule-event/submit">
    <ul>
        <li>Instructor NetID <input class="input-info" type="text" placeholder="NetID" name="instructor_id"></li>
        <li>Term <input class="input-info" type="text" placeholder="Term" name="term"></li>
        <li>Reservation Type
            <label>
                <select name="type">
                    <option value="Course">Course</option>
                    <option value="Other">Other</option>
                </select>
            </label>
        </li>
        <li>Exam Name
            <input class="input-info" type="text" placeholder="Exam Name" name="examName">
        </li>
        <li>Number of Attendees
            <input class="input-info" type="number" placeholder="Number of attendee" name="capacity">
        </li>
        <li>Start Date Time
            <input class="input-info" type="datetime" placeholder="Start Time" name="startDateTime">
        </li>
        <li>End Date Time
            <input class="input-info" type="datetime" placeholder="End Time" name="endDateTime">
        </li>
        <li>Duration
            <input class="input-info" type="number" placeholder="Duration" name="duration">
        </li>
        <li>Instructor NetID
            <input class="input-info" type="text" placeholder="NetID" name="instructorId">
        </li>
        <li>Course
            <input class="input-info" type="text" placeholder="Course ID" name="courseId">
        </li>
    </ul>
    <div class="info-column"><input type="submit" class="submit-button" value="Submit"></div>
</form>