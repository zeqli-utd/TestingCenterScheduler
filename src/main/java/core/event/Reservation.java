package core.event;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name="Reservation")
public class Reservation {
    @Id
    @Column(name = "reservation_id")
    private String reservationID;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="start_date_time")
    @Basic(optional = false)
    private Date startDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="end_date_time")
    @Basic(optional = false)
    private Date endDateTime;

    @Column(name="instructor_id")
    @Basic(optional = false)
    private String instructorId;

    //haven't initiated
    @Basic(optional = false)
    @Column(name="status")
    private String status;

    @Basic(optional = false)
    @Column(name="terms")
    private String terms;

    @Basic(optional = false)
    @Column(name="types")
    private String types;

    public Reservation(){}

    /**
     * This constructor is for testing
     * @param startTime Start Time
     * @param endTime End Time
     * @param insId Instructor Id
     * @param terms Term
     */
    public Reservation(
            String id,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String insId,
            String terms) {
        this.reservationID = id;
        this.setStartDateTime(startTime);
        this.setEndDateTime(endTime);
        instructorId = insId;
        this.terms = terms;
        this.status = "Pending";
        this.types = "Course";
    }

    public String getType() {
        return types;
    }

    public void setType(String type) {
        this.types = type;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public LocalDateTime getStartDateTime() {
        Date ts = startDateTime;
        Instant instant = Instant.ofEpochMilli(ts.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        Instant instant = startDateTime.atZone(ZoneId.systemDefault()).toInstant();
        this.startDateTime = Date.from(instant);
    }

    public LocalDateTime getEndDateTime() {
        Date ts = endDateTime;
        Instant instant = Instant.ofEpochMilli(ts.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        Instant instant = endDateTime.atZone(ZoneId.systemDefault()).toInstant();
        this.endDateTime = Date.from(instant);
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    // Extend functionality
    public LocalDate getStartDate(){
        Instant instant = Instant.ofEpochMilli(startDateTime.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }

    public LocalDate getEndDate(){
        Instant instant = Instant.ofEpochMilli(endDateTime.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }
}
