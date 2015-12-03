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
            <table>
                <thead>
                <tr>
                    <th>Dates</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${dates}" var="date">
                    <tr>
                        <td><c:out value="${date}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <table>
                <thead>
                <tr>
                    <th>Actual</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${actualDates}" var="actrualdDate">
                    <tr>
                        <td><c:out value="${actrualdDate}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <table>
                <thead>
                <tr>
                    <th>Expected</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${expectedDates}" var="expectedDate">
                    <tr>
                        <td><c:out value="${expectedDate}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<footer>

</footer>
</body>
</html>