package bg.tu_varna.sit.b2.f21621518;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Application
{
    public static void main(String[] args)
    {
        //Creating instance of personalCalendar
        PersonalCalendar personalCalendar=new PersonalCalendar();

        //Pattern Example 08.01.2020
        String europeanDatePattern = "dd.MM.yyyy";

        //First Date 08.01.2020
        LocalDate date = LocalDate.parse("08.01.2020", DateTimeFormatter.ofPattern(europeanDatePattern));

        //Second Date 09.01.2020
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


        //Fifth appointment (For New Date)
        LocalTime fifthStartTime=LocalTime.of(14,30,00);
        LocalTime fifthEndTime=LocalTime.of(17,00,00);
        Appointment fifthAppointment=new Appointment(secondDate,fifthStartTime,fifthEndTime,"Clean the house!","Prepare everything for cleaning!");

        //Booking appointments
        personalCalendar.book(firstAppointment);
        personalCalendar.book(secondAppointment);
        personalCalendar.book(thirdAppointment);
        personalCalendar.book(fourthAppointment);
        personalCalendar.book(fifthAppointment);

        System.out.println(personalCalendar);

        //Remove (WORKS Correctly)
        //Removing -> Date: 08.01.2020 StartTime:10.30.00 EndTime:11.00.00
        LocalDate firstDateToRemove=LocalDate.parse("08.01.2020",DateTimeFormatter.ofPattern(europeanDatePattern));
        personalCalendar.unbook(firstDateToRemove,startTime,endTime);
        System.out.println();

        //Agenda (by date)  (WORKS)
        personalCalendar.agenda(date);
        System.out.println();
        personalCalendar.agenda(secondDate);
        System.out.println();

        LocalDate dateNotContained = LocalDate.parse("11.01.2020", DateTimeFormatter.ofPattern(europeanDatePattern));
        personalCalendar.agenda(dateNotContained);
        System.out.println();

        //Change (WORKS Correctly)
        // date - 08.01.2020 ; thirdStartTime: 9:15;
        personalCalendar.change(date,thirdStartTime,"date","10.01.2020");

        LocalDate newlyChangedDate = LocalDate.parse("10.01.2020", DateTimeFormatter.ofPattern(europeanDatePattern));

        //Format of newStartTime "11:15:00"
        //First appointment from 10.01.2020 (Start Time: 9:15)
        // 10.01.2020  (9:15(old)   -> 11:15:00(new))
        personalCalendar.change(newlyChangedDate,thirdStartTime,"starttime","11:15:00");
        LocalTime newStartTime=LocalTime.of(11,15,00);

        personalCalendar.change(newlyChangedDate,newStartTime,"endtime","14:45:00");

        System.out.println(personalCalendar);

        //Find (WORKS)
        personalCalendar.find("bank");
        personalCalendar.find("car");

        System.out.println();

       //BusyDays (WORKS)
       personalCalendar.busyDays(date,secondDate);
       System.out.println();

        //FindSlot
        personalCalendar.findSlot(secondDate,2);
    }
}
