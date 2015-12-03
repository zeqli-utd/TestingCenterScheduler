<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Testing Center Scheduler</title>
    <link rel="stylesheet" href="/assets/css/styles.css" type="text/css">
</head>
<body>
<div class="header-wrapper">
    <div class="header-logo">
        <a href="https://it.stonybrook.edu/services/testing-center" class="button">
            <h1>Stony Brook Testing Center</h1>
        </a>
    </div>
    <div class="main-heading">
        <h1>View Appointments</h1>
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
            <table>
                <thead>
                <tr>
                    <th>Exam</th>
                    <th>Student Id</th>
                    <th>Appointment StartTime</th>
                    <th>Appointment EndTime</th>
                    <th>Seat</th>
                    <th>Attendance</th>
                    <th>Superfluous</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="appointment" items="${appointments}">
                    <tr>
                        <td><c:out value="${appointment.examId}"/></td>
                        <td><c:out value="${appointment.studentId}"/></td>
                        <td><c:out value="${appointment.startDateTime}"/></td>
                        <td><c:out value="${appointment.endDateTime}"/></td>
                        <td><c:out value="${appointment.seat}"/></td>
                        <td><c:out value="${appointment.attend}"/></td>
                        <td><c:out value="${appointment.status}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
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
