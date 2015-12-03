<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="appointment" type="core.event.Appointment"--%>
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
        <h1>Check In</h1>
    </div>
    <div class="header-menu">
        <a href="/logout">Logout</a>
        <a>Administrator</a>
    </div>
</div>
<div class="main-wrapper">
    <div class="container">
        <div class="sidebar">
            <jsp:include page="student-sidebar.jsp"/>
        </div>
        <div class="content">
            <table>
                <thead>
                <tr>
                    <th>Term</th>
                    <th>Exam</th>
                    <th>Starts at</th>
                    <th>Ends at</th>
                    <th>Seat</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${appointments}" var="appointment">
                    <tr>
                        <td>
                            <a href="/admin/make-appointment/commit/${appointment.appointmentID}">
                                <c:out value="${appointment.term}"/>
                            </a>
                        </td>
                        <td><c:out value="${appointment.examName}"/></td>
                        <td><c:out value="${appointment.startDateTime}"/></td>
                        <td><c:out value="${appointment.endDateTime}"/></td>
                        <td><c:out value="${appointment.seat}"/></td>
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

    </div>
</div>

<footer>

</footer>
</body>
</html>
