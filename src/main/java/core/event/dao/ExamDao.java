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

    boolean updateExam(Exam exam, String id);

    boolean deleteExam(String examId);

    void listExamByApprovedRequest(String exId);

    boolean approveExam(String examId);

    List<Exam> getAllAvailableExamsToStudent(String studentId);
}
