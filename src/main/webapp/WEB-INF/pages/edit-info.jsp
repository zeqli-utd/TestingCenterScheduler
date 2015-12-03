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
        <h1>Center Information</h1>
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
            <select form="infoForm" name="termId">
                <c:forEach items="${terms}" var="term">
                    <option value="${term.termId}"><c:out value="${term.termName}"/></option>
                </c:forEach>
            </select>
            <form class="edit-info" id="infoForm" action="/admin/edit-info/general-submit" method="post">
                <label>
                    Number of seats <input type="number" name="numSeats" class="input-info">
                </label>
                <label>
                    Number of set aside seats <input type="number" name="numSetAsideSeats" class="input-info">
                </label>
                <label>
                    Open <input type="time" name="open" class="input-info">
                </label>
                <label>
                    Close <input type="time" name="close" class="input-info">
                </label>
                <label>
                    Gap time <input type="number" name="gap" class="input-info">
                </label>
                <label>
                    Reminder interval <input type="number" name="reminderInterval" class="input-info">
                </label>
                <input type="submit" class="submit-button" value="Continue">
            </form>
            <a style="margin-left: 8em" class="button" href="/admin/new-term"/>
        </div>
    </div>
</div>
<%-----------------------popup content-------------------------%>
<div class="popup-overlay" id="popup1">
    <div class="popup">
        <a class="close" href="#"><i class="fa fa-times"></i></a>
    </div>
</div>
</body>
</html>