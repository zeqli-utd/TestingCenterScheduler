package core.event.dao;

import core.event.Appointment;
import core.event.Term;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentDao {
    List<Appointment> findAllAppointment();

    List<Appointment> findAllByStudent(String netId);

    List<Appointment> findAllAppointmentByTime(LocalDateTime time);

    List<Appointment> findAllAppointmentsByTerm(Term term);

    /**
     * For Instructor's functionality seeing appointments and attendance details.
     * Attendance Detail can be calculated in the
     * @param examId
     * @return
     */
    List<Appointment> findAllAppointmentsByExamId(String examId);

    Appointment findAppointmentById(String AppointmentID);

    boolean insertAppointment(Appointment appointment);

    boolean updateAppointment(Appointment appointment, String id);

    boolean deleteAppointment(String appointmentId);

    boolean checkLegalAppointment(Appointment appointment);


}
