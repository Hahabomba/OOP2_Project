package bg.tu_varna.sit.b2.f21621518;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class PersonalCalendar implements Calendar
{
    private Map<String, List<Appointment>> appointmentsByDate;
    private List<Appointment> appointments;

    public PersonalCalendar()
    {
        this.appointmentsByDate=new HashMap<>();
        this.appointments=new ArrayList<>();
    }

    @Override
    public void book(Appointment appointmentToBook)
    {
        LocalDate appointmentToBookDate=appointmentToBook.getDate();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");

        //Text -> Key
        String text = appointmentToBookDate.format(formatter);

        appointments.add(appointmentToBook);
        //Date is not cointaned yet
        if (!(this.appointmentsByDate.containsKey(text)))
        {
            //text -> date in appropriate format
            this.appointmentsByDate.put(text,new ArrayList<>());
            appointmentsByDate.get(text).add(appointmentToBook);
        }
        //Date is already contained -> add appointments
        else
        {
           this.appointmentsByDate.get(text).add(appointmentToBook);
        }
    }

    @Override
    public void unbook(LocalDate date, LocalTime startTime, LocalTime endTime)
    {
        LocalDate appointmentToRemoveDate=date;
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String dateAsText=appointmentToRemoveDate.format(formatter);

        System.out.println(dateAsText);

        boolean appointmentToRemoveFound=false;

        Appointment appointmentToRemove=null;

        for(Appointment appointment: appointments)
        {
            boolean datesAreEqual=appointment.getDate().equals(date);
            boolean startTimesAreEqual=appointment.getStartTime().equals(startTime);
            boolean endTimesAreEqual=appointment.getEndTime().equals(endTime);

            if (datesAreEqual && startTimesAreEqual && endTimesAreEqual)
            {
                appointmentToRemoveFound=true;
                appointmentToRemove=appointment;
            }
        }

        if (appointmentToRemoveFound)
        {
            this.appointments.remove(appointmentToRemove);
            System.out.println("Successfully unbooked appointment with date: "+dateAsText+" ,start time: "+startTime+" ,end time: "+endTime);
        }
        else
        {
            System.out.println("Invalid appointment! No such appointment exists!");
        }
    }

    @Override
    public void agenda(LocalDate date)
    {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String correctDateFormat=formatter.format(date);

        List<Appointment> currentDateAppointments=this.appointmentsByDate.get(correctDateFormat);
       currentDateAppointments.sort(Comparator.comparing(Appointment::getStartTime));

       System.out.println("Appointmens for date: "+correctDateFormat+ ", Ordered chronologically:");
       for (Appointment currentAppointment: currentDateAppointments)
       {
           System.out.println(currentAppointment);
       }
    }

    @Override
    public void find(String searchString)
    {
        //Search for appointment : Print info about appointments whose name or note contains string

        for(Appointment currentAppointment: this.appointments)
        {
            boolean isContainedInName=currentAppointment.getName().contains(searchString);
            boolean isContainedInNote=currentAppointment.getNote().contains(searchString);

            if (isContainedInName || isContainedInNote)
            {
                System.out.println(currentAppointment);
            }

        }
    }

    @Override
    public String toString() {
        return "Personal Calendar:\n" +
                "Appointments By Date:" + appointmentsByDate +
                '}';
    }
}
