package core;

import core.event.Appointment;
import core.event.Course;
import core.event.Exam;
import core.event.Term;
import core.service.SessionManager;
import org.apache.log4j.Logger;
import org.hibernate.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Report {
    private static final Logger log = Logger.getLogger(Report.class);

    private List<Exam> exams;
    private List<Course> courses;
    private List<Appointment> appointments;

    private Term startTerm;
    private Term endTerm;

    public Report() {

    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Term getEndTerm() {
        return endTerm;
    }

    public void setEndTerm(Term endTerm) {
        this.endTerm = endTerm;
    }

    public Term getStartTerm() {
        return startTerm;
    }

    public void setStartTerm(Term startTerm) {
        this.startTerm = startTerm;
    }

    public List<Appointment> getAppointments(Term term) {
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;

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


    public String showDayReport(Term term) {
        appointments = getAppointments(term);
        String s = "";
        s += "| - " + "------------Show report by Day------------\n";
        // Print A List of Entire Semester, and Appointment corresponding to each day.
        for (LocalDate localDate = term.getTermStartDate(); localDate.isBefore(term.getTermEndDate().plusDays(1)); localDate = localDate.plusDays(1)) {


            List<Appointment> list = new LinkedList<>();
            int i = 0;
            for (Appointment appt : appointments) {
                i = 0;
                // Check if the appointment meet up today.
                if (appt.getStartDateTime().toLocalDate().equals(localDate)) {
                    i++;
                    list.add(appt);
                }
            }

            s += "| - Date: " + localDate.toString() + " - - - - - - " + i + " Appointments \n";
            for(Appointment appt:list){
                s += "  | - ExamId: " + appt.getExamId() + "\n";
                s += "  | - AppointmentId: " + appt.getAppointmentID() + "\n";
                s += "  | - MadeBy: " + appt.getMadeBy() + "\n";
            }

        }
        s += "| - End of report...\n";
        log.info("\n" + s);
        return s;
    }

    public String showWeekReport(Term term) {
        appointments = getAppointments(term);
        String s = "";
        s += "| - ------------Show report by Week------------\n";

        // Assume Each Semester Starts on Monday
        for (LocalDate date = term.getTermStartDate(); date.isBefore(term.getTermEndDate().plusDays(1)); date = date.plusWeeks(1)) {

            List<Appointment> list = new LinkedList<>();
            for (Appointment appt : appointments) {
                if (appt.getStartDateTime().toLocalDate().isAfter(date) && appt.getEndDateTime().toLocalDate().isBefore(date.plusWeeks(1))) {
                    list.add(appt);
                }
            }

            // Count courses.
            HashMap<String, Integer> courseCount = new HashMap<>();
            for (Appointment a:list){
                String cName = a.getExamId();

                // If Key exist
                if (courseCount.get(cName)!=null){
                    courseCount.put(cName, courseCount.get(cName) + 1);
                }
                else {
                    courseCount.put(cName, 1);
                }
            }
            s += "| - Week of " + date.format(DateTimeFormatter.ofPattern("LLLL dd, yyyy")) + " ------------ " + list.size() + "Appointments\n";


            // Read Course
            Iterator it = courseCount.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry pair = (Map.Entry)it.next();
                s += "  | - " + pair.getKey() + " - - - - - - - - - - - " + pair.getValue() + " Appointments\n";
            }

        }
        s += "| - End of report...\n";
        log.info("\n" + s);
        return s;
    }

    // Report Date
    public String showTermReport(Term term){
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        Set<String> courses = new LinkedHashSet<>();
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("select e.examId from Exam e where  :startDate <= e.startDateTime and e.endDateTime <= :endDate");

            log.debug("Begin Term " + Date.from(term.getTermStartDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            log.debug("End Term " + Date.from(term.getTermEndDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            query.setTimestamp("endDate", Date.from(term.getTermEndDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            query.setTimestamp("startDate", Date.from(term.getTermStartDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));


            List<String> listResult = query.list();
            for(String row : listResult){
                String s = row;
                log.debug("Read exam id from table Exam: " + s);
                courses.add(s);
            }
            tx.commit();
        } catch (HibernateException he) {
            if(tx!=null){
                tx.rollback();
            }
            log.error("Error with Table Join", he);
        }

        String s = "";
        s += "| - ------------Show report by Term------------\n";
        s += "| - " + term.getTermName() + " starts from " + term.getTermStartDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + "\n";
        Iterator<String> it = courses.iterator();
        while (it.hasNext()){
            s += "| - ExamId: " + it.next() + "\n";
        }
        s += "| - " + term.getTermName() + " ends on " + term.getTermEndDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + "\n";

        s += "| - End of report...\n";
        log.info("\n" + s);
        return s;
    }

    // Report Date
    public String showTermRangeReport(List<Term> terms){
        String s = "";
        s += "| - ------------Show report by Term Range------------\n";

        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        Set<String> courses = new LinkedHashSet<>();

        for (Term term: terms){
            try{
                tx = session.beginTransaction();
                Query query = session.createQuery("select count(*) from Appointment a where  :startDate <= a.startDateTime and a.endDateTime <= :endDate");

                log.debug("Begin Term " + term.getTermName() + " " + Date.from(term.getTermStartDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                log.debug("End Term " + term.getTermName() + " " + Date.from(term.getTermEndDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

                query.setTimestamp("startDate", Date.from(term.getTermStartDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                query.setTimestamp("endDate", Date.from(term.getTermEndDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

                try {
                    Long apptInTerm = (Long) query.uniqueResult();
                    s += "| - " +  "In Term " + term.getTermName() + " - - - - - - - - - - " + apptInTerm + " Appointments\n";
                } catch (NonUniqueResultException nre){
                    log.error("" + nre);
                }

                tx.commit();
            } catch (HibernateException he) {
                if(tx!=null){
                    tx.rollback();
                }
                log.error("Error with Table Join", he);
            }
        }
        s += "| - End of report...\n";
        log.info("\n" + s);
        return s;
    }


}
