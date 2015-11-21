package core.event;

import org.hibernate.HibernateException;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDao {
    List findAll();

    Reservation findByID(String reservationID);

    List findByDate(LocalDate date);

    List findByInstructorId(String InstructorID);

    boolean insertReservation(Reservation reservation) throws HibernateException;

    boolean updateReservation(Reservation reservation, String id);

    boolean deleteReservation(String reservationId);

    boolean setType(String reservationId, String type);

    String getTypeById(String reservationId);

    boolean setStatus(String reservationId, String Status);

    String getStatusById(String reservationId);

    void listAllReservationByInstructorId(String instructorId);
}
