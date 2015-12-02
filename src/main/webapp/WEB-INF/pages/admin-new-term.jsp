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
        <h1>Administrator</h1>
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
            <form class="edit-info" action="/admin/new-term/submit">
                <label>
                    Name
                    <input type="text" name="termId" class="input-info" placeholder="Term ID">
                </label>
                <label>
                    Start Date
                    <input type="date" name="termStartDate" class="input-info" placeholder="Starts">
                </label>
                <label>
                    End Date
                    <input type="date" name="termEndDate" class="input-info" placeholder="Ends">
                </label>
                <input type="submit" value="Submit" class="submit-button">
            </form>
        </div>
    </div>
</div>
<footer>

</footer>
</body>
</html>