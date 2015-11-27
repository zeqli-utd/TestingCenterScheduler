package core.helper;

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

}
