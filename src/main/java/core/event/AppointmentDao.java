package core.event;

import java.util.List;

public interface AppointmentDao {
    List findAllAppointment();

    List findAllByStudent(String netId);

    //List<Appointment> findAllByInstructor(String netId);   why???

    Appointment findByAppointmentID(String AppointmentID);

    boolean insertAppointment(Appointment appointment);

    boolean updateAppointment(Appointment appointment, String id);

    boolean deleteAppointment(Appointment appointment);

}
