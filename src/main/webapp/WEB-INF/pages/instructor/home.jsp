<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="content" type="java.lang.String"--%>
<%--@elvariable id="popup_content" type="java.lang.String"--%>
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
        <h1>Instructor Home</h1>
    </div>
    <div class="header-menu">
        <a>Logout</a>
        <a>User <i class="fa fa-chevron-down"></i></a>
    </div>
</div>
<div class="main-wrapper">
    <div class="container">
        <div class="sidebar">
            <ul class="side-nav">
                <li><a class="button" href="instructor/home"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a class="button" href="instructor/schedule-event"><i class="fa fa-#"></i>&nbsp;Schedule an Event</a></li>
                <li><a class="button" href="#"><i class="fa fa-#"></i>&nbsp;Pending Request</a></li>
                <li><a class="button" href="#"><i class="fa fa-#"></i>&nbsp;View appointments</a></li>
            </ul>
        </div>
        <div class="content">
            <jsp:include page="${content}"/>
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
