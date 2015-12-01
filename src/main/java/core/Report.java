package core;

import core.event.Appointment;
import core.event.Course;
import core.event.Exam;
import core.event.Term;
import core.event.dao.AppointmentDao;
import core.event.dao.AppointmentDaoImp;
import core.service.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class Report {
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
        String header = "<div id=\"day_report\"><table>" +
                "<tr><td><pre>Appointments Daily Reports</pre></td></tr>" +
                "<tr><td><pre>- - - - -    " + term.getTermName() + "    - - - - -</pre></td></tr>";
        String body = "";
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

            body += "<tr><td><pre>" + String.format("%s%13d",localDate.toString(), numAppts) + "</pre></td></tr>";

        }
        String footer = "</table></div>";
        return header+body+footer;
    }

    /**
     * Show Weekly Report
     * @param term Specified Term
     * @return
     */
    public String showWeekReport(Term term) {
        AppointmentDao appointmentDao = new AppointmentDaoImp();
        appointments = appointmentDao.findAllAppointmentsByTerm(term);

        String header = "<div id=\"week_report\"><table>" +
                "<tr><td><pre>Appointments Weekly Reports</pre></td></tr>" +
                "<tr><td><pre>- - - - -    " + term.getTermName() + "    - - - - -</pre></td></tr>";
        String body = "";
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

            // Week Header
            body += "<tr><td><div><pre>Week of " + String.format("%11s%5d",
                    date.format(DateTimeFormatter.ofPattern("LLLL dd, yyyy")), list.size())
                    + "</pre></div></td></tr>";

            // Week Body


            // Iterate through hash table and get the appointments counts.
            Iterator it = courseCount.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry pair = (Map.Entry)it.next();
                body += "<tr><td><div><pre>"+ String.format("%s%5d",pair.getKey(),pair.getValue()) + "</pre></div></td></tr>";
            }
        }
        String footer = "</table></div>";
        return header+body+footer;
    }

    /**
     * Show Term Report
     * @param term
     * @return
     */
    public String showTermReport(Term term){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        Set<String> courses = new HashSet<>();

        String header = "<div id=\"term_report\"><table>" +
                "<tr><td><pre>Appointment Termly Report</pre></td></tr>" +
                "<tr><td><pre>- - - - -    " + term.getTermName() + "    - - - - -</pre></td></tr>";
        String body = "";

        try{
            tx = session.beginTransaction();

            // Get all exams for this term.
            Query query = session
                    .createQuery
                            ("select e.examId from Exam e where  :startDate <= e.startDateTime and e.endDateTime <= :endDate");
            query.setTimestamp("endDate",
                    Date.from(term.getTermEndDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            query.setTimestamp("startDate",
                    Date.from(term.getTermStartDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

            List<String> listResult = query.list();
            for(String row : listResult){
                String s = row;
                courses.add(s);
            }
            tx.commit();
        } catch (HibernateException he) {
            if(tx!=null){
                tx.rollback();
            }
        }

        Iterator<String> it = courses.iterator();
        while (it.hasNext()){
            body += "<tr><td><pre>"+it.next()+"</pre></td></tr>" ;
        }
        String footer = "</table></div>";
        return header+body+footer;
    }

    /**
     * Show Term Range Report
     * @param terms List of Terms
     * @return
     */
    public String showTermRangeReport(List<Term> terms){
        String header = "<div id=\"term_range_report\"><table>" +
                "<tr><td><pre>Multiple Termly Report</pre></td></tr>" +
                "<tr><td><pre>- - - - -    "+
                terms.get(0).getTermName()+" - "+terms.get(terms.size() - 1).getTermName()+
                "    - - - - -</pre></td></tr>";
        String body = "";

        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        for (Term term: terms){
            try{
                tx = session.beginTransaction();
                Query query = session.createQuery("select count(*) from Appointment a where  :startDate <= a.startDateTime and a.endDateTime <= :endDate");
                query.setTimestamp("startDate", Date.from(term.getTermStartDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                query.setTimestamp("endDate", Date.from(term.getTermEndDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

                Long apptInTerm = (Long) query.uniqueResult();
                body += "<tr><td><pre>"+
                        String.format("%11s%5d", term.getTermName(),apptInTerm)+
                        "</pre></td></tr>";

                tx.commit();
            } catch (HibernateException he) {
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        String footer = "</table></div>";
        return header+body+footer;
    }




}
