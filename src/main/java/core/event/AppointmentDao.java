package core.event;

import java.util.List;

public interface AppointmentDao {
    List<Appointment> findAllAppointment();

    List<Appointment> findAllByStudent(String netId);

    List<Appointment> findAllAppointmentsByTerm(Term term);

    //List<Appointment> findAllByInstructor(String netId);   why???

    Appointment findByAppointmentID(String AppointmentID);

    boolean insertAppointment(Appointment appointment);

    boolean updateAppointment(Appointment appointment, String id);

    boolean deleteAppointment(String appointmentId);



}
