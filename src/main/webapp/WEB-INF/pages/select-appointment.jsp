<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--@elvariable id="slot" type="core.event.TestingCenterTimeSlots"--%>
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
        <h1>Student Home</h1>
    </div>
    <div class="header-menu">
        <a>Logout</a>
        <a>User <i class="fa fa-chevron-down"></i></a>
    </div>
</div>
<div class="main-wrapper">
    <div class="container">
        <div class="sidebar">
            <jsp:include page="student-sidebar.jsp"/>
        </div>
        <div class="content">
            <form:form modelAttribute="appointment">
                <form:radiobuttons path="slotId" items="${timeSlots}"/>
            </form:form>
            <table title="${exam}">
                <thead>
                    <tr>
                        <th>Begins at</th>
                        <th>Ends at</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${timeSlots}" var="slot">
                    <tr>
                        <td><a href="student/make-appointment/new/${slot.timeSlotId}">${slot.begin}</a></td>
                        <td><a>${slot.end}</a></td>
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
