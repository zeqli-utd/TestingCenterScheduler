package core.service;

import core.event.TestingCenterInfo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class TestingCenterInfoRetrievalImp implements TestingCenterInfoRetrieval {

    private static TestingCenterInfo testingCenterInfo = new TestingCenterInfo();

    /**
     * retrieves the testing center information, return the object
     * of class TestingCenterInfo
     * @return
     */
    @Override
    public TestingCenterInfo retrieveInfo() {
        return testingCenterInfo.deserialize();
    }

    /**
     * update the testing center information by
     * taking HashMap as a parameter
     * @param testingCenterInfoMap
     * @return return true if the update is a success
     *         return false if there is an error, an exception will be
     *         thrown
     */
    @Override
    public boolean updateTestingCenterInfo(TestingCenterInfo testingCenterInfoMap) {
        return testingCenterInfo.update(testingCenterInfo);
    }

    @Override
    public boolean updateField(String fieldName, Object value) {
        switch(fieldName) {
            case "numSeats":
                testingCenterInfo.setNumSeats((int) value);
                return true;
            case "numSetAsideSeats":
                testingCenterInfo.setNumSetAsideSeats((int) value);
                return true;
            case "open":
                testingCenterInfo.setOpen((LocalTime) value);
                return true;
            case "close":
                testingCenterInfo.setClose((LocalTime) value);
                return true;
            //need i to edit value for this field
//            case "closeDateRanges":     testingCenterInfo.setCloseDateRanges((List<LocalDate[]>)value);
//                                        return true;
            //also need i to edit value for this field
//            case "reserveRanges":       testingCenterInfo.setReserveRanges((List<LocalDateTime[]>)value);
//                                        return true;
            case "gap":
                testingCenterInfo.setGap((int) value);
                return true;
            case "reminderInterval":
                testingCenterInfo.setReminderInterval((int) value);
                return true;
        }
        return false;
    }

    @Override
    public boolean addCloseDates(LocalDate[] closeDates){
        testingCenterInfo.getCloseDateRanges().add(closeDates);
        return false;
    }

    @Override
    public boolean deleteCloseDates(){
        return false;
    }

    @Override
    public boolean addReserveDateTimes(LocalDateTime[] reserveDateTimes){
        testingCenterInfo.getReserveRanges().add(reserveDateTimes);
        return false;
    }

    @Override
    public boolean deleteReserveDateTimes(){
        return false;
    }
}
