package core.event;

import core.event.dao.AppointmentDaoImp;
import core.service.TestingCenterInfoRetrieval;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "timeSlots")
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
    private LocalDateTime begin;

    @Basic(optional = false)
    @Column(name = "end")
    private LocalDateTime end;

    @Basic(optional = false)
    @Column(name = "seat_arrangement")
    //occupied: appointmentId; nonoccupied: ""
    private String[] seatArrangement;

    @Transient
    private int numSeat;

    @Transient
    private int setAsideSeat;

    @Transient
    private int occupiedNum;

    @Transient
    private AppointmentDaoImp apptImp = new AppointmentDaoImp();


    public TestingCenterTimeSlots(){

    }

    public TestingCenterTimeSlots(String examId, LocalDateTime begin, LocalDateTime end,
                                  int numSeats, int setAsideSeat){
        this.timeSlotId = Integer.toString(begin.getDayOfYear()) +
                Integer.toString(begin.getHour()) + Integer.toString(begin.getMinute());
        this.examId = examId;
        this.begin = begin;
        this.end = end;
        this.seatArrangement = initSeatArrangement(numSeats);
        this.numSeat = numSeats;
        this.setAsideSeat = setAsideSeat;
        this.occupiedNum = 0;
    }

    public String[] initSeatArrangement(int numSeats){
        String[] seatsArrangement = new String[numSeats];
        for(int i = 0; i < numSeats; i++){
            seatsArrangement[i] = "";
        }
        return seatsArrangement;
    }

    public boolean assignSeat(Appointment appt){
        String apptId = appt.getAppointmentID();
        if(occupiedNum + setAsideSeat > numSeat)
            occupiedNum += setAsideSeat;
        else {
            System.out.print("No seats available for this exam at this time.");
            return false;
        }
        seatArrangement[occupiedNum-1] = apptId;
        appt.setStartDateTime(begin);
        appt.setEndDateTime(end);
        appt.setSeat(Integer.toString(occupiedNum));
        apptImp.updateAppointment(appt, appt.getAppointmentID());
        return true;
    }

    //TODO need to be used when remove an appointment
    public void releaseSeat(Appointment appt){
        String apptId = appt.getAppointmentID();
        int seatNum = Integer.parseInt(appt.getSeat()) - 1;
        if(apptId.equals(seatArrangement[seatNum]))
            seatArrangement[seatNum] = "";
        else
            System.out.print("Error when release a seat.");
    }


}