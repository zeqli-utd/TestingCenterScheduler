package core.event.dao;

import core.event.AdhocExam;
import core.event.Exam;

import java.util.List;

public interface AdhocExamDao {
     List<AdhocExam> getAllAdhocExams();

     AdhocExam findByAdhocExamId(String examId);

     List<AdhocExam> findByInstructorId(String instructorId);

     boolean addAdhocExam(AdhocExam exam);

     boolean updateExam(AdhocExam exam, String id);

     boolean deleteExam(String examId);

}
