package core.helper;

import java.util.Map;

public class IdGenerator {
    public String generateReservationId(Map<String, Object> reservationDetails) {
        return (String) reservationDetails.get("instructor_id") +
                    reservationDetails.get("term") +
                        (reservationDetails.get("start_date_time")).toString();
    }
}
