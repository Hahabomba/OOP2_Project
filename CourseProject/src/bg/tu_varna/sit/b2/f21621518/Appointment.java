package bg.tu_varna.sit.b2.f21621518;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Appointment
{
    //LocalDate saves dates in format -> yyyy-MM-dd
    private LocalDate date;

    //Represents time with a default format of hour-minute-second
    private LocalTime startTime;
    private LocalTime endTime;

    //Name of appointment
    private String name;
    //Some note about the appointment
    private String note;

    private boolean isInHolidayDay;

    public Appointment(LocalDate date,LocalTime startTime,LocalTime endTime,String name,String note)
    {
        this.date=date;
        this.startTime=startTime;
        this.endTime=endTime;
        this.name=name;
        this.note=note;
        this.isInHolidayDay=false;
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

    public boolean isInHolidayDay() {
        return this.isInHolidayDay;
    }

    public void setInHolidayDay(boolean inHolidayDay) {
        isInHolidayDay = inHolidayDay;
    }

    public String toString() {
        return "Appointment ->" +
                "Date:" + date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)) +
                ", Start Time:" + startTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) +
                ", End Time:" + endTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) +
                ", Name:'" + name + '\'' +
                ", Note:'" + note + '\'' ;
    }
}
