<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--@elvariable id="appointment" type="core.event.Appointment"--%>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Testing Center Scheduler</title>
    <link rel="stylesheet" href="/assets/css/styles.css" type="text/css">
</head>
<body>
<%-------------------------header--------------------------%>
<div class="header-wrapper">
    <div class="header-logo">
        <a href="https://it.stonybrook.edu/services/testing-center" class="button">
            <h1>Stony Brook Testing Center</h1>
        </a>
    </div>
    <div class="main-heading">
        <h1>Appointments</h1>
    </div>
    <div class="header-menu">
        <a href="/logout">Logout</a>
        <a>User <i class="fa fa-chevron-down"></i></a>
    </div>
</div>
<%--------------------------main content--------------------------%>
<div class="main-wrapper">
    <div class="container">
        <div class="sidebar">
            <jsp:include page="admin-sidebar.jsp" flush="true"/>
        </div>
        <div class="content">
            <p><c:out value="${errorMessage}"/></p>
            <table>
                <thead>
                <tr>
                    <th>Appointment ID</th>
                    <th>Starts</th>
                    <th>Ends</th>
                    <th>Student</th>
                    <th>Made by</th>
                    <th>Seat</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${appointments}" var="appointment">
                    <tr>
                        <td><c:out value="${appointment.appointmentID}"/></td>
                        <td><c:out value="${appointment.startDateTime}"/></td>
                        <td><c:out value="${appointment.endDateTime}"/></td>
                        <td><c:out value="${appointment.studentId}"/></td>
                        <td><c:out value="${appointment.madeBy}"/></td>
                        <td><c:out value="${appointment.seat}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%-----------------------popup content-------------------------%>
<div class="popup-overlay" id="popup1">
    <div class="popup">
        <a class="close" href="#"><i class="fa fa-times"></i></a>
    </div>
</div>

<footer>

</footer>
</body>
</html>