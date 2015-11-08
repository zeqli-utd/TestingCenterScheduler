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
<%----------------------------------------------------------------%>
<div class="main-wrapper">
  <div class="container">
    <div class="sidebar">
      <ul class="side-nav">
        <li><a class="button" href="${path}/ADMINISTRATOR/home"><i class="fa fa-home"></i>&nbsp;Home</a></li>
        <li><a class="button" href="${path}/student-make-appointment"><i class="fa fa-plus-square"></i>&nbsp;Make Appointment</a></li>
        <li><a class="button" href="${path}/student-view-appointments"><i class="fa fa-eye"></i>&nbsp;View Appointments</a></li>
      </ul>
    </div>
    <div class="content">
        <form class="input-info" action="find-exam/by-instructor">
            <div class="info-column">
                Search for an exam <input class="input-info" type="text" placeholder="Search here">
            </div>
            <div class="info-column">
                <input class="submit-button" type="submit" name="by-instructor">
                <input class="submit-button" type="submit" name="by-course">
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
            </table>
        </div>
    </div>
  </div>
</div>
<%------------------------------------------------------------------%>
<div class="popup-overlay" id="popup1">
  <div class="popup">
    <a class="close" href="#"><i class="fa fa-times"></i></a>

  </div>
</div>

<footer>

</footer>
</body>
</html>
