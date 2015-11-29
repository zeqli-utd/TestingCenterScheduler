<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<html>
<head>
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
    <div class="header-menu">
    </div>
</div>
<div>
    <form action="authorizing" class="login-box">
        <h3 class="login-heading">Sign in</h3>
        <input type="text" class="login-text" placeholder="NetID" name="uerId">
        <input type="password" class="login-text" placeholder="Password" name="password">
        <input type="submit" value="Login" class="submit-button"/>
    </form>
    <div class="error-message">${errorMessage}</div>
</div>

<div class="popup-overlay" id="popup1">
    <div class="popup">
        <a class="close" href="#"><i class="fa fa-times"></i></a>
        <jsp:include page="${popup_content}"/>
    </div>
</div>

</body>
</html>
