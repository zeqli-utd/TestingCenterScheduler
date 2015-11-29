package core.event.dao;

import core.event.*;
import core.service.SessionManager;
import core.service.TestingCenterInfoRetrieval;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Repository
public class AppointmentDaoImp implements AppointmentDao {

    public AppointmentDaoImp() {

    }

    @Override
    public List findAllAppointment() {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        List appointments = session.createQuery("FROM Appointment").list();
        session.close();
        return appointments;
    }

    @Override
    public List findAllByStudent(String NetId) {
        List result;
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery
                ("FROM Appointment A WHERE A.studentId = :stuId");
        query.setParameter("stuId", NetId);
        tx.commit();
        result = query.list();
        session.close();
        return result;
    }

    public List findAllAppointmentByTime(LocalDateTime time){
        ArrayList<Appointment> result = new ArrayList<Appointment>();
        List allAppointment = findAllAppointment();
        Appointment appointmentIter = new Appointment();
        TestingCenterInfoRetrieval info = new TestingCenterInfoRetrieval();
        int gap = info.findByTerm(info.getCurrentTerm()).getGap();
        for(int i = 0; i < allAppointment.size(); i++) {
            appointmentIter = (Appointment)allAppointment.get(i);
            if ( (time.minusMinutes(gap).isBefore(appointmentIter.getEndDateTime()))
                    &&  (
                        (time.isAfter(appointmentIter.getStartDateTime()))
                            ||
                                time.isEqual(appointmentIter.getStartDateTime())
                        )
                    ){
                result.add(appointmentIter);
            }
        }
        return result;
    }

    //need to assignSeat
    @Override
    public boolean insertAppointment(Appointment appointment) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
//            TestingCenterTimeSlotsDaoImp tscsImp = new TestingCenterTimeSlotsDaoImp();
//            LocalDateTime begin = appointment.getStartDateTime();
//            TestingCenterTimeSlots tscs = tscsImp.findTimeSlotById(Integer.toString(begin.getDayOfYear()) +
//                    Integer.toString(begin.getHour()) + Integer.toString(begin.getMinute()));
//            tscsImp.insertTimeSlot(tscs);
            session.save(appointment);
            tx.commit();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean deleteAppointment(String appointmentId) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            TestingCenterTimeSlotsDaoImp tscsImp = new TestingCenterTimeSlotsDaoImp();
            Appointment appt = findAppointmentById(appointmentId);
            LocalDateTime begin = appt.getStartDateTime();
            TestingCenterTimeSlots tscs = tscsImp.findTimeSlotById(Integer.toString(begin.getDayOfYear()) +
                    Integer.toString(begin.getHour()) + Integer.toString(begin.getMinute()));
            tscs.releaseSeat(appt);
            Query query = session.createQuery
                    ("delete from Appointment R where R.appointmentID = :appointmentID");
            query.setParameter("appointmentID", appointmentId);

            tx.commit();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return  true;
    }

    @Override
    public boolean updateAppointment(Appointment appointment, String id){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Query query = session.createQuery
                    ("update Appointment A set A  = :A where A.appointmentID = :appointmentID");
            query.setParameter("A", appointment);
            query.setParameter("appointmentID", id);
            query.executeUpdate();
            tx.commit();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return  true;
    }

    @Override
    public List<Appointment> findAllAppointmentsByTerm(Term term) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        List<Appointment> appointments = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Appointment a where a.startDateTime >= :startDate and :endDate >= a.endDateTime");
            query.setTimestamp("endDate", Date.from(term.getTermEndDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            query.setTimestamp("startDate", Date.from(term.getTermStartDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            // If query won't get any record from table, the result will be an empty list.
            appointments = query.list();
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return appointments;
    }

    @Override
    public Appointment findAppointmentById(String AppointmentID) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("FROM Appointment A WHERE A.appointmentID = :appId");
        query.setParameter("appId", AppointmentID);
        tx.commit();
        Appointment result = (Appointment)query.uniqueResult();
        session.close();
        return result;
    }


    /**
     * Check if the attempting appointment is valid. If not, the system will denied it automatically.
     * @param a
     * @return
     */
    public boolean checkLegalAppointment(Appointment a){

        String studentIdCheck = a.getStudentId();
        String examIdCheck = a.getExamId();
        boolean res = true;

        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Exam exam = (Exam)session.get(Exam.class, examIdCheck);

            // 1. Check Student in Ad Hoc List
            if(exam.getExamType().equals(ExamType.AD_HOC)){
                List<StudentEntry> studentEntries = ((AdhocExam)exam).getStudentList();
                for(StudentEntry se: studentEntries){
                    if (se.getNetId().equals(studentIdCheck)){
                        break;
                    }
                }
            }else {
                // 2. Check Student Enroll in Course
                Roster roster = (Roster)session.get(Roster.class, new Roster(exam.getCourseId(),studentIdCheck));
                if (roster == null) {
                    return false;
                }
            }

            //the appt time is during exam time period
            //check d
            if((exam.getStartDateTime().isBefore(a.getStartDateTime()))
                    &&(exam.getEndDateTime().isAfter(a.getEndDateTime()))) {
                //map to object
                String hql = "FROM Appointment b WHERE b.studentId = :stuId";
                String stuId = studentIdCheck;
                Query query = session.createQuery(hql);
                query.setParameter("stuId", "%" + stuId + "%");
                List<Appointment> list = query.list();
                Iterator it = list.iterator();

                TestingCenterInfo testingCenterInfo = (TestingCenterInfo)session.
                        get(TestingCenterInfo.class, exam.getTerm());


                ArrayList<Appointment> possibleLists = new ArrayList<Appointment>();
                while (it.hasNext()) {
                    Appointment appt = new Appointment();
                    appt = (Appointment) it.next();
                    //check b
                    if (!appt.getExamId().equals(examIdCheck)) {
                        //check c and gap
                        if ( ((a.getStartDateTime().minusMinutes(testingCenterInfo.getGap())).isAfter(appt.getEndDateTime())) ||
                                (a.getEndDateTime().isBefore((appt.getStartDateTime().minusMinutes(testingCenterInfo.getGap())))) ) {
                            //continue
                            possibleLists.add(appt);
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }

                Appointment apptIter;
                int i = 0;
                while(i < possibleLists.size()) {
                    apptIter = possibleLists.get(i);
                    //check e (seat)
                    String timeSlotId = Integer.toString(a.getStartDateTime().getDayOfYear()) +
                            Integer.toString(a.getEndDateTime().getHour()) + Integer.toString(a.
                            getStartDateTime().getMinute());
                    TestingCenterTimeSlots testingCenterTimeSlots = (TestingCenterTimeSlots)
                            session.get(TestingCenterTimeSlots.class, timeSlotId);
                    if (testingCenterTimeSlots.checkSeatAvailable()){
                        testingCenterTimeSlots.assignSeat(apptIter);
                        return true;
                    }
                    i++;
                }
                return false;
            }
            else{
                return false;
            }
        }catch (HibernateException he) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return true;
    }
}
