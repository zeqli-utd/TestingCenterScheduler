package core.event;

import core.event.dao.AppointmentDao;
import core.event.dao.AppointmentDaoImp;
import core.event.dao.ExamDao;
import core.event.dao.ExamDaoImp;

import java.time.temporal.ChronoUnit;

public class Utilization {

    private double utilzExpection;
    private double utilzActual;
    private int numApprove;
    private int numStudent;
    private int numDay;
    private double gap;
    private int day;
    private String exam;
    private int numSeat;

    private TestingCenterInfo center = new TestingCenterInfo();
    //
    AppointmentDao appointmentDao = new AppointmentDaoImp();
    ExamDao examDao = new ExamDaoImp();

    public double countUtilzActual(){
        double TotalDuration = 0;//
        double Hours = (double)ChronoUnit.MINUTES.between(center.getOpen(), center.getClose())/60;////

        for (Appointment appointment : appointmentDao.findAllAppointment()) {
            //System.out.println(appointment.getAppointmentID());
            TotalDuration += (double)ChronoUnit.MINUTES.between(appointment.getStartDateTime(), appointment.getEndDateTime())/60;
        }
        utilzActual = (TotalDuration / (double)(numSeat * Hours))*100;
        return utilzActual;
    }

    public double countUtilzExpection(){
        double TotalExamDuration = 0;
        double Hours = (double)ChronoUnit.MINUTES.between(center.getOpen(), center.getClose())/60;////

//        System.out.println(examDao.find);

        for (Exam exam: examDao.getAllExams()) {
//            System.out.println(Exam.getDuration()+gap);
//            System.out.println((double)Exam.getNumStudentNeed());
//            System.out.println((double)Exam.getNumStudentAppointment()/(double)(day*24));
//            System.out.println((double)Exam.getNumStudentAppointment());
//            System.out.println((double)day);
            TotalExamDuration += (exam.getDuration()+ gap) * (((double)exam.getNumStudentNeed() - (double)exam.getNumStudentAppointment())/(double)(day*24)) ;
        }

        return  utilzActual + (TotalExamDuration)*100;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
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
