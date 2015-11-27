package core.event;

import core.event.dao.AppointmentDao;
import core.event.dao.AppointmentDaoImp;
import core.event.dao.ExamDao;
import core.event.dao.ExamDaoImp;
import core.service.SessionManager;
import core.service.TestingCenterInfoRetrieval;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class Utilization {

    private double utilzExpection;
    private double utilzActual;
    private int numApprove;
    private int numStudent;
    private int numDay;
    private double gap;
    private String exam;
    private int numSeat;
    AppointmentDao appointmentDao;
    ExamDao examDao;

    @Autowired
    TestingCenterInfoRetrieval testingCenterInfoRetrieval;

    TestingCenterInfo center;

    public Utilization(){
        //center = new TestingCenterInfo();
        appointmentDao = new AppointmentDaoImp();
        examDao = new ExamDaoImp();
    }

    public double countUtilzActual(LocalDateTime dateTime){
        center = testingCenterInfoRetrieval.findByTerm(testingCenterInfoRetrieval.getTermByDay(dateTime));
        LocalDate date = dateTime.toLocalDate();

        double TotalDuration = 0;//
        double Hours = (double)ChronoUnit.MINUTES.between(center.getOpen(), center.getClose())/60;////

        for (Appointment appointment : appointmentDao.findAllAppointment()) {
            //System.out.println(appointment.getAppointmentID());
            LocalDate aday = appointment.getStartDateTime().toLocalDate();
            //if(aday.getYear()==day.getYear() && aday.getMonth()==day.getMonth() && aday.getDayOfMonth()==day.getDayOfMonth()){
            if(aday.isEqual(date)){
                TotalDuration += (double)ChronoUnit.MINUTES.between(appointment.getStartDateTime(), appointment.getEndDateTime())/60;
            }
        }
        utilzActual = TotalDuration / (numSeat * Hours);
        return utilzActual;
    }

    public double countUtilzExpection(LocalDateTime dateTime){
        center = testingCenterInfoRetrieval.findByTerm(testingCenterInfoRetrieval.getTermByDay(dateTime));
        LocalDate date = dateTime.toLocalDate();

        double ExpectedApptmentsDuration = 0;
        double Hours = (double)ChronoUnit.MINUTES.between(center.getOpen(), center.getClose())/60;////

//      System.out.println(examDao.find);

        for (Exam exam: examDao.getAllExams()) {
            LocalDate startDay = exam.getStartDateTime().toLocalDate();
            LocalDate endDay = exam.getStartDateTime().toLocalDate();

            if( (startDay.isBefore(date) || startDay.isEqual(date)) && (endDay.isAfter(date) || endDay.isEqual(date)) ){
                ExpectedApptmentsDuration += (exam.getDuration()+ gap) * (((double)exam.getCapacity() - (double)exam.getNumAppointments())/ exam.getDayDuration()) ;
            }
        }
        utilzExpection = utilzActual + ExpectedApptmentsDuration/(numSeat * Hours);
        return  utilzExpection;
    }


    public double getUtilzExpection() {
        return utilzExpection;
    }

    public void setUtilzExpection(double utilzExpection) {
        this.utilzExpection = utilzExpection;
    }

    public double getUtilzActual() {
        return utilzActual;
    }

    public void setUtilzActual(double utilzActual) {
        this.utilzActual = utilzActual;
    }

    public int getNumApprove() {
        return numApprove;
    }

    public void setNumApprove(int numApprove) {
        this.numApprove = numApprove;
    }

    public int getNumStudent() {
        return numStudent;
    }

    public void setNumStudent(int numStudent) {
        this.numStudent = numStudent;
    }

    public int getNumDay() {
        return numDay;
    }

    public void setNumDay(int numDay) {
        this.numDay = numDay;
    }

    public double getGap() {
        return gap;
    }

    public void setGap(double gap) {
        this.gap = gap;
    }

    public int getNumSeat() {
        return numSeat;
    }

    public void setNumSeat(int numSeat) {
        this.numSeat = numSeat;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public TestingCenterInfo getCenter() {
        return center;
    }

    public AppointmentDao getAppointmentDao() {
        return appointmentDao;
    }

    public ExamDao getExamDao() {
        return examDao;
    }

}
