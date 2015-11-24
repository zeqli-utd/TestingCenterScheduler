<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="exams" type="java.util.List"--%>
<%--@elvariable id="exam" type="core.event.Exam"--%>
<%--@elvariable id="courseName" type="java.lang.String"--%>
<%--@elvariable id="instructorName" type="java.lang.String"--%>
<form class="input-info" action="find-exam/by-instructor">
     <div class="info-column">
         Search for an exam <input class="input-info" type="text" placeholder="Search here">
     </div>
     <div class="info-column">
         <label>
             <input type="radio" name="search-type" value="by-instructor">
         </label>By Instructor
         <label>
             <input type="radio" name="search-type" value="by-course">
         </label>By Course
         <input class="submit-button" type="submit" name="search" value="Search">
     </div>
 </form>
 <div>
     <table>
         <tr>
             <th>Course</th>
             <th>Instructor</th>
             <th>Start</th>
             <th>End</th>
         </tr>
         <c:forEach items="exams" var="exam">
            <tr>
                <td>${exam.courseName}</td>
                <td>${exam.instructorName}</td>
                <td>${exam.startDateTime}</td>
                <td>${exam.endDateTime}</td>
            </tr>
         </c:forEach>
     </table>
 </div>