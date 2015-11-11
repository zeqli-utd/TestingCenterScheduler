package core.event;

import org.hibernate.HibernateException;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDao {
    List<Reservation> findAll();

    Reservation findByID(String reservationID);

    List<Reservation> findByDate(LocalDate date);

    List<Reservation> findByInstructorId(String InstructorID);

    void insertReservation(Reservation reservation) throws HibernateException;

    boolean updateReservation(Reservation reservation, String id);

    boolean deleteReservation(Reservation reservation);

    boolean setType(String reservationId, String type);

    String getTypeById(String reservationId);

    boolean setStatus(String reservationId, String Status);

    String getStatusById(String reservationId);

    void listAllReservationByInstructorId(String instructorId);
}
