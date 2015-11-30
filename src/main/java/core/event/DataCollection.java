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

                br = new BufferedReader(new FileReader(path));
                line = br.readLine();   // Discard the first line
                while ((line = br.readLine()) != null) {
                    String[] listItem = line.split(cvsSplitBy);

                    // NetId, FirstName, LastName, Email
                    Student userIter = new Student(listItem[0], listItem[1], listItem[2],listItem[3]);
                    studentDao.addStudent(userIter);
                }
            }
            //Instructor Data
            if (path.contains("instructor.csv")) {

                br = new BufferedReader(new FileReader(path));
                line = br.readLine();   // Discard the first line
                while ((line = br.readLine()) != null) {
                    String[] listItem = line.split(cvsSplitBy);
                    Instructor instructorIter = new Instructor(listItem[0], listItem[1], listItem[2],listItem[3]);
                    instructorDao.addInstructor(instructorIter);
                }
            }
            //class data
            else if (path.contains("class.csv")) {
                // Clear Class Table by term
                courseDao.deleteCoursesByTerm(termId);

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

                br = new BufferedReader(new FileReader(path));
                line = br.readLine();   // Discard the first line
                ArrayList<String[]> list = new ArrayList<String[]>();
                while ((line = br.readLine()) != null) {
                    String[] listItem = line.split(cvsSplitBy);
                    list.add(listItem);
                }
                //change the list to hashmap
                HashMap<String, String> hash = new HashMap();
                for (int i = 0; i < list.size(); i++) {
                    hash.put(list.get(i)[0], list.get(i)[1]);
                }
                markSuperfluous(appointmentList, list);

                Session session = SessionManager.getInstance().openSession();
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    List e = session.createQuery("FROM Appointment WHERE Appointment.status = 's' ").list();
                    Iterator<Appointment> iterator = e.iterator();
                    while (iterator.hasNext()) {
                        Appointment appt = (Appointment) iterator.next();
                        if (hash.get(appt.getStudentId()) == appt.getExamId()) {    // Reinstate Appointment
                            appt.setStatus("r");
                            session.update(appt);
                            //TODO reinstate
                        } else {
                            //TODO cancel appointment
                        }
                    }
                } catch (HibernateException var9) {
                    if (tx != null) {
                        tx.rollback();
                    }

                } finally {
                    session.close();
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

    /**
     *
     * @param appointmentList
     * @param list
     */
    public void markSuperfluous(List<Appointment> appointmentList, List<String[]> list) {
        Iterator databaseIterator = appointmentList.iterator();
        while (databaseIterator.hasNext()) {
            Appointment appt = (Appointment) databaseIterator.next();
            appt.setStatus("s");
            for (int i = 0; i < list.size(); i++) {
                if ((appt.getStudentId() == list.get(i)[0]) && (appt.getExamId() == list.get(i)[1])) {
                    appt.setStatus(null);
                }
            }
        }
    }

}