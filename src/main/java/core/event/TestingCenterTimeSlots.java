package core.event;

import core.event.dao.AppointmentDao;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;

import core.event.Seat;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TestingCenterTimeSlots {

    @Id
    @Column(name = "timeSlotId")
    // format： examId+"_"+startDateTime.
    // For example: startTime
    private String timeSlotId;

    @Basic(optional = false)
    @Column(name = "examId")
    private String examId;

    @Basic(optional = false)
    @Column(name = "begin")
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime begin;

    @Basic(optional = false)
    @Column(name = "end")
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime end;



    //occupied: appointmentId; nonoccupied: ""
    @OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "slots_seat",
            joinColumns = @JoinColumn(name = "slotid"),
            inverseJoinColumns = @JoinColumn(name = "seatid")
    )
    private List<Seat> seatArrangement = new ArrayList<>();

    @Basic(optional = false)
    @Column(name = "numSeat")
    private int numSeat;

    @Basic(optional = false)
    @Column(name = "setAsideSeat")
    private int setAsideSeat;

    @Basic(optional = false)
    @Column(name = "occupiedNum")
    private int occupiedNum;

    @Transient
    @Autowired
    private AppointmentDao apptDao;

    public TestingCenterTimeSlots() {

    }

    /**
     *
     * @param examId
     * @param begin
     * @param end
     * @param numSeats
     * @param setAsideSeat
     */
    public TestingCenterTimeSlots(String examId,
                                  LocalDateTime begin,
                                  LocalDateTime end,
                                  int numSeats,
                                  int setAsideSeat) {
        this.timeSlotId = Integer.toString(begin.getDayOfYear()) +
                Integer.toString(begin.getHour()) + Integer.toString(begin.getMinute());

        this.examId = examId;
        this.begin = begin;
        this.end = end;
        this.numSeat = numSeats;
        this.seatArrangement = initSeatArrangement(numSeats);
        this.setAsideSeat = setAsideSeat;
        this.occupiedNum = 0;
    }

    public List<Seat> initSeatArrangement(int numSeats) {
        List<Seat> seatsArrangement = new ArrayList<>(numSeats);
        for (int i = 0; i < numSeats; i++) {
            Seat seat = new Seat();
            seat.setTimeSlot(this);
            seatsArrangement.add(seat);
        }
        return seatsArrangement;
    }

    public boolean checkSeatAvailable() {
        if (occupiedNum + 1 > numSeat)
            return false;
        else {
            return true;
        }
    }

    /**
     * Assign an seat automatically
     *
     * @param appt
     * @return
     */
    public boolean assignSeat(Appointment appt) {
        int apptId = appt.getAppointmentID();
        occupiedNum += 1;
        int seat = -1;
        for (int i = 0; i < numSeat; i++) {
            if (seatArrangement.get(i).getAssignId() == 0) {
                Seat seat1 = seatArrangement.get(i);
                seat1.setAssignId(apptId);
                seat = i + 1;
                break;
            }
        }
        if (seat == -1) {
            return false; // There is no more seat
        } else {
            appt.setSlotId(this.timeSlotId);
            appt.setStartDateTime(begin);
            appt.setEndDateTime(end);
            appt.setSeat(Integer.toString(seat));
            return true;
        }
    }

    /**
     * need to be used when remove an appointment
     */
    public void releaseSeat(Appointment appt) {
        int apptId = appt.getAppointmentID();
        int seatNum = Integer.parseInt(appt.getSeat()) - 1;
        if (apptId == seatArrangement.get(seatNum).getAssignId()) {
            seatArrangement.get(seatNum).setAssignId(0);
//            seatArrangement.remove(seatNum);
//            seatArrangement.add(seatNum, 0);
            occupiedNum -= 1;
        } else
            System.out.print("Error when releasing a seat.");
    }

    public String getTimeSlotId() {
        return timeSlotId;
    }

    public String getExamId() {
        return examId;
    }

    public LocalDateTime getBegin() {
        return begin;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setTimeSlotId(String timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public void setBegin(LocalDateTime begin) {
        this.begin = begin;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public List<Seat> getSeatArrangement() {
        return seatArrangement;
    }

    public void setSeatArrangement(List<Seat> seatArrangement) {
        this.seatArrangement = seatArrangement;
    }

    public void setNumSeat(int numSeat) {
        this.numSeat = numSeat;
    }

    public void setSetAsideSeat(int setAsideSeat) {
        this.setAsideSeat = setAsideSeat;
    }

    public void setOccupiedNum(int occupiedNum) {
        this.occupiedNum = occupiedNum;
    }

    public AppointmentDao getApptDao() {
        return apptDao;
    }

    public void setApptDao(AppointmentDao apptDao) {
        this.apptDao = apptDao;
    }

    public int getNumSeat() {
        return numSeat;
    }

    public int getSetAsideSeat() {
        return setAsideSeat;
    }

    public int getOccupiedNum() {
        return occupiedNum;
    }
}