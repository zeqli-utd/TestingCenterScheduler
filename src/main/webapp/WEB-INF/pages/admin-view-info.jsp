<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--@elvariable id="term" type="core.event.Term"--%>
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
            <select name="viewedTerm" form="changeTerm">
                <c:forEach items="${terms}" var="term">
                    <option value="${term.termId}"><c:out value="${term.termName}"/></option>
                </c:forEach>
            </select>
            <form id="changeTerm" action="/admin/view-info/change-term">
                <input type="submit" value="Change Term" class="submit-button" class="stand-alone">
            </form>
            <ul>
                <li>
                    Term
                    <c:out value="${info.term}"/>
                </li>
                <br/>
                <li>
                    Hours
                    <c:out value="${info.open}"/> - <c:out value="${info.close}"/>
                </li>
                <br/>
                <li>
                    Gap Time
                    <c:out value="${info.gap}"/>
                </li>
                <br/>
                <li>
                    Number of Seats
                    <c:out value="${info.numSeats}"/>
                </li>
                <br/>
                <li>
                    Number of Set-aside Seats
                    <c:out value="${info.numSetAsideSeats}"/>
                </li>
                <br/>
                <li>
                    Reminder Interval
                    <c:out value="${info.reminderInterval}"/>
                </li>
            </ul>
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