<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
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
            <jsp:include page="sidebar.jsp"/>
        </div>
        <div class="content">

            <form class="input-info" action="student/find-exam/by-instructor">
                <div class="info-column">
                    Search for an exam <input class="input-info" type="text" placeholder="Search here">
                </div>
                <div class="info-column">
                    <label>
                        <input type="radio" name="search-type" value="by-instructor">
                    </label>By Instructor
                    <label>
                        <input type="radio" name="search-type" value="by-course">
                    </label>By Course
                    <input class="submit-button" type="submit" name="search" value="Search">
                </div>
            </form>
            <div>
                <table>
                    <tr>
                        <th>Course</th>
                        <th>Instructor</th>
                        <th>Start</th>
                        <th>End</th>
                    </tr>
                    <c:forEach items="exams" var="exam">
                        <tr>
                            <td>
                                <a href="student/make-appointment/new/${exam.examId}">
                                        ${exam.courseName}
                                </a>
                            </td>
                            <td>
                                <a href="student/make-appointment/new/${exam.examId}">
                                        ${exam.madeBy}
                                </a>
                            </td>
                            <td>
                                <a href="student/make-appointment/new/${exam.examId}">
                                        ${exam.startDateTime}
                                </a>
                            </td>
                            <td>
                                <a href="student/make-appointment/new/${exam.examId}">
                                        ${exam.endDateTime}
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
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
