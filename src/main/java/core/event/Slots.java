package core.event;

import core.event.dao.ExamDaoImp;
import core.event.dao.TestingCenterTimeSlotsDaoImp;
import core.service.TestingCenterInfoRetrieval;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Slots {

    private String examId;

    private Exam exam;

    private int numRemainingTimes;
    //the "shortest" time needed for all students to take the exam
    private int actualDuration;

    private ArrayList<TestingCenterTimeSlots> timeSlots = new ArrayList<TestingCenterTimeSlots>();

    ArrayList<TestingCenterTimeSlots> tctsList = new ArrayList<TestingCenterTimeSlots>();

    @Autowired
    TestingCenterInfoRetrieval tcir;
    TestingCenterInfo tci = tcir.findByTerm(tcir.getCurrentTerm().getTermId());
    int numSeats = tci.getNumSeats();
    int gap = tci.getGap();
    int numSetAsideSeats = tci.getNumSetAsideSeats();
    LocalTime close = tci.getClose();
    LocalTime open = tci.getOpen();

    public Slots(){}

    public Slots(Exam e){
        this.examId = e.getExamId();
        this.exam = e;
        this.actualDuration = e.getActualDuration();
        this.numRemainingTimes = e.getNumRemainingTime();
    }

    //suppose duration and start/endDateTime are legal
    public ArrayList<TestingCenterTimeSlots> generateTimeSlots(){
        TestingCenterTimeSlotsDaoImp tctsImp  = new TestingCenterTimeSlotsDaoImp();
        tctsImp.findAllTimeSlots();
        LocalDateTime begin = exam.getStartDateTime();
        LocalDateTime end;
        int i = 0;
        if (checkConflict() == false) {
            while(i < numRemainingTimes) {
                end = begin.plusMinutes(exam.getDuration());
                if( (end.getHour() <= close.getHour()) && (end.getMinute() <= close.getMinute()) ){

                }
                else{
                    begin = LocalDateTime.of(begin.plusDays(1).toLocalDate(), open);
                    end = begin.plusMinutes(exam.getDuration());
                }

                if(end.isAfter(exam.getEndDateTime())){
                    return null;
                }
                TestingCenterTimeSlots tcts = new TestingCenterTimeSlots(examId, begin, end,
                    numSeats, numSetAsideSeats);
                begin = end.plusMinutes(gap);
                i++;
            }
        }
        return tctsList;
    }

    public boolean checkConflict(){
        boolean conflict = false;
        ExamDaoImp examImp = new ExamDaoImp();
        List<Exam> allExams;
        allExams = examImp.getAllExams();
        Exam examIter = new Exam();
        ArrayList<Exam> conflictExams = new ArrayList<Exam>();
        for(int i = 0; i < allExams.size(); i++){
            examIter = (Exam)allExams.get(i);
            examIter.getStartDateTime();
            if( ((exam.getStartDateTime().minusMinutes(gap).minusMinutes
                    (examIter.getActualDuration())).isAfter(examIter.
                    getStartDateTime())) || ((exam.getStartDateTime().
                    minusMinutes(gap).minusMinutes(examIter.getActualDuration())).
                    isEqual(examIter.getStartDateTime()))|| (exam.getEndDateTime().
                    isBefore(examIter.getEndDateTime().minusMinutes(gap).
                            minusMinutes(examIter.getActualDuration()))) ||
                    (exam.getEndDateTime().isEqual(examIter.getEndDateTime().
                            minusMinutes(gap).minusMinutes(examIter.getActualDuration()))) ){
                //no conflict
            }
            else{
                conflictExams.add(allExams.get(i));
                conflict = true;
            }
        }
        return conflict;

    }

//    public boolean checkSchedulability(){
//        boolean schedulability = true;
//        ExamDaoImp examImp = new ExamDaoImp();
//        List<Exam> allExams;
//        allExams = examImp.getAllExams();
//        Exam examIter = new Exam();
//        ArrayList<Exam> conflictExams = new ArrayList<Exam>();
//        for(int i = 0; i < allExams.size(); i++){
//            examIter = (Exam)allExams.get(i);
//            examIter.getStartDateTime();
//            if( ((exam.getStartDateTime().minusMinutes(gap).minusMinutes
//                    (examIter.getActualDuration())).isAfter(examIter.
//                    getStartDateTime())) || ((exam.getStartDateTime().
//                    minusMinutes(gap).minusMinutes(examIter.getActualDuration())).
//                    isEqual(examIter.getStartDateTime()))|| (exam.getEndDateTime().
//                    isBefore(examIter.getEndDateTime().minusMinutes(gap).
//                            minusMinutes(examIter.getActualDuration()))) ||
//                    (exam.getEndDateTime().isEqual(examIter.getEndDateTime().
//                            minusMinutes(gap).minusMinutes(examIter.getActualDuration()))) ){
//                //no conflict
//            }
//            else{
//                conflictExams.add(allExams.get(i));
//                schedulability = false;
//            }
//
//            if(schedulability == true){
//                return true;
//            }
//            else{
//                schedulability = checkConflicts(conflictExams);
//            }
//        }
//        return schedulability;
//    }
//
//    public boolean checkConflicts(ArrayList conflictExams){
//        boolean schedulability = true;
//        Exam examIter = new Exam();
//        //int[] minuteStatus;
//        for(int i = 0;  i < conflictExams.size(); i++){
//            examIter = (Exam)conflictExams.get(i);
//            examIter.getStartDateTime();
//            if( ((exam.getStartDateTime().minusMinutes(gap)).isAfter(
//                    examIter.getEndDateTime())) || (exam.getEndDateTime().
//                    isBefore(examIter.getStartDateTime().minusMinutes(gap))) ){
//                        //no conflict
//            }
//            else {
//
//            }
//                conflictExams.add(conflictExams.get(i));
//                schedulability = false;
//        }
//
//
//        return schedulability;
//    }

}
