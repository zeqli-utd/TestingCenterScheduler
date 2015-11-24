package core.user.dao;


import core.event.Appointment;
import core.event.Course;
import core.event.Roster;
import core.user.Administrator;
import core.user.Authorization;
import core.user.UserType;

import java.util.List;

public interface AdministratorDao {

     Authorization getPermission();
     List<Administrator> findByNetID(String administratorId);
    // Import User.csv
    // Add a USER row with fields: | FirstName | LastName | NetID | Email|
     boolean addUser(UserType user);

    // Import Class.csv
    // Add a CLASS row with fields: | ClassID | Subject | CatalogNumber | Section | InstructorNetID |
     boolean addClass(Course course);

    // Import Roster.csv
    // Add a ROSTER row with fields: | NetID | ClassID |
     boolean addRoster(Roster roster);


    // Handle schedule request
    // Update request's status field accordingly.(approved/denied)
     void updateRequestStatus(boolean isApproved);

    // Make appointment on behalf of a student
    // Add a row ot Appointment
     void addAppointment(Appointment appointment);

    // View Appointment
    // Method to list all the appointments detail
     void listAppointment();

    // Check-in Student
     void updateStudent(String studentNetID);
}
