<%--
  Created by IntelliJ IDEA.
  User: walt
  Date: 11/27/15
  Time: 9:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<div class="popup-overlay" id="popup1">
  <div class="popup">
    <a class="close" href="#"><i class="fa fa-times"></i></a>
    <jsp:include page="${popup_content}"/>
  </div>
</div>
</body>
</html>
