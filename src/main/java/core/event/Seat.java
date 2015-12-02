package core.event;

import javax.persistence.*;

// This is a helper entity that store seat data for timeslot
@Entity
public class Seat {

    @Id
    @GeneratedValue
    int seatId;

    @ManyToOne
    TestingCenterTimeSlots timeSlot;

    @Column(name = "assignid")
    int assignId;

    public Seat() {
    }


    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public TestingCenterTimeSlots getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TestingCenterTimeSlots timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getAssignId() {
        return assignId;
    }

    public void setAssignId(int assignId) {
        this.assignId = assignId;
    }
}

