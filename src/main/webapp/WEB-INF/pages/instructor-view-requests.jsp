<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="request" type="core.event.Exam"--%>
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
        <h1>View Requests</h1>
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
            <table style="margin-left: 2em">
                <thead>
                    <tr>
                        <th>Exam Name</th>
                        <th>Capacity</th>
                        <th>Start</th>
                        <th>End</th>
                        <th>Duration</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="request" items="${requests}">
                        <tr>
                            <td>
                                <a class="button" href="/instructor/view-requests/cancel/${request.examId}">
                                    <c:out value="${request.examName}"/>
                                </a>
                            </td>
                            <td><c:out value="${request.capacity}"/></td>
                            <td><c:out value="${request.startDateTime}"/></td>
                            <td><c:out value="${request.endDateTime}"/></td>
                            <td><c:out value="${request.duration}"/></td>
                            <td><c:out value="${request.statusType}"/></td>
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
