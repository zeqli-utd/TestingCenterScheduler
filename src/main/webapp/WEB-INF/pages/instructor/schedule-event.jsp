<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Testing Center Scheduler</title>
    <link rel="stylesheet" href="assets/css/styles.css" type="text/css">
</head>
<body>
<div class="header-wrapper">
    <div class="header-logo">
        <a href="https://it.stonybrook.edu/services/testing-center" class="button">
            <h1>Stony Brook Testing Center</h1>
        </a>
    </div>
    <div class="main-heading">
        <h1>${heading}</h1>
    </div>
    <div class="header-menu">
        <a>Logout</a>
        <a>User <i class="fa fa-chevron-down"></i></a>
    </div>
</div>
<div class="main-wrapper">
    <div class="container">
        <div class="sidebar">
            <jsp:include page="sidebar.jsp"/>
        </div>
        <div class="content">
            <form class="edit-info" action="schedule-event/submit">
                <ul>
                    <li>Instructor NetID <input class="input-info" type="text" placeholder="NetID" name="instructor_id">
                    </li>
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
        </div>
    </div>
</div>

<div class="popup-overlay" id="popup1">
    <div class="popup">
        <a class="close" href="#"><i class="fa fa-times"></i></a>
        <jsp:include page="${popup_content}"/>
    </div>
</div>

<footer>

</footer>
</body>
</html>
