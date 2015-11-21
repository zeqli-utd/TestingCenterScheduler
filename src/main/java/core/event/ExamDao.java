package core.event;

import java.util.List;

public interface ExamDao {
    public List<Exam> getAllExams();

    public Exam findByExamId(String examId);

    public List<Exam> findByInstructorId(String instructorId);

    public boolean addExam(Exam exam);

    public boolean updateExam(Exam exam, String id);

    public boolean deleteExam(String examId);

    public void listExamByApprovedRequest(String exId);

}
