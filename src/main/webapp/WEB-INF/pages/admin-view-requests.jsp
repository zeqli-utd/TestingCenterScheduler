<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--@elvariable id="request" type="core.event.Exam"--%>
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
        <h1>View Requests</h1>
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
            <table style="margin-left: 2em">
                <thead>
                <tr>
                    <th>Exam Name</th>
                    <th>Capacity</th>
                    <th>Course ID</th>
                    <th>Starts at</th>
                    <th>Ends at</th>
                    <th>Duration</th>
                    <th>APPROVE</th>
                    <th>REJECT</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requests}" var="request">
                    <tr>
                        <td><c:out value="${request.examName}"/></td>
                        <td><c:out value="${request.capacity}"/></td>
                        <td><c:out value="${request.courseId}"/></td>
                        <td><c:out value="${request.startDateTime}"/></td>
                        <td><c:out value="${request.endDateTime}"/></td>
                        <td><c:out value="${request.duration}"/></td>
                        <td><a href="/admin/view-requests/approve/${request.examId}" class="button">Approve</a></td>
                        <td><a href="/admin/view-requests/reject/${request.examId}" class="button">Reject</a></td>
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