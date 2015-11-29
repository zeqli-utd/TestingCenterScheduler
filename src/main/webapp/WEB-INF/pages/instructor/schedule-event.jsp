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
            <form:form modelAttribute="exam" action="instructor/schedule-event/submit" method="post">
                <label>Instructor </label>
                <form:input path="instructorId"/>
                <br/>
                <label>Term </label>
                <form:select path="term">
                    <form:options items="${terms}" itemLabel="termName" itemValue="termId"/>
                </form:select>
                <label>Exam Type </label>
                <form:select path="examType">
                    <form:option value="">Exam type</form:option>
                    <form:option value="REGULAR">Regular</form:option>
                    <form:option value="AD_HOC">Other</form:option>
                </form:select>
                <br/>
                <label>Exam Name </label>
                <form:input path="examName"/>
                <br/>
                <label>Start</label>
                <form:input path="startDateTime"/>
                <br/>
                <label>End</label>
                <form:input path="endDateTime"/>
                <br/>
                <label>Capacity</label>
                <form:input path="capacity"/>
                <br/>
                <label>Duration</label>
                <form:input path="duration"/>
                <br/>
                <input type="submit" value="Submit" class="submit-button">
            </form:form>
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
