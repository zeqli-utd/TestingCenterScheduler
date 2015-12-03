package core.event;

import core.event.dao.AppointmentDao;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TestingCenterTimeSlots {

    @Id
    // format： examId+"_"+startDateTime.
    // For example: startTime
    private String timeSlotId;

    @Basic(optional = false)
    private String examId;

    @Basic(optional = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime begin;

    @Basic(optional = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime end;


    //occupied: appointmentId; nonoccupied: ""
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "slots_seat",
            joinColumns = @JoinColumn(name = "slotid"),
            inverseJoinColumns = @JoinColumn(name = "seatid")
    )
    private List<Seat> seatArrangement = new ArrayList<>();

    @Basic(optional = false)
    private int numSeat;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "slots_setaside_seat",
            joinColumns = @JoinColumn(name = "slotid"),
            inverseJoinColumns = @JoinColumn(name = "seatid")
    )
    private List<Seat> seatAsideSeatArrangement = new ArrayList<>();

    @Basic(optional = false)
    private int numSetAsideSeat;

    @Basic(optional = false)
    private int occupiedNum;

    @Transient
    @Autowired
    private AppointmentDao apptDao;

    public TestingCenterTimeSlots() {

    }

    /**
     * @param examId
     * @param begin
     * @param end
     * @param numSeats
     * @param numsetAsideSeat
     */
    public TestingCenterTimeSlots(String examId,
                                  LocalDateTime begin,
                                  LocalDateTime end,
                                  int numSeats,
                                  int numsetAsideSeat) {
        this.timeSlotId = Integer.toString(begin.getDayOfYear()) +
                Integer.toString(begin.getHour()) + Integer.toString(begin.getMinute());
        this.examId = examId;
        this.begin = begin;
        this.end = end;
        this.numSeat = numSeats;
        this.seatArrangement = initSeatArrangement(numSeats);
        this.seatAsideSeatArrangement = initSetAsideeSeatArrangement(numsetAsideSeat);
        this.numSetAsideSeat = numsetAsideSeat;
        this.occupiedNum = 0;
    }

    /**
     * Init Seat Arrangement
     *
     * @param numSeats
     * @return
     */
    public List<Seat> initSeatArrangement(int numSeats) {
        List<Seat> seatsArrangement = new ArrayList<>(numSeats);
        for (int i = 0; i < numSeats; i++) {
            Seat seat = new Seat();
            seat.setTimeSlot(this);
            seatsArrangement.add(seat);
        }
        return seatsArrangement;
    }

    /**
     * Init Set Aside Seat Arrangement
     *
     * @param numSetAside
     * @return
     */
    public List<Seat> initSetAsideeSeatArrangement(int numSetAside) {
        List<Seat> setAsideSeatArrangement = new ArrayList<>(numSetAside);
        for (int i = 0; i < numSetAside; i++) {
            Seat seat = new Seat();
            seat.setTimeSlot(this);
            setAsideSeatArrangement.add(seat);
        }
        return setAsideSeatArrangement;
    }

    /**
     * Check if regular seat available
     * No longer use
     *
     * @return
     */
    @Deprecated
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
        boolean isSuccess = false;
        int seat = -1;
        for (int i = 0; i < numSeat; i = i + 2) {       // Jump Non-adjacent Seat
            if (seatArrangement.get(i).getAssignId() == 0) {
                Seat seat1 = seatArrangement.get(i);
                seat1.setAssignId(apptId);
                seat = i + 1;
                break;
            }
        }
        if (seat == -1) {
            isSuccess = false; // There is no more seat
        } else {
            appt.setSlotId(this.timeSlotId);
            appt.setStartDateTime(begin);
            appt.setEndDateTime(end);
            appt.setSeat(Integer.toString(seat));
            isSuccess = true;
        }

        if(isSuccess){
            occupiedNum += 1;
        }
        return isSuccess;
    }

    /**
     * Assign an set aside seat automatically
     *
     * @param appt
     * @return
     */
    public boolean assignSetAsideSeat(Appointment appt) {
        int apptId = appt.getAppointmentID();
        boolean isSuccess = false;
        int base = numSeat + 1;
        int seat = -1;
        for (int i = 0; i < numSetAsideSeat; i = i+2) {
            if (seatAsideSeatArrangement.get(i).getAssignId() == 0) {
                Seat seat1 = seatAsideSeatArrangement.get(i);
                seat1.setAssignId(apptId);
                seat = i + 1;
                break;
            }
        }
        if (seat == -1) {
            isSuccess = false; // There is no more seat
        } else {
            appt.setSlotId(this.timeSlotId);
            appt.setStartDateTime(begin);
            appt.setEndDateTime(end);
            appt.setSeat(Integer.toString(seat+base));
            isSuccess =  true;
        }
        if(isSuccess){
            occupiedNum += 1;
        }
        return isSuccess;
    }



    /**
     * need to be used when remove an appointment
     */
    public boolean releaseSeat(Appointment appt) {
        // 1. Get Seat Number
        int apptId = appt.getAppointmentID();
        int seatNum = Integer.parseInt(appt.getSeat()) - 1;
        boolean isSetAside = appt.isSetAside();
        int base = numSeat + 1;

        // 2. Iterate through all slots
        if (!isSetAside) {
            if (apptId == seatArrangement.get(seatNum).getAssignId()) {
                seatArrangement.get(seatNum).setAssignId(0);        // Release Seat
//            seatArrangement.remove(seatNum);
//            seatArrangement.add(seatNum, 0);
                occupiedNum -= 1;
                return true;
            } else {
                return false;
            }
        } else {
            if (apptId == seatAsideSeatArrangement.get(seatNum+base).getAssignId()) {
                seatAsideSeatArrangement.get(seatNum+base).setAssignId(0);       // Release Seat
//            seatArrangement.remove(seatNum);
//            seatArrangement.add(seatNum, 0);
                occupiedNum -= 1;
                return true;
            } else {
                return false;
            }

        }

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

    public void setNumSetAsideSeat(int setAsideSeat) {
        this.numSetAsideSeat = setAsideSeat;
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

    public int getNumSetAsideSeat() {
        return numSetAsideSeat;
    }

    public int getOccupiedNum() {
        return occupiedNum;
    }
}