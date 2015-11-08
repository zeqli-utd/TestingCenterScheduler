package test;//package test;
//
//import core.user.*;
//import core.event.*;
//import org.apache.log4j.Logger;
//
//import java.time.LocalDateTime;
//
//public class SchedulingRequestTest {
//    public static final Logger log = Logger.getLogger(Log4J.class);
//    public static void main(String[] args){
//
//        //                              (Reservation Id, Start Time, End Time, Instructor Id, Term)
//        Reservation R1 = new Reservation("resId1", LocalDateTime.of(2015,11,1,10,0), LocalDateTime.of(2015,11,1,14,30),"ins1", "Fall 2015");
//        Reservation R2 = new Reservation("resId2", LocalDateTime.of(2015,11,2,10,0), LocalDateTime.of(2015,11,1,14,30),"ins2", "Fall 2015");
//        Reservation R3 = new Reservation("resId3", LocalDateTime.of(2015,11,3,10,0), LocalDateTime.of(2015,11,1,14,30),"ins1", "Fall 2015");
//        Reservation R4 = new Reservation("resId4", LocalDateTime.of(2015,11,4,10,0), LocalDateTime.of(2015,11,1,14,30),"ins4", "Fall 2015");
//        Reservation R5 = new Reservation("resId5", LocalDateTime.of(2015,11,5,10,0), LocalDateTime.of(2015,11,1,14,30),"ins5", "Fall 2015");
//        //Reservation R1 = new Reservation("1", LocalDateTime.of(2015,11,1,10,0), LocalDateTime.of(2015,11,1,14,30),"1", "Fall 2015");
//        //Reservation R1 = new Reservation("1", LocalDateTime.of(2015,11,1,10,0), LocalDateTime.of(2015,11,1,14,30),"1", "Fall 2015");
//
//        Exam E1 = new Exam("ex1", "Course", LocalDateTime.of(2015,11,1,10,0), LocalDateTime.of(2015,11,1,12,00), 2, 60, 100);
//        Exam E2 = new Exam("ex2", "Ad Hoc", LocalDateTime.of(2015,11,2,10,0), LocalDateTime.of(2015,11,2,12,00), 2, 60, 100);
//
//        Administrator Admin = new Administrator();
//        Instructor Ins = new Instructor();
//
//        ReservationDao reservationDao = new ReservationDaoImp();
//        ExamDao examDao = new ExamDaoImp();
//        //AdministratorDao administratorDao = new AppointmentDaoImp();
//        //System.out.println(reservationDao.insertReservation(R1));
//
//        //Test Scheduling Activity Flow
//        boolean b1 = reservationDao.insertReservation(R1);
//        Reservation t1 = reservationDao.findByID("resId1");
//        String status = reservationDao.getStatusById("resId1");
//        String type = reservationDao.getTypeById("resId1");
//        log.info("|  -Insert First Reservation, Insert result: " + b1);
//        log.info("|  -Reservation Id: " + t1.getReservationID());
//        log.info("|  -StartDateTime: " + t1.getStartDateTime());
//        log.info("|  -EndDateTime: " + t1.getEndDateTime());
//        log.info("|  -Instructor Id: " + t1.getInstructorId());
//        log.info("|  -Term: " + t1.getTerms());
//        log.info("|  -Status " + status);
//        log.info("|  -Type: " + type);
//        log.info("|  Here we set the type to be Ad Hoc");
//
//
//        // Set Reservation 1 to Ad Hoc Type
//        reservationDao.setType("resId1", "Ad Hoc");
//        status = reservationDao.getStatusById("resId1");
//        type = reservationDao.getTypeById("resId1");
//        log.info("|  -Reservation Id: " + t1.getReservationID());
//        log.info("|  -StartDateTime: " + t1.getStartDateTime());
//        log.info("|  -EndDateTime: " + t1.getEndDateTime());
//        log.info("|  -Instructor Id: " + t1.getInstructorId());
//        log.info("|  -Term: " + t1.getTerms());
//        log.info("|  -Status " + status);
//        log.info("|  -Type: " + type);
//
//        // Add Reservation
//        boolean b2 = reservationDao.insertReservation(R2);
//
//        // Test if Admin or Instructor can find a Reservation by a unique identifier.
//        Reservation t2 = reservationDao.findByID("resId2");
//        status = reservationDao.getStatusById("resId2");
//        type = reservationDao.getTypeById("resId2");
//        log.info("|  -Insert Second Reservation, Insert result: " + b2);
//        log.info("|  -Reservation Id: " + t2.getReservationID());
//        log.info("|  -StartDateTime: " + t2.getStartDateTime());
//        log.info("|  -EndDateTime: " + t2.getEndDateTime());
//        log.info("|  -Instructor Id: " + t2.getInstructorId());
//        log.info("|  -Term: " + t2.getTerms());
//        log.info("|  -Status " + status);
//        log.info("|  -Type: " + type);
//        log.info("|  Here we set the status of R2 to be Approved");
//        reservationDao.setStatus("resId2", "Approved");
//
//
//        boolean be2 = reservationDao.insertReservation(R2);
//        status = reservationDao.getStatusById("resId2");
//        type = reservationDao.getTypeById("resId2");
//        log.info("|  -Reservation Id: " + t2.getReservationID());
//        log.info("|  -StartDateTime: " + t2.getStartDateTime());
//        log.info("|  -EndDateTime: " + t2.getEndDateTime());
//        log.info("|  -Instructor Id: " + t2.getInstructorId());
//        log.info("|  -Term: " + t2.getTerms());
//        log.info("|  -Status " + status);
//        log.info("|  -Type: " + type);
//
//
//        boolean b3 = reservationDao.insertReservation(R3);
//        Reservation t3 = reservationDao.findByID("resId3");
//        status = reservationDao.getStatusById("resId3");
//        type = reservationDao.getTypeById("resId3");
//        log.info("|  -Insert Third Reservation, Insert result: " + b3);
//        log.info("|  -Reservation Id: " + t3.getReservationID());
//        log.info("|  -StartDateTime: " + t3.getStartDateTime());
//        log.info("|  -EndDateTime: " + t3.getEndDateTime());
//        log.info("|  -Instructor Id: " + t3.getInstructorId());
//        log.info("|  -Term: " + t3.getTerms());
//        log.info("|  -Status " + status);
//        log.info("|  -Type: " + type);
//        log.info("|  Here we set the status of R2 to be Denied");
//        reservationDao.setStatus("resId3", "Denied");
//        status = reservationDao.getStatusById("resId3");
//        type = reservationDao.getTypeById("resId3");
//        log.info("|  -Reservation Id: " + t3.getReservationID());
//        log.info("|  -StartDateTime: " + t3.getStartDateTime());
//        log.info("|  -EndDateTime: " + t3.getEndDateTime());
//        log.info("|  -Instructor Id: " + t3.getInstructorId());
//        log.info("|  -Term: " + t3.getTerms());
//        log.info("|  -Status " + status);
//        log.info("|  -Type: " + type);
//
//
//        // Test Cancel a Reservation, simply just remove it from table.
//        boolean d1 = reservationDao.deleteReservation(R2);
//       /* t1 = reservationDao.findAll().get(0);
//        t2 = reservationDao.findAll().get(1);
//        S = reservationDao.getStatusById(t1.getReservationID());
//        T = reservationDao.getTypeById(t1.getReservationID());
//        log.info("|  -Delete Pending request, which Reservation Id is 2 ");*/
//        log.info("|  ------------------Delete Reservation--------------------- ");
//        log.info("|  -Delete Reservation result: " + d1);
//        log.info("|  ------------------Delete Reservation--------------------- ");
//        /*log.info("|  -Then we print out all the left Reservations");
//        log.info("|  -Reservation Id: " + t1.getReservationID());
//        log.info("|  -StartDateTime: " + t1.getStartDateTime());
//        log.info("|  -EndDateTime: " + t1.getEndDateTime());
//        log.info("|  -Instructor Id: " + t1.getInstructorId());
//        log.info("|  -Term: " + t1.getTerms());
//        log.info("|  -Status " + S);
//        log.info("|  -Type: " + T);
//        S = reservationDao.getStatusById(t2.getReservationID());
//        T = reservationDao.getTypeById(t2.getReservationID());
//        log.info("|  ----------------------------------------------------------------------------");
//        log.info("|  -Reservation Id: " + t2.getReservationID());
//        log.info("|  -StartDateTime: " + t2.getStartDateTime());
//        log.info("|  -EndDateTime: " + t2.getEndDateTime());
//        log.info("|  -Instructor Id: " + t2.getInstructorId());
//        log.info("|  -Term: " + t2.getTerms());
//        log.info("|  -Status " + S);
//        log.info("|  -Type: " + T);*/
//
//        // Test List all Reservations requested by an Instructor
//        reservationDao.listAllReservationByInstructorId("ins1");
//
//        E1.setAttendance(1);
//        E2.setAttendance(0);
//        b1 = examDao.addExam(E1);
//        b2 = examDao.addExam(E2);
//
//        System.out.println("THIS IS True of false: -------------------------------------------------------: "+b1+" "+b2);
//        examDao.listExamByApprovedRequest("ex1");
//
//
//
//
//    }
//}
//