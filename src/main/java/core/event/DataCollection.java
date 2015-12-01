package core.event;

import core.event.dao.AppointmentDao;
import core.event.dao.CourseDao;
import core.event.dao.RosterDao;
import core.service.SessionManager;
import core.user.Instructor;
import core.user.Student;
import core.user.dao.InstructorDao;
import core.user.dao.StudentDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Component
public class DataCollection {

    static final private String PATH = "/course_registration_data/";
    static final private String fileExt = ".csv";

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private InstructorDao instructorDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private RosterDao rosterDao;


    public DataCollection(){}

    //import ata from CSV file
    public boolean readFile(String path, int termId) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        List<Appointment> appointmentList = appointmentDao.findAllAppointment();
        try {

            //Student Data
            if (path.contains("user.csv")) {

                // 1. Discard the first line
                br = new BufferedReader(new FileReader(path));
                line = br.readLine();

                while ((line = br.readLine()) != null) {
                    String[] listItem = line.split(cvsSplitBy);

                    // FirstName, LastName, NetId, Email
                    Student userIter = new Student(listItem[2], listItem[0], listItem[1],listItem[3]);
                    studentDao.addStudent(userIter);
                }
            }
            //Instructor Data
            if (path.contains("instructor.csv")) {

                // 1. Discard the first line
                br = new BufferedReader(new FileReader(path));
                line = br.readLine();

                while ((line = br.readLine()) != null) {
                    String[] listItem = line.split(cvsSplitBy);
                    // Last Name, First Name, User Id, Email
                    Instructor instructorIter = new Instructor(listItem[2], listItem[1], listItem[0],listItem[3]);
                    instructorDao.addInstructor(instructorIter);
                }
            }
            //class data
            else if (path.contains("class.csv")) {
                // Clear Class Table by term
                courseDao.deleteCoursesByTerm(termId);

                // 1. Discard the first line
                br = new BufferedReader(new FileReader(path));
                line = br.readLine();   // Discard the first line


                while ((line = br.readLine()) != null) {
                    String[] listItem = line.split(cvsSplitBy);
                    Course courseIter = new Course(listItem[0], listItem[1], listItem[2], listItem[3], listItem[4], termId);
                    courseDao.addCourse(courseIter);
                }
            } else if (path.contains("roster.csv")) {
                // Clear roster table by term
                rosterDao.deleteRostersByTerm(termId);

                // 1. Discard the first line
                br = new BufferedReader(new FileReader(path));
                line = br.readLine();

                ArrayList<String[]> rosterList = new ArrayList<String[]>();
                while ((line = br.readLine()) != null) {
                    String[] listItem = line.split(cvsSplitBy);
                    Roster roster = new Roster(listItem[1], listItem[0], termId);
                    rosterDao.addRoster(roster);
                    rosterList.add(listItem);
                }

            }
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return true;
    }


}