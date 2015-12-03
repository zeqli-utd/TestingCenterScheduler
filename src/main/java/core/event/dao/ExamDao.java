package core.event.dao;

import core.event.Exam;

import java.util.List;

public interface ExamDao {
    List getAllPending();

    List getAllApproved();

    List getAllDenied();

    List<Exam> getAllExams();

    Exam findByExamId(String examId);

    List<Exam> findByInstructorId(String instructorId);

    boolean addExam(Exam exam);

    boolean insertExam(Exam exam);

    boolean deleteExam(String examId);

    void listExamByApprovedRequest(String exId);

    boolean updateExam(Exam exam);

    List<Exam> getAllAvailableExamsToStudent(String studentId);
}
