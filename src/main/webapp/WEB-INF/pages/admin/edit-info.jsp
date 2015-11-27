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
        <div class="sidebar">
            <jsp:include page="sidebar.jsp" flush="true"/>
        </div>
        <div class="content">
            <div class="info-column">
                Term
                <select>

                </select>
            </div>
            <div class="info-column">
                Closed Dates
                <table>
                    <c:forEach items="${centerInfo.closeDateRanges}" var="closeDates">
                        <tr>
                            <td><c:out value="${closeDates}"/></td>
                        </tr>
                    </c:forEach>
                </table>
                <form class="edit-info" action="admin/edit-info/closed-dates/add">
                    <label>
                        <input type="date" class="input-info" name="add-closed-date-start">
                    </label>
                    <label>
                        <input type="date" class="input-info" name="add-closed-date-end">
                    </label>
                    <input type="submit" class="submit-button" value="Add Another">
                </form>
            </div>
            <div class="info-column">
                Reserved Dates
                <table>
                    <c:forEach items="${centerInfo.reserveRanges}" var="reservedDates">
                        <tr>
                            <td><c:out value="${reservedDates}"/></td>
                        </tr>
                    </c:forEach>
                </table>
                <form class="edit-info" action="admin/edit-info/reserve-dates/add">
                    <label>
                        <input type="date" class="input-info" name="add-reserved-date-start">
                    </label>
                    <label>
                        <input type="date" class="input-info" name="add-reserved-date-end">
                    </label>
                    <input type="submit" class="submit-button" value="Add Another">
                </form>
            </div>
            <div class="info-column">
                Open Time <c:out value="${centerInfo.open}"/>
                <form class="edit-info" action="admin/edit-info/open/modify">
                    <label>
                        <input type="time" class="input-info" name="modify-open-hour">
                    </label>
                    <input type="submit" class="submit-button" value="Change">
                </form>
                Close Time <c:out value="${centerInfo.close}"/>
                <form class="edit-info" action="admin/edit-info/close/modify">
                    <label>
                        <input type="time" class="input-info" name="modify-close-hour">
                    </label>
                    <input type="submit" class="submit-button" value="Change">
                </form>
            </div>
            <div class="info-column">
                Number of Seats <c:out value="${centerInfo.numSeats}"/>
                <form class="edit-info" action="admin/edit-info/num-of-seats/modify">
                    <label>
                        <input type="text" class="input-info" name="modify-seats">
                    </label>
                    <input type="submit" class="submit-button" value="Change">
                </form>
            </div>
            <div class="info-column">
                Number of Set-aside Seats <c:out value="${centerInfo.numSetAsideSeats}"/>
                <form class="edit-info" action="admin/edit-info/set-aside/modify">
                    <label>
                        <input type="text" class="input-info" name="modify-seats">
                    </label>
                    <input type="submit" class="submit-button" value="Change">
                </form>
            </div>
            <div class="info-column">
                Gap Time <c:out value="${centerInfo.gap}"/> minutes
                <form class="edit-info" action="admin/edit-info/gap/modify">
                    <label>
                        <input type="text" class="input-info" name="gap-time">
                    </label>
                    <input type="submit" class="submit-button" value="Change">
                </form>
            </div>
            <div class="info-column">
                Reminder Interval <c:out value="${centerInfo.reminderInterval}"/> minutes
                <form class="edit-info" action="admin/edit-info/interval/modify">
                    <label>
                        <input type="text" class="input-info" name="modify-interval">
                    </label>
                    <input type="submit" class="submit-button" value="Change">
                </form>
            </div>
        </div>
    </div>
</div>
<%-----------------------popup content-------------------------%>
<div class="popup-overlay" id="popup1">
    <div class="popup">
        <a class="close" href="#"><i class="fa fa-times"></i></a>
        <jsp:include page="${popup_content}" flush="true"/>
    </div>
</div>

<footer>

</footer>
</body>
</html>