package core.event.dao;

import core.event.TestingCenterTimeSlots;

import java.util.ArrayList;
import java.util.List;

public interface TestingCenterTimeSlotsDao {
    List<TestingCenterTimeSlots> findAllTimeSlots();

    List<TestingCenterTimeSlots> findAllTimeSlotsByExamId(String examId);

    TestingCenterTimeSlots findTimeSlotById(String timeSlotId);

    boolean insertTimeSlot(TestingCenterTimeSlots timeSlots);

    boolean insertTimeSlotsByExamId(String examId);

    boolean deleteTimeSlot(String timeSlotId);

    boolean updateTimeSlot(TestingCenterTimeSlots tcts);
}
