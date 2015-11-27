<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--@elvariable id="page_heading" type="java.lang.String"--%>
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
<%-------------------------header--------------------------%>
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
<%--------------------------main content--------------------------%>
<div class="main-wrapper">
    <div class="container">
        <h2>${newTerm}</h2>

        <h2>Step One</h2>

        <p>General Information</p>

        <form>
            <div class="info-column">
                Open Time
                <label>
                    <input type="time" class="input-info" name="open">
                </label>
                <input type="submit" class="submit-button" value="Change">
                Close Time
                <label>
                    <input type="time" class="input-info" name="close">
                </label>
                <input type="submit" class="submit-button" value="Change">
            </div>
            <div class="info-column">
                Number of Seats
                <label>
                    <input type="text" class="input-info" name="numSeats" placeholder="Number of Seats">
                </label>
                <input type="submit" class="submit-button" value="Change">
            </div>
            <div class="info-column">
                Number of Set-aside Seats
                <label>
                    <input type="text" class="input-info" name="numSetAsideSeats" placeholder="Set Aside Seats">
                </label>
                <input type="submit" class="submit-button" value="Change">
            </div>
            <div class="info-column">
                Gap Time
                <label>
                    <input type="text" class="input-info" name="gap" placeholder="minutes">
                </label>
                <input type="submit" class="submit-button" value="Change">
            </div>
            <div class="info-column">
                Reminder Interval
                <label>
                    <input type="text" class="input-info" name="reminderInterval" placeholder="minutes">
                </label>
                <input type="submit" class="submit-button" value="Change">
            </div>
        </form>
    </div>
</div>
<%-----------------------popup content-------------------------%>
<div class="popup-overlay" id="popup1">
    <div class="popup">
        <a class="close" href="#"><i class="fa fa-times"></i></a>
        <form action="admin/edit-info/new-term/submit">
            Add new term
            <label>
                <input class="input-info" type="text" name="termName" placeholder="Enter a new Term">
            </label>
            <input class="input-info" type="date" name="termStartDate" placeholder="Term start Date">
            <input class="input-info" type="date" name="termEndDate" placeholder="Term End Date">
            <input class="submit-button" type="submit" value="Continue">
        </form>
        <a class="button" href="admin/edit-info">Cancel</a>
    </div>
</div>

<footer>

</footer>
</body>
</html>