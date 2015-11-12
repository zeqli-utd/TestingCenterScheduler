package core;

import core.event.*;
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

    /**
     * Show day report
     * @param term Specified term
     * @return
     */
    public String showDayReport(Term term) {
        // First get all appointments in a specified term.
        AppointmentDao appointmentDao = new AppointmentDaoImp();
        appointments = appointmentDao.findAllAppointmentsByTerm(term);

        // Output the result in a String
        String sDayReport = "";
        sDayReport += "### Reports by Day ###\n";
        sDayReport += String.format("%10s | %20s", "Date ", "# of Appointments" ) + "\n";

        // Print a list of day in entire semester, and number of appointments corresponding to each day.
        for (LocalDate localDate = term.getTermStartDate(); localDate.isBefore(term.getTermEndDate().plusDays(1));
             localDate = localDate.plusDays(1)) {
            List<Appointment> apptsOnCertainDay = new LinkedList<>();
            int numAppts = 0;

            // Walk through the appointments list of this term.
            for (Appointment appt : this.appointments) {
                // Check if the appointment meet up today.
                if (appt.getStartDateTime().toLocalDate().equals(localDate)) {
                    numAppts++;
                    apptsOnCertainDay.add(appt);
                }
            }

            sDayReport += String.format("%s%13d",localDate.toString(), numAppts) + "\n";
            for(Appointment appt:apptsOnCertainDay){
                sDayReport += "  | - ExamId: " + appt.getExamId() + "\n";
                sDayReport += "  | - AppointmentId: " + appt.getAppointmentID() + "\n";
                sDayReport += "  | - MadeBy: " + appt.getMadeBy() + "\n";
            }
        }
        sDayReport += "### End of report... ###\n";
        log.debug("\n\n" + sDayReport);
        return sDayReport;
    }

    /**
     * Show Weekly Report
     * @param term Specified Term
     * @return
     */
    public String showWeekReport(Term term) {
        AppointmentDao appointmentDao = new AppointmentDaoImp();
        appointments = appointmentDao.findAllAppointmentsByTerm(term);
        String sWeekReport = "";
        sWeekReport += "### Reports by Week ###\n";

        // Assume each semester starts on Monday
        for (LocalDate date = term.getTermStartDate(); date.isBefore(term.getTermEndDate().plusDays(1)); date = date.plusWeeks(1)) {
            List<Appointment> list = new LinkedList<>();
            for (Appointment appt : appointments) {
                if (appt.getStartDateTime().toLocalDate().isAfter(date) && appt.getEndDateTime().toLocalDate().isBefore(date.plusWeeks(1))) {
                    list.add(appt);
                }
            }
            // Count the number of appointments for each course.
            HashMap<String, Integer> courseCount = new HashMap<>();
            for (Appointment a:list){
                String sCourseName = a.getExamId();

                if (courseCount.get(sCourseName)!=null){
                    courseCount.put(sCourseName, courseCount.get(sCourseName) + 1);
                }
                else {
                    courseCount.put(sCourseName, 1);
                }
            }

            sWeekReport += "| - Week of " + String.format("%11s", date.format(DateTimeFormatter.ofPattern("LLLL dd, yyyy"))) +
                    String.format("%12d", list.size()).replace(' ', '-') + " Appointments\n";

            // Iterate through hash table and get the appointments counts.
            Iterator it = courseCount.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry pair = (Map.Entry)it.next();
                sWeekReport += "  | - " + pair.getKey() + String.format("%12d", pair.getValue()).replace(' ', '-')
                 + " Appointments\n";
            }
        }
        sWeekReport += "### End of report... ###\n";
        log.debug("\n\n" + sWeekReport);
        return sWeekReport;
    }

    /**
     * Show Term Report
     * @param term
     * @return
     */
    public String showTermReport(Term term){
        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;
        Set<String> courses = new HashSet<>();
        try{
            tx = session.beginTransaction();

            // Get all exams for this term.
            Query query = session.createQuery("select e.examId from Exam e where  :startDate <= e.startDateTime and e.endDateTime <= :endDate");
            query.setTimestamp("endDate", Date.from(term.getTermEndDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            query.setTimestamp("startDate", Date.from(term.getTermStartDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

            log.debug("Begin Term " + Date.from(term.getTermStartDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            log.debug("End Term " + Date.from(term.getTermEndDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

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
//          finally {
//            if(session.isOpen()) {
//                session.close();
//            }
//        }

        String sTermReport = "";
        sTermReport += "### Reports by Term ###\n";
        sTermReport += "| - " + term.getTermName() + " starts from " + term.getTermStartDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + "\n";
        Iterator<String> it = courses.iterator();
        while (it.hasNext()){
            sTermReport += "| - ExamId: " + it.next() + "\n";
        }
        sTermReport += "| - " + term.getTermName() + " ends on " + term.getTermEndDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + "\n";

        sTermReport += "### End of report... ###\n";
        log.debug("\n" + sTermReport);
        return sTermReport;
    }

    /**
     * Show Term Range Report
     * @param terms List of Terms
     * @return
     */
    public String showTermRangeReport(List<Term> terms){
        String sTermRangeReport = "";
        sTermRangeReport += "### Reports by Term Range ###\n";

        Session session = SessionManager.getInstance().getOpenSession();
        Transaction tx = null;

        for (Term term: terms){
            try{
                tx = session.beginTransaction();
                Query query = session.createQuery("select count(*) from Appointment a where  :startDate <= a.startDateTime and a.endDateTime <= :endDate");
                query.setTimestamp("startDate", Date.from(term.getTermStartDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                query.setTimestamp("endDate", Date.from(term.getTermEndDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

                Long apptInTerm = (Long) query.uniqueResult();
                sTermRangeReport += String.format("| - Term %11s", term.getTermName()) +
                        String.format("%12d", apptInTerm).replace(' ', '-') + " Appointments\n";

                tx.commit();
            } catch (HibernateException he) {
                if (tx != null) {
                    tx.rollback();
                }
                log.error("Error with Table Join", he);
            }
//            finally {
//                if(session.isOpen()) {
//                    session.close();
//                }
//            }
        }
        sTermRangeReport += "### End of report... ###\n";
        return sTermRangeReport;
    }
}
