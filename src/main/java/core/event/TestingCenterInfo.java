package core.event;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class TestingCenterInfo {

    static final private String PATH = "./doc/TestingCenterInfo.json";
    private int numSeats;
    private int numSetAsideSeats;
    private LocalTime open;
    private LocalTime close;
    private List<LocalDate[]> closeDateRanges;
    private List<LocalDateTime[]> reserveRanges;
    private int gap;
    private int reminderInterval;

    public TestingCenterInfo() {

    }

    public static TestingCenterInfo deserialize() {
        try {
            String json = new String(Files.readAllBytes(Paths.get(PATH)));
            Gson gson = new Gson();
            TestingCenterInfo info = gson.fromJson(json, TestingCenterInfo.class);
            return info;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //public void serialize() {
    //    GsonBuilder gsonBuilder = new GsonBuilder();
    //    Gson gson = gsonBuilder.create();
    //    String json = gson.toJson(this);
    //    System.out.println(json);
    //
    //    try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PATH)))) {
    //        writer.write(json);
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    }
    //}

    public boolean update(TestingCenterInfo info) {
        if (this != null) {
            return false;
        } else {
            this.setNumSeats(info.getNumSeats());
            this.setNumSetAsideSeats(info.getNumSetAsideSeats());
            this.setOpen(info.getOpen());
            this.setClose(info.getClose());
            this.setCloseDateRanges(info.getCloseDateRanges());
            this.setReserveRanges(info.getReserveRanges());
            this.setGap(info.getGap());
            this.setReminderInterval(info.getReminderInterval());
            return true;
        }
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
