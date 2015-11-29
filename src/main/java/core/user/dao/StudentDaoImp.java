package core.user.dao;

import core.event.Appointment;
import core.service.SessionManager;
import core.user.Administrator;
import core.user.Authorization;
import core.user.Student;
import core.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDaoImp implements StudentDao {

    public StudentDaoImp() {
    }

    @Override
    public boolean addStudent(Student student) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(student);
            tx.commit();
            session.close();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean deleteStudent(Student student) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Student e = (Student)session.get(Student.class, student.getNetId());
            session.delete(e);
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    //retrieve list of students from the database
    @Override
    public List<Student> getAllStudents() {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        List<Student> studentList = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            studentList = session.createQuery("FROM core.user.Student").list();
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return studentList;


    }

    @Override
    public List<Student> getStudentsByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Student getStudentById(String netid) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        Student student = null;
        try {
            tx = session.beginTransaction();
            student = (Student)session.get(Student.class, netid);
            tx.commit();
        } catch (HibernateException he) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return student;
    }

    @Override
    public boolean updateInfo(Student student) {// return should have different cases
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(student);
            tx.commit();
        } catch (HibernateException he) {
            if(tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return true;
    }




}
