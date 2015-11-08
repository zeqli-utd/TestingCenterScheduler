package core.service;

import core.event.TestingCenterInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TestingCenterInfoRetrieval {
    /**
     * returns the testing center information
     * @return
     */
    TestingCenterInfo retrieveInfo();

    /**
     * update all of testing center information by
     * taking HashMap as a parameter
     * @param testingCenterInfo
     * @return return true if the update is a success
     *         return false if there is an error, an exception will be
     *         thrown
     */
    boolean updateTestingCenterInfo(TestingCenterInfo testingCenterInfo);

    /**
     * update a field of testing center information specified
     * by the fieldName argument with the new value. This method should
     * be able to perform a getInfoByName or setInfoByName operation.
     * @param fieldName
     * @param value
     * @return
     */
    boolean updateField(String fieldName, Object value);

    /**
     * add closeDates to testing center information
     * @param closeDates
     * @return
     */
    boolean addCloseDates(LocalDate[] closeDates);

    /**
     * delete closeDates in testing center information
     * @return
     */
    boolean deleteCloseDates();
    /**
     * add reserveDateTimes to testing center information
     * @param reserveDateTimes
     * @return
     */
    boolean addReserveDateTimes(LocalDateTime[] reserveDateTimes);

    /**
     * delete reserveDateTimes in testing center information
     * @return
     */
    boolean deleteReserveDateTimes();
}
