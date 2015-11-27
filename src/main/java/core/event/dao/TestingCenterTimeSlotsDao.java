package core.event.dao;

import core.event.Appointment;
import core.event.TestingCenterTimeSlots;

import java.util.List;

/**
 * Created by Acer on 11/27/2015.
 */
public interface TestingCenterTimeSlotsDao {
    List<TestingCenterTimeSlots> findAllTimeSlots();

    TestingCenterTimeSlots findTimeSlotById(String timeSlotId);

    boolean insertTimeSlot(TestingCenterTimeSlots timeSlots);

    boolean deleteTimeSlot(String timeSlotId);
}
