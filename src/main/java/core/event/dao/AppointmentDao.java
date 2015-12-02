package core.event.dao;

import core.event.Appointment;
import core.event.Term;
import core.event.TestingCenterTimeSlots;

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

    Appointment findAppointmentById(int AppointmentID);

    boolean insertAppointment(Appointment appointment);

    boolean updateAppointment(Appointment appointment, int id);

    boolean deleteAppointment(int appointmentId);

    boolean checkLegalAppointment(Appointment appointment);

    /**
     * Make appointment.
     * @param ap
     * @param slots
     * @return
     */
    boolean makeAppointment(Appointment ap, TestingCenterTimeSlots slots);


}
