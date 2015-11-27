package core.event;

import core.event.dao.AppointmentDaoImp;
import core.event.dao.CourseDaoImp;
import core.service.SessionManager;
import core.user.Instructor;
import core.user.Student;
import core.user.dao.InstructorDaoImp;
import core.user.dao.StudentDaoImp;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DataCollection {

    static final private String PATH = "/course_registration_data/";
    static final private String fileExt = ".csv";

    private AppointmentDaoImp appointmentDaoImpl = new AppointmentDaoImp();

    //import ata from CSV file
    //0: fail; 1: user; 2: class; 3: rooster
    public void readFile(String path) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        List<Appointment> appointmentList = appointmentDaoImpl.findAllAppointment();
        try {
            //student data
            if (path.contains("user.csv")) {
                br = new BufferedReader(new FileReader(path));
                line = br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] listItem = line.split(cvsSplitBy);
                    StudentDaoImp studentDaoImp = new StudentDaoImp();
                    Student userIter = new Student(listItem[0], listItem[1], listItem[2],listItem[3]);
                    studentDaoImp.addStudent(userIter);
                }
            }
            //instructor data
            if (path.contains("instructor.csv")) {
                br = new BufferedReader(new FileReader(path));
                line = br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] listItem = line.split(cvsSplitBy);
                    InstructorDaoImp instructorDaoImp = new InstructorDaoImp();
                    Instructor instructorIter = new Instructor(listItem[0], listItem[1], listItem[2],listItem[3]);
                    instructorDaoImp.addInstructor(instructorIter);
                }
            }
            //class data
            else if (path.contains("class.csv")) {
                br = new BufferedReader(new FileReader(path));
                line = br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] listItem = line.split(cvsSplitBy);
                    CourseDaoImp courseDaoImp = new CourseDaoImp();
                    Course courseIter = new Course(listItem[0], listItem[1], listItem[2], listItem[3], listItem[4]);
                    courseDaoImp.addCourse(courseIter);
                }
            } else if (path.contains("roster.csv")) {
                br = new BufferedReader(new FileReader(path));
                line = br.readLine();
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
                    Iterator iterator = e.iterator();
                    while (iterator.hasNext()) {
                        Appointment appt = (Appointment) iterator.next();
                        if (hash.get(appt.getStudentId()) == appt.getExamId()) {
                            appt.setStatus(null);
                            //reinstate appointment
                        } else {
                            //cancel appointment
                        }
                    }
                } catch (HibernateException var9) {
                    if (tx != null) {
                        tx.rollback();
                    }

                    var9.printStackTrace();
                } finally {
                    session.close();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

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