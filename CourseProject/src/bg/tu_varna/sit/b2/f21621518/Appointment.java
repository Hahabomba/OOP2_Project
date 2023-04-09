package bg.tu_varna.sit.b2.f21621518;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment
{
    //LocalDate saves dates in format -> yyyy-MM-dd
    private LocalDate date;

    //Represents time with a default format of hour-minute-second
    private LocalTime startTime;
    private LocalTime endTime;

    private String name;
    private String note;

    public Appointment(LocalDate date,LocalTime startTime,LocalTime endTime,String name,String note)
    {
        this.date=date;
        this.startTime=startTime;
        this.endTime=endTime;
        this.name=name;
        this.note=note;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
