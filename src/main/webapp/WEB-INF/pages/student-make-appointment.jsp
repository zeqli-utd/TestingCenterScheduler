<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="exam" type="core.event.Exam"--%>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Testing Center Scheduler</title>
    <link rel="stylesheet" href="/assets/css/styles.css"/>
</head>
<body>
<div class="header-wrapper">
    <div class="header-logo">
        <a href="https://it.stonybrook.edu/services/testing-center" class="button">
            <h1>Stony Brook Testing Center</h1>
        </a>
    </div>
    <div class="main-heading">
        <h1>Make an Appointment</h1>
    </div>
    <div class="header-menu">
        <a href="/logout">Logout</a>
        <a>User <i class="fa fa-chevron-down"></i></a>
    </div>
</div>
<div class="main-wrapper">
    <div class="container">
        <div class="sidebar">
            <jsp:include page="student-sidebar.jsp"/>
        </div>
        <div class="content">
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>Exam</th>
                            <th>Term</th>
                            <th>Course</th>
                            <th>Duration</th>
                            <th>Start</th>
                            <th>End</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${exams}" var="exam">
                        <tr>
                            <td>
                                <a class="button" href="/student/make-appointment/new/${exam.examId}">
                                    ${exam.examName}
                                </a>
                            </td>
                            <td>${exam.term}</td>
                            <td>${exam.duration}</td>
                            <td>${exam.startDateTime}</td>
                            <td>${exam.endDateTime}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
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
