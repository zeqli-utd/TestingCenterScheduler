package core.helper;

import core.event.Course;
import core.event.dao.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IdGenerator {

    @Autowired
    CourseDao courseDao;
    public String generateReservationId(Map<String, Object> reservationDetails) {
        return (String) reservationDetails.get("instructor_id") +
                    reservationDetails.get("term") +
                        (reservationDetails.get("start_date_time")).toString();
    }


    public String generateExamId(String classId){
        // 1. Get the Class Identifier for this class. e.g. CSE308-01_1158
        Course course = courseDao.findByCourseId(classId);
        // 2. Check previous Exam in the same term.

        // 3. Append a new number for a new exam.

//        String generatedExamId = "";
//        Session session = sessionManager.getInstance().getOpenSession();
//        Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            Course e = (Course) session.get(Course.class, classId);
//            int examNum = e.getExamNum();
//            examNum++;
//            e.setExamNum(examNum++);
//            generatedExamId = classId + "_ex" + examNum;
//        }catch (HibernateException var9) {
//            if(tx != null) {
//                tx.rollback();
//            }
//        } finally {
//            session.close();
//        }
        return generatedExamId;
    }
}
