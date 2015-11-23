package core.helper;

import java.util.HashMap;
import java.util.Map;

public class StringResources {
    public final static String LOGIN_USER_ERROR = "NetID not registered";
    public final static String LOGIN_PASSWORD_ERROR = "Password does not match user";
    public final static String USER_INSTRUCTOR = "Instructor";
    public final static String USER_STUDENT = "Student";
    public final static String USER_ADMINISTRATOR = "Administrator";

    public final static String STUDENT_VIEW_NAME = "student-home";
    public final static String ADMIN_VIEW_NAME = "admin-home";
    public final static String INSTRUCTOR_VIEW_NAME = "instructor-home";

    public final static Map<String, String> ADMINISTRATOR_OPERATIONS = new HashMap<String, String>();
    public final static Map<String, String> STUDENT_OPERATIONS = new HashMap<String, String>();
    public final static Map<String, String> INSTRUCTOR_OPERATIONS = new HashMap<String, String>();

    static {
        ADMINISTRATOR_OPERATIONS.put("viewRequest", "Manage Schedule Requests");
        ADMINISTRATOR_OPERATIONS.put("viewInfo", "View Testing Center Information");
        ADMINISTRATOR_OPERATIONS.put("upload", "Upload a file");
        ADMINISTRATOR_OPERATIONS.put("viewAppointments", "View Appointment");
        ADMINISTRATOR_OPERATIONS.put("makeAppointment", "Make An Appointment");
        ADMINISTRATOR_OPERATIONS.put("checkIn", "Check in a Student");
        ADMINISTRATOR_OPERATIONS.put("generateReport","Generate Report");
        ADMINISTRATOR_OPERATIONS.put("home", "Home");

        INSTRUCTOR_OPERATIONS.put("scheduleEvent", "Schedule an Event");
        INSTRUCTOR_OPERATIONS.put("viewReservations", "View Requests");
        INSTRUCTOR_OPERATIONS.put("viewAppointments", "View Appointments");
        INSTRUCTOR_OPERATIONS.put("home", "Home");

        STUDENT_OPERATIONS.put("makeAppointment", "Make Appointment");
        STUDENT_OPERATIONS.put("viewAppointments", "View Appointments");
        STUDENT_OPERATIONS.put("home", "Home");
    }
}