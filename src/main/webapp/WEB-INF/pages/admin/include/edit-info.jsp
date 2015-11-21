<%--@elvariable id="centerInfo" type="core.event.TestingCenterInfo"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <form class="edit-info" action="edit-info/reserve-dates/add">
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
    <form class="edit-info" action="edit-info/open/modify">
        <label>
            <input type="time" class="input-info" name="modify-open-hour">
        </label>
        <input type="submit" class="submit-button" value="Change">
    </form>
    Close Time <c:out value="${centerInfo.close}"/>
    <form class="edit-info" action="edit-info/close/modify">
        <label>
            <input type="time" class="input-info" name="modify-close-hour">
        </label>
        <input type="submit" class="submit-button" value="Change">
    </form>
</div>
<div class="info-column">
    Number of Seats <c:out value="${centerInfo.numSeats}"/>
    <form class="edit-info" action="edit-info/num-of-seats/modify">
        <label>
            <input type="text" class="input-info" name="modify-seats">
        </label>
        <input type="submit" class="submit-button" value="Change">
    </form>
</div>
<div class="info-column">
    Number of Set-aside Seats <c:out value="${centerInfo.numSetAsideSeats}"/>
    <form class="edit-info" action="edit-info/set-aside/modify">
        <label>
            <input type="text" class="input-info" name="modify-seats">
        </label>
        <input type="submit" class="submit-button" value="Change">
    </form>
</div>
<div class="info-column">
    Gap Time <c:out value="${centerInfo.gap}"/> minutes
    <form class="edit-info" action="edit-info/gap/modify">
        <label>
            <input type="text" class="input-info" name="gap-time">
        </label>
        <input type="submit" class="submit-button" value="Change">
    </form>
</div>
<div class="info-column">
    Reminder Interval <c:out value="${centerInfo.reminderInterval}"/> minutes
    <form class="edit-info" action="edit-info/interval/modify">
        <label>
            <input type="text" class="input-info" name="modify-interval">
        </label>
        <input type="submit" class="submit-button" value="Change">
    </form>
</div>
