package core.service;

import java.time.LocalDateTime;
import java.util.Map;

public class IdGenerator {
    public String generateReservationId(Map<String, Object> reservationDetails) {
        return (String)reservationDetails.get("instructor_id") +
                (String) reservationDetails.get("term") +
                ((LocalDateTime) reservationDetails.get("start_date_time")).toString();
    }
}
