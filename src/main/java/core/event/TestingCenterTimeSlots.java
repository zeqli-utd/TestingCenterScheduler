package core.event;

import core.event.dao.AppointmentDaoImp;
import core.service.TestingCenterInfoRetrieval;
import org.springframework.beans.factory.annotation.Autowired;

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
    private AppointmentDaoImp apptImp;

    public TestingCenterTimeSlots(){

    }

    public TestingCenterTimeSlots(String examId, LocalDateTime begin, LocalDateTime end,
                                  int numSeats, int setAsideSeat){
        this.timeSlotId = Integer.toString(begin.getDayOfYear()) +
                Integer.toString(begin.getHour()) + Integer.toString(begin.getMinute());
        this.examId = examId;
        this.begin = begin;
        this.end = end;
        this.numSeat = (numSeats-1)/setAsideSeat+1;
        this.seatArrangement = initSeatArrangement(numSeats);
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

    public  boolean checkSeatAvailable(){
        if(occupiedNum + 1 > numSeat)
            return false;
        else {
            return true;
        }
    }

    public boolean assignSeat(Appointment appt){
        String apptId = appt.getAppointmentID();
        occupiedNum += 1;
        int seat = -1;
        for(int i = 0; i < numSeat; i++){
            if(seatArrangement[i].equals("")){
                seatArrangement[i] = apptId;
                seat = i + 1;
            }
        }
        if(seat == -1)
            return false;
        appt.setStartDateTime(begin);
        appt.setEndDateTime(end);
        appt.setSeat(Integer.toString(seat));
        apptImp.updateAppointment(appt, appt.getAppointmentID());
        return true;
    }

    //TODO need to be used when remove an appointment
    public void releaseSeat(Appointment appt){
        String apptId = appt.getAppointmentID();
        int seatNum = Integer.parseInt(appt.getSeat()) - 1;
        if(apptId.equals(seatArrangement[seatNum])) {
            seatArrangement[seatNum] = "";
            occupiedNum -= 1;
        }
        else
            System.out.print("Error when releasing a seat.");
    }



}