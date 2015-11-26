package core.event.dao;

import core.event.AdhocExam;
import core.event.Exam;

import java.util.List;

public interface AdhocExamDao {
     List<AdhocExam> getAllAdhocExams();

     AdhocExam findByExamId(String examId);

     List<AdhocExam> findByInstructorId(String instructorId);

     boolean addExam(AdhocExam exam);

     boolean updateExam(AdhocExam exam, String id);

     boolean deleteExam(String examId);

}
