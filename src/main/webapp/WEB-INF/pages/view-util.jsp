<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <h1>Utilization</h1>
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
            <form class="edit-info" action="/admin/view-utilization/submit" method="post">
                <label>Enter the dates you would like to view utilization information for.</label>
                <label>
                    Start Date
                </label>
                <input class="input-info" type="date" name="startDate">
                <label>
                    End Date
                </label>
                <input class="input-info" type="date" name="EndDate">
                <input class="submit-button" type="submit" value="Submit">
            </form>
        </div>
    </div>
</div>
<footer>

</footer>
</body>
</html>