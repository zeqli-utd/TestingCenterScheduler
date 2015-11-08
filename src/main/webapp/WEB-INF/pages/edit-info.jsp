<%--@elvariable id="centerInfo" type="core.event.TestingCenterInfo"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <meta charset="utf-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=2.0"/>
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
    <h1>Testing Center Information</h1>
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
        <li><a class="button" href="ADMINISTRATOR/home">
            <i class="fa fa-home"></i>&nbsp;Home</a></li>
        <li><a class="button" href="ADMINISTRATOR/view-info">
            <i class="fa fa-info"></i>&nbsp;View testing center info</a></li>
        <li><a class="button" href="ADMINISTRATOR/upload">
            <i class="fa fa-upload"></i>&nbsp;Upload a file</a></li>
        <li><a class="button" href="ADMINISTRATOR/view-requests">
            <i class="fa fa-spinner"></i>&nbsp;View requests</a></li>
        <li><a class="button" href="ADMINISTRATOR/view-appointments">
            <i class="fa fa-eye"></i>&nbsp;View appointments</a></li>
        <li><a class="button" href="ADMINISTRATOR/make-appointment">
            <i class="fa fa-plus-square"></i>&nbsp;Make an appointment</a></li>
      </ul>
    </div>
    <div class="content">
        <p>
        <div class="info-column">
            Closed Dates
            <table>
                <c:forEach items="${centerInfo.closeDateRanges}" var="closeDates">
                    <tr>
                        <td><c:out value="${closeDates}"/></td>
                    </tr>
                </c:forEach>
            </table>
            <form class="edit-info" action="edit-info/closed-dates/add">
                <input type="date" class="input-info" name="add-closed-date-start">
                <input type="date" class="input-info" name="add-closed-date-end">
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
            <form class="edit-info" action="edit-info/reserve-dates/add">
                <input type="date" class="input-info" name="add-reserved-date-start">
                <input type="date" class="input-info" name="add-reserved-date-end">
                <input type="submit" class="submit-button" value="Add Another">
            </form>
        </div>
        <div class="info-column">
            Open Time <c:out value="${centerInfo.open}"/>
            <form class="edit-info" action="edit-info/open/modify">
                <input type="time" class="input-info" name="modify-open-hour">
                <input type="submit" class="submit-button" value="Change">
            </form>
            Close Time <c:out value="${centerInfo.close}"/>
            <form class="edit-info" action="edit-info/close/modify">
                <input type="time" class="input-info" name="modify-close-hour">
                <input type="submit" class="submit-button" value="Change">
            </form>
        </div>
        <div class="info-column">
            Number of Seats <c:out value="${centerInfo.numSeats}"/>
            <form class="edit-info" action="edit-info/num-of-seats/modify">
                <input type="text" class="input-info" name="modify-seats">
                <input type="submit" class="submit-button" value="Change">
            </form>
        </div>
        <div class="info-column">
            Number of Set-aside Seats <c:out value="${centerInfo.numSetAsideSeats}"/>
            <form class="edit-info" action="edit-info/set-aside/modify">
                <input type="text" class="input-info" name="modify-seats">
                <input type="submit" class="submit-button" value="Change">
            </form>
        </div>
        <div class="info-column">
            Gap Time <c:out value="${centerInfo.gap}"/> minutes
            <form class="edit-info" action="edit-info/gap/modify">
                <input type="text" class="input-info" name="gap-time">
                <input type="submit" class="submit-button" value="Change">
            </form>
        </div>
        <div class="info-column">
            Reminder Interval <c:out value="${centerInfo.reminderInterval}"/> minutes
            <form class="edit-info" action="edit-info/interval/modify">
                <input type="text" class="input-info" name="modify-interval">
                <input type="submit" class="submit-button" value="Change">
            </form>
        </div>
        </p>
    </div>
  </div>
</div>

<footer>
</footer>
</body>
</html>