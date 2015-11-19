<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="page_heading" type="java.lang.String"--%>
<%--@elvariable id="content" type="java.lang.String"--%>
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
        <h1>${page_heading}</h1>
    </div>
    <div class="header-menu">
        <a>Logout</a>
        <a>User <i class="fa fa-chevron-down"></i></a>
    </div>
</div>
<div class="main-wrapper">
    <div class="container">
        <div class="sidebar">
            <jsp:include page="include/sidebar.jsp"/>
        </div>
        <div class="content">
            <jsp:include page="${content}"/>
        </div>
    </div>
</div>

<div class="popup-overlay" id="popup1">
    <div class="popup">
        <a class="close" href="#"><i class="fa fa-times"></i></a>
        <form>
            <table class="card-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Made By</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Attendance</th>
                </tr>
                </thead>
                <tbody>
                <tr>

                </tr>
                </tbody>
            </table>
            <input type="submit" value="Modify" class="popup-submit">
            <input type="submit" value="Submit" class="popup-submit">
            <input type="submit" value="Cancel Request" class="popup-submit">
            <input type="submit" value="Approve" class="popup-submit">
            <a href="#" class="button">Back</a>
        </form>
    </div>
</div>

<footer>

</footer>
</body>
</html>