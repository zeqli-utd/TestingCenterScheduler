package core.event.dao;

import core.event.AdhocExam;

import java.util.List;

/**
 * Created by Zeqli on 11/26/2015.
 */
public class AdhocExamDaoImp implements AdhocExamDao{
    @Override
    public List<AdhocExam> getAllAdhocExams() {
        return null;
    }

    @Override
    public AdhocExam findByExamId(String examId) {
        return null;
    }

    @Override
    public List<AdhocExam> findByInstructorId(String instructorId) {
        return null;
    }

    @Override
    public boolean addExam(AdhocExam exam) {
        return false;
    }

    @Override
    public boolean updateExam(AdhocExam exam, String id) {
        return false;
    }

    @Override
    public boolean deleteExam(String examId) {
        return false;
    }
}
