package bg.tu_varna.sit.b2.f21621518;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Application
{
    public static void main(String[] args) {
        PersonalCalendar personalCalendar=new PersonalCalendar();

        String europeanDatePattern = "dd.MM.yyyy";
        LocalDate date = LocalDate.parse("08.01.2020", DateTimeFormatter.ofPattern(europeanDatePattern));

        LocalTime startTime=LocalTime.of(10,30,00);
        LocalTime endTime=LocalTime.of(11,00,00);

        Appointment firstAppointment=new Appointment(date,startTime,endTime,"Going to the dentist","Do not be late!");

        personalCalendar.book(firstAppointment);

        System.out.println(personalCalendar);

        LocalDate firstDateToRemove=LocalDate.parse("08.01.2020",DateTimeFormatter.ofPattern(europeanDatePattern));
        personalCalendar.unbook(firstDateToRemove,startTime,endTime);
    }
}
