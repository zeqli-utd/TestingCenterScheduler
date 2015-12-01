<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <c:url value="/authorizing" var="authorizing"/>
    <form class="login-box" method="post" action="${authorizing}">
        <h3 class="login-heading">Sign in</h3>
        <input name="userId" class="login-text" placeholder="NetID"/>
        <input name="password" class="login-text" placeholder="Password"/>
        <input type="submit" value="Login" class="submit-button"/>
    </form>
    <div class="error-message">${errorMessage}</div>
</div>
<div class="popup-overlay" id="popup1">
    <div class="popup">
        <a class="close" href="#"><i class="fa fa-times"></i></a>
    </div>
</div>

</body>
</html>
