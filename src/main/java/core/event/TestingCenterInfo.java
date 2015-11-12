package core.event;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import java.io.IOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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

}