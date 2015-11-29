<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Welcome</title>
    <meta http-equiv="refresh" content="1;login"/>
    <link rel="stylesheet" type="text/css" href="assets/css/styles.css">
</head>
<body>
    <p class="welcome-message">Welcome To Testing Center</p>


    <div class="popup-overlay" id="popup1">
        <div class="popup">
            <a class="close" href="#"><i class="fa fa-times"></i></a>
            <jsp:include page="${popup_content}"/>
        </div>
    </div>
</body>
</html>
