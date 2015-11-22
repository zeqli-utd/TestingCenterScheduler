//package core.service;
//
//import core.event.Term;
//import core.event.TestingCenterInfo;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//public interface TestingCenterInfoRetrieval {
//    /**
//     * returns the testing center information
//     */
//    TestingCenterInfo findByTerm(Term term);
//
//    /**
//     * update a field of testing center information specified
//     * by the fieldName argument with the new value. This method should
//     * be able to perform a getInfoByName or setInfoByName operation.value
//     */
//    boolean updateField(Term term, String fieldName, Object value);
//
//    /**
//     * add closeDates to testing center information
//     * @param
//     * @return
//     */
//    boolean addDates(Term term, String fieldName, Object dates);
//
//    /**
//     * delete closeDates in testing center information
//     * @return
//     */
//    boolean deleteCloseDates(int i);
//    /**
//     * add reserveDateTimes to testing center information
//     * @param reserveDateTimes
//     * @return
//     */
//    boolean addReserveDateTimes(LocalDateTime[] reserveDateTimes);
//
//    /**
//     * delete reserveDateTimes in testing center information
//     * @return
//     */
//    boolean deleteReserveDateTimes();
//}