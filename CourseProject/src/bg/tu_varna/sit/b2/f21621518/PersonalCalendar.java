package bg.tu_varna.sit.b2.f21621518;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        //Date is not cointaned yet
        if (!(this.appointmentsByDate.containsKey(text)))
        {
            //text -> date in appropriate format
            this.appointments.add(appointmentToBook);
            this.appointmentsByDate.put(text,this.appointments);
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

        boolean appointmentsContainsStartTime=this.appointmentsByDate.values().contains(startTime);
        boolean appointmentsContainsEndTime=this.appointmentsByDate.values().contains(endTime);

        if (this.appointmentsByDate.containsKey(dateAsText) && appointmentsContainsStartTime && appointmentsContainsEndTime)
        {

        }
        else
        {
            System.out.println("Invalid Date! Does not exist!");
        }
    }

    @Override
    public String toString() {
        return "Personal Calendar:\n" +
                "Appointments By Date:" + appointmentsByDate +
                '}';
    }
}
