<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
            <form action="instructor/schedule-event/submit" method="post">
                <label>Instructor </label>
                <input name="instructorId"/>
                <br/>
                <label>Term </label>
                <select name="term">
                    <c:forEach items="${terms}" var="term">
                        <option value="${term.termId}"><c:out value="${term.termName}"/></option>
                    </c:forEach>
                </select>
                <label>Exam Type </label>
                <select name="examType">
                    <option value="REGULAR">Regular</option>
                    <option value="AD_HOC">Other</option>
                </select>
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
