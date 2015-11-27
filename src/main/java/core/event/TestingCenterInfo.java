package core.event;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "TestingCenterInfo")
public class TestingCenterInfo {
    @Id
    @Column(name = "term")
    private Term term;

    @Basic(optional = false)
    @Column(name = "numSeats")
    private int numSeats;

    @Basic(optional = false)
    @Column(name = "numSetAsideSeats")
    private int numSetAsideSeats;

    @Basic(optional = false)
    @Column(name = "open")
    private LocalTime open;

    @Basic(optional = false)
    @Column(name = "close")
    private LocalTime close;

    @Basic(optional = false)
    @Column(name = "closeDateRanges")
    private List<LocalDate[]> closeDateRanges;

    @Basic(optional = false)
    @Column(name = "reserveRanges")
    private List<LocalDateTime[]> reserveRanges;

    @Basic(optional = false)
    @Column(name = "gap")
    private int gap;

    @Basic(optional = false)
    @Column(name = "reminderInterval")
    private int reminderInterval;

    public TestingCenterInfo() {

    }

    public TestingCenterInfo(Term term, int numSeats, int numSetAsideSeats, LocalTime open,
                             LocalTime close, List<LocalDate[]> closeDateRanges,
                             List<LocalDateTime[]> reserveRanges, int gap, int reminderInterval) {
        this.term = term;
        this.numSeats = numSeats;
        this.numSetAsideSeats = numSetAsideSeats;
        this.open = open;
        this.close = close;
        this.closeDateRanges = closeDateRanges;
        this.reserveRanges = reserveRanges;
        this.gap = gap;
        this.reminderInterval = reminderInterval;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public int getNumSetAsideSeats() {
        return numSetAsideSeats;
    }

    public void setNumSetAsideSeats(int numSetAsideSeats) {
        this.numSetAsideSeats = numSetAsideSeats;
    }

    public LocalTime getOpen() {
        return open;
    }

    public void setOpen(LocalTime open) {
        this.open = open;
    }

    public LocalTime getClose() {
        return close;
    }

    public void setClose(LocalTime close) {
        this.close = close;
    }

    public List<LocalDate[]> getCloseDateRanges() {
        return closeDateRanges;
    }

    public void setCloseDateRanges(List<LocalDate[]> closeDateRanges) {
        this.closeDateRanges = closeDateRanges;
    }

    public List<LocalDateTime[]> getReserveRanges() {
        return reserveRanges;
    }

    public void setReserveRanges(List<LocalDateTime[]> reserveRanges) {
        this.reserveRanges = reserveRanges;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public int getReminderInterval() {
        return reminderInterval;
    }

    public void setReminderInterval(int reminderInterval) {
        this.reminderInterval = reminderInterval;
    }
}