package bg.tu_varna.sit.b2.f21621518;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Application
{
    public static void main(String[] args)
    {
        PersonalCalendar personalCalendar=new PersonalCalendar();

        String europeanDatePattern = "dd.MM.yyyy";
        //First Date
        LocalDate date = LocalDate.parse("08.01.2020", DateTimeFormatter.ofPattern(europeanDatePattern));

        //Second Date
        LocalDate secondDate=LocalDate.parse("09.01.2020",DateTimeFormatter.ofPattern(europeanDatePattern));

        //First appointment
        LocalTime startTime=LocalTime.of(10,30,00);
        LocalTime endTime=LocalTime.of(11,00,00);
        Appointment firstAppointment=new Appointment(date,startTime,endTime,"Going to the dentist","Do not be late!");

        //Second appointment
        LocalTime secondStartTime=LocalTime.of(13,00,00);
        LocalTime secondEndTime=LocalTime.of(14,00,00);
        Appointment secondAppointment=new Appointment(date,secondStartTime,secondEndTime,"Going to the bank","Do not forget to bring your ID card!");

        //Third appointment
        LocalTime thirdStartTime=LocalTime.of(9,15,00);
        LocalTime thirdEndTime=LocalTime.of(10,00,00);
        Appointment thirdAppointment=new Appointment(date,thirdStartTime,thirdEndTime,"Lectures at university","Bring a pen to write information");

        //Fourth appointment (New Date)
        LocalTime fourthStartTime=LocalTime.of(11,45,00);
        LocalTime fourthEndTime=LocalTime.of(14,30,00);
        Appointment fourthAppointment=new Appointment(secondDate,fourthStartTime,fourthEndTime,"Get the car to a mechanic","Service interval past due!");


        //Booking appointments
        personalCalendar.book(firstAppointment);
        personalCalendar.book(secondAppointment);
        personalCalendar.book(thirdAppointment);
        personalCalendar.book(fourthAppointment);

        System.out.println(personalCalendar);

        //Removing -> Date: 08.01.2020 StartTime:10.30.00 EndTime:11.00.00
        LocalDate firstDateToRemove=LocalDate.parse("08.01.2020",DateTimeFormatter.ofPattern(europeanDatePattern));
        personalCalendar.unbook(firstDateToRemove,startTime,endTime);

        //Agenda (by date)
        personalCalendar.agenda(date);
        System.out.println();
        personalCalendar.agenda(secondDate);
        System.out.println();

        //Find
       personalCalendar.find("bank");
       personalCalendar.find("car");
    }
}
