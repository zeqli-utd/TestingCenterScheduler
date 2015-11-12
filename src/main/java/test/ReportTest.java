package test;

import core.Report;
import core.event.*;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


public class ReportTest {
    private static Logger log = Logger.getLogger(ReportTest.class);

    public static void main(String[] args){
        Term term1 = new Term(1158,LocalDate.of(2015, 8, 22), LocalDate.of(2015, 12, 17) );
        Term term2 = new Term(1161,LocalDate.of(2015, 12, 25), LocalDate.of(2016, 1, 23));
        Term term3 = new Term(1164,LocalDate.of(2016, 1, 24), LocalDate.of(2016, 5, 20));


        Appointment walApp = new Appointment("cse308examWal", "cse_308", "wal",
                LocalDateTime.of(2015,10,29,1, 0),
                LocalDateTime.of(2015,10,29,2,20),"wal", "5R13", false);

        Appointment yimApp = new Appointment("cse308examYim", "cse_308", "yim",
                LocalDateTime.of(2015,10,30, 1, 0),
                LocalDateTime.of(2015,10,30,2,20),"yim", "5R15", false);

        Appointment zeqApp = new Appointment("cse308examZeq", "cse_308", "zeq",
                LocalDateTime.of(2016,1,30, 1, 0),
                LocalDateTime.of(2016,1,31,2,20),"yim", "5R15", false);

        Appointment yisApp = new Appointment("cse308examYis", "cse_219", "yis",
                LocalDateTime.of(2015,10,30, 1, 0),
                LocalDateTime.of(2015,12,17,2,20),"yim", "5R15", false);

        // Add Test Data Into Table
        AppointmentDaoImp daoImp = new AppointmentDaoImp();
        daoImp.insertAppointment(walApp);
        daoImp.insertAppointment(yimApp);
        daoImp.insertAppointment(yisApp);
        daoImp.insertAppointment(zeqApp);

        Exam exam1 = new Exam("308","ad hoc", LocalDateTime.of(2015,9,20,13,30),
                LocalDateTime.of(2015,9,21,15,0), 1.5,50,80, "Prof1");
        exam1.setExamName("Software Engineering");
        Exam exam2 = new Exam("305","course", LocalDateTime.of(2015,9,20,15,10),
                LocalDateTime.of(2015,9,21,17,40), 2.5,50,80, "Prof2");
        exam2.setExamName("Database System");
        ExamDao exam = new ExamDaoImp();
        exam.addExam(exam1);
        exam.addExam(exam2);

        Report report = new Report();

        // 1. Show Day Report
        report.showDayReport(term1);

        // 2. Show Week Report
        report.showWeekReport(term1);

        // 3. Show Term Report
        report.showTermReport(term1);

        List<Term> terms = new LinkedList<>();
        terms.add(term1);
        terms.add(term2);
        terms.add(term3);

        // 4. Show Report By A Range of Terms
        report.showTermRangeReport(terms);

        // 5. Concatenate All Four Reports Into A Single List to Out Put in File
        String doc = "" + report.showDayReport(term1) + "\n"
                + report.showWeekReport(term1) + "\n"
                + report.showTermReport(term1) + "\n"
                + report.showTermRangeReport(terms);
        log.debug("\n" + doc);
    }
}
