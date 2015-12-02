<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
        <h1>Schedule a request</h1>
    </div>
    <div class="header-menu">
        <a href="/logout">Logout</a>
        <a>User <i class="fa fa-chevron-down"></i></a>
    </div>
</div>
<div class="main-wrapper">
    <div class="container">
        <div class="sidebar">
            <jsp:include page="instructor-sidebar.jsp"/>
        </div>
        <div class="content">
            <form action="/instructor/schedule-event/submit" method="post" name="term" id="request-form">
                <label>Instructor </label>
                <input name="instructorId" type="text" placeholder="Instructor"/>
                <br/>
                <label>Exam ID</label>
                <input name="examId" type="text" placeholder="Exam ID">
                <br/>
                <label>Course ID</label>
                <input name="courseId" type="text" placeholder="Course ID">
                <br/>
                <label>Exam Name </label>
                <input name="examName" type="text" placeholder="Name"/>
                <br/>
                <label>Start</label>
                <input name="startDateTime" type="datetime-local" placeholder="Starts at"/>
                <br/>
                <label>End</label>
                <input name="endDateTime" type="datetime-local" placeholder="Ends at"/>
                <br/>
                <label>Capacity</label>
                <input name="capacity" type="number" placeholder="Max number"/>
                <br/>
                <label>Duration</label>
                <input name="duration" type="number" placeholder="Duration"/>
                <br/>
                <input type="submit" value="Submit" class="submit-button">
            </form>
            <select name="term" form="request-form" title="Term">
                <c:forEach items="${terms}" var="term">
                    <option value="${term.termId}"><c:out value="${term.termName}"/></option>
                </c:forEach>
            </select>
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
