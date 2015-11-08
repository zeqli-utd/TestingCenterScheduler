package core.user;

import core.event.Appointment;
import core.service.SessionManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDaoImp implements StudentDao{
    private static Logger log = Logger.getLogger(StudentDaoImp.class);
    private List<Student> students;

    public StudentDaoImp(){
        students = new ArrayList<Student>();
        Student student1 = new Student();
        Student student2 = new Student();
        students.add(student1);
        students.add(student2);
    }

    @Override
    public boolean addStudent(Student student) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(student);
            log.info(student.toString());
            tx.commit();
            session.close();
        }catch (HibernateException he){
            log.error("", he);
            if(tx != null){
                tx.rollback();
            }
            return  false;
        } finally {
        session.close();
    }
        return true;
    }

    @Override
    public boolean deleteStudent(Student student) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Administrator e = (Administrator)session.get(Administrator.class, student.getNetId());
            session.delete(e);
            tx.commit();
        } catch (HibernateException he) {
            if(tx != null) {
                tx.rollback();
            }
            log.error("", he);
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    //retrieve list of students from the database
    @Override
    public List<Student> getAllStudents() {
        return students;
    }

    @Override
    public List<Student> getStudentsByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Student getStudentById(int Id) {
        return students.get(Id);
    }

    @Override
    public Authorization getPermission() {
        return null;
    }

    @Override
    public boolean updateInfo(Student student) {// return should have different cases
//        students.get(student.getStudent_Id()).setName(student.getName());
//        return true;
        return false;
    }

    public void makeAppointment(Appointment apt){
        // 1. Student Enrolled in Course in Current Term. or on the List of ad hoc exam.

        // 2. Student does not have an existing appointment for same exam.
        
        // 3. Student does not have an appointment for a different in an overlapping.

        // 4. Appointment is entirely between the start date-time and end date-time of the exam.

    }





}
