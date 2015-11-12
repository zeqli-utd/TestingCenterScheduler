<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="reservation" type="core.event.Reservation"--%>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Testing Center Scheduler</title>
    <link rel="stylesheet" href="assets/css/styles.css"/>
</head>
<body>
<div class="header-wrapper">
    <div class="header-logo">
        <a href="https://it.stonybrook.edu/services/testing-center" class="button">
            <h1>Stony Brook Testing Center</h1>
        </a>
    </div>
    <div class="main-heading">
        <h1>Instructor Home</h1>
    </div>
    <div class="header-menu">
        <a>Logout</a>
        <a>User <i class="fa fa-chevron-down"></i></a>
    </div>
</div>
<div class="main-wrapper">
    <div class="container">
        <div class="sidebar">
            <ul class="side-nav">
                <li><a class="button" href="instructor-home"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a class="button" href="schedule-event"><i class="fa fa-#"></i>&nbsp;Schedule an Event</a></li>
                <li><a class="button" href="view-requests"><i class="fa fa-#"></i>&nbsp;Pending Requests</a></li>
                <li><a class="button" href="instructor-view-appointments"><i class="fa fa-#"></i>&nbsp;View appointments</a></li>
            </ul>
        </div>
        <div class="content">
            <form class="edit-info" action="schedule-event/submit">
                <ul>
                    <li>Instructor NetID <input class="input-info" type="text" placeholder="NetID" name="instructor_id"></li>
                    <li>Term <input class="input-info" type="text" placeholder="Term" name="term"></li>
                    <li>Reservation Type
                        <select name="type">
                            <option value="Course">Course</option>
                            <option value="Other">Other</option>
                        </select>
                    </li>
                    <li>Start Date Time <input class="input-info" type="datetime" placeholder="Start Time" name="start_date_time"></li>
                    <li>End Date Time <input class="input-info" type="datetime" placeholder="End Time" name="end_date_time"></li>
                    <li>Duration <input class="input-info" type="number" placeholder="Duration" name="duration"></li>
                </ul>
                <div class="info-column"><input type="submit" class="submit-button" value="Submit"></div>
            </form>
        </div>
    </div>
</div>

<div class="popup-overlay" id="popup1">
    <div class="popup">
        <a class="close" href="#"><i class="fa fa-times"></i></a>

    </div>
</div>

<footer>

</footer>
</body>
</html>
