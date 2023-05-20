package bg.tu_varna.sit.b2.f21621518;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonalCalendar implements Calendar
{
    // MAP ( Date(String)  -> List<Appointment>)
    private Map<String, List<Appointment>> appointmentsByDate;
    //Inner list of apppointments
    private List<Appointment> appointments;

    private Map<String, Double> busyHoursByDay;

    private DateFormatter dateFormatter;


    public PersonalCalendar()
    {
        this.appointmentsByDate=new HashMap<>();
        this.appointments=new ArrayList<>();
        this.busyHoursByDay=new HashMap<>();
        this.dateFormatter=new DateFormatter();
    }

    @Override
    public void book(Appointment appointmentToBook)
    {
        LocalDate appointmentToBookDate=appointmentToBook.getDate();
        //Text -> Key
        String text = dateFormatter.formatDate(appointmentToBookDate);

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
        String dateAsText=dateFormatter.formatDate(date);

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
            //Remove appointment from inner list of appointments
            this.appointments.remove(appointmentToRemove);
            //Remove appointment from selected Date (IN MAP)
            this.appointmentsByDate.get(dateAsText).remove(appointmentToRemove);
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
        String correctDateFormat=dateFormatter.formatDate(date);

        if (this.appointmentsByDate.containsKey(correctDateFormat))
        {

            List<Appointment> currentDateAppointments = this.appointmentsByDate.get(correctDateFormat);

            currentDateAppointments.sort(Comparator.comparing(Appointment::getStartTime));

            System.out.println("Appointmens for date: " + correctDateFormat + ", Ordered chronologically:");
            for (Appointment currentAppointment : currentDateAppointments) {
                System.out.println(currentAppointment);
            }
        }
        else
        {
         System.out.println("Invalid date - "+correctDateFormat+ " No such date is contained yet");
        }
    }

    //To Do implement Find First
    public Appointment findFirst(LocalDate date,LocalTime startTime,LocalTime endTime)
    {
        for (int i=0;i<this.appointments.size();i++)
        {
            Appointment currentAppointment=this.appointments.get(i);
            if (currentAppointment.getDate().equals(date))
            {

            }
        }

        return null;
    }
    @Override
    public void change(LocalDate date, LocalTime startTime, String option, String newValue)
    {
        Appointment appointmentToChange=null;

        String dateFormatted= dateFormatter.formatDate(date);

        //Date is contained in map
        if (this.appointmentsByDate.containsKey(dateFormatted))
        {
            //Get appointments for date -> (dateFormatted)
            List<Appointment> appointmentsList=this.appointmentsByDate.get(dateFormatted);

            //Iterate through appointments
            for (Appointment currentAppointment: appointmentsList)
            {
                //Check if current appointment date and start time are equal to given parameters
                boolean dateIsEqual=currentAppointment.getDate().equals(date);
                boolean startTimeIsEqual=currentAppointment.getStartTime().equals(startTime);

                //Appointment was found
                if (dateIsEqual && startTimeIsEqual)
                {
                    appointmentToChange=currentAppointment;
                    break;
                }
            }

            //Appointment was not found
            if (appointmentToChange==null)
            {
                System.out.println("Appointment was not found!");
                return;
            }

            String europeanDatePattern="dd.MM.yyyy";

            //Handling different options
            switch (option)
            {

                case "date":
                    LocalDate newDate=LocalDate.parse(newValue,DateTimeFormatter.ofPattern(europeanDatePattern));
                    appointmentToChange.setDate(newDate);
                    break;

                case "starttime":
                    LocalTime newStartTime=LocalTime.parse(newValue,DateTimeFormatter.ofPattern(europeanDatePattern));
                    appointmentToChange.setStartTime(newStartTime);
                    break;
                case "endtime":
                    LocalTime newEndTime=LocalTime.parse(newValue,DateTimeFormatter.ofPattern(europeanDatePattern));
                    appointmentToChange.setEndTime(newEndTime);
                    break;

                case "name":
                    appointmentToChange.setName(newValue);
                    break;
                case "note":
                    appointmentToChange.setNote(newValue);
                    break;

                default:
                    System.out.println("Invalid option entered!");
                    return;
            }

            System.out.println("Successfully updated appointment!");

        }
        //Date selected is not contained
        else
        {
            System.out.println("Date you entered is not present in calendar!");
        }

    }

    @Override
    public void find(String searchString)
    {
        //Search for appointment : Print info about appointments whose name or note contains string
        for(Appointment currentAppointment: this.appointments)
        {
            String wordBoundaryPattern = "\\b";
            String escapedSearchString = Pattern.quote(searchString);
            String regexPattern = wordBoundaryPattern + escapedSearchString + wordBoundaryPattern;

            //Search String is contained in appointment name
            boolean isContainedInName = currentAppointment.getName().matches(".*" + regexPattern + ".*");
            //Search String is contained in appointment note
            boolean isContainedInNote = currentAppointment.getNote().matches(".*" + regexPattern + ".*");

            if (isContainedInName || isContainedInNote)
            {
                System.out.println("Appointment containing "+searchString+":");
                System.out.println(currentAppointment);
            }
        }
    }

    @Override
    public void holiday(LocalDate date)
    {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String correctDateFormat=formatter.format(date);

        //Date is contained
        if (this.appointmentsByDate.containsKey(correctDateFormat))
        {
            List<Appointment> appointmentList=appointmentsByDate.get(correctDateFormat);
            for(Appointment currentAppointment: appointmentList)
            {
                currentAppointment.setInHolidayDay(true);
            }
        }
        else
        {
            System.out.println("No such date exists!");
        }
    }

    @Override
    public void busyDays(LocalDate beginDate, LocalDate endDate)
    {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String correctBeginDate=dateFormatter.formatDate(beginDate);
        String correctEndDate=dateFormatter.formatDate(endDate);

        boolean beginDateIsContained=this.appointmentsByDate.containsKey(correctBeginDate);
        boolean endDateIsContained=this.appointmentsByDate.containsKey(correctEndDate);

        for (String currentDateString: this.appointmentsByDate.keySet())
        {
            LocalDate convertedDay=LocalDate.parse(currentDateString,formatter);

            this.busyHoursByDay.put(convertedDay.getDayOfWeek().toString(),0.0);
        }

        if (beginDateIsContained && endDateIsContained)
        {
            for (LocalDate currentDate=beginDate;currentDate.isBefore(endDate.plusDays(1));currentDate=currentDate.plusDays(1))
            {
                String correctFormatCurrDate=formatter.format(currentDate);

                List<Appointment> appointmentsList=this.appointmentsByDate.get(correctFormatCurrDate);

                for (Appointment appointment: appointmentsList)
                {
                    String dayOfWeek=currentDate.getDayOfWeek().toString();

                    double currentBusyHours=busyHoursByDay.get(dayOfWeek);

                    double appointmentDurationHours=appointment.getEndTime().getHour()-appointment.getStartTime().getHour();
                    busyHoursByDay.put(dayOfWeek,currentBusyHours+(appointmentDurationHours));
                }
            }

            List<Map.Entry<String, Double>> sortedBusyDays = new ArrayList<>(busyHoursByDay.entrySet());
            sortedBusyDays.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            System.out.println("Busy Days Statistics for -> "+"Begin Date: "+correctBeginDate+" End Date: "+correctEndDate);
            for (Map.Entry<String, Double> entry : sortedBusyDays)
            {
                String dayOfWeek = entry.getKey();
                double busyHours = entry.getValue();
                System.out.println(dayOfWeek + ": " + busyHours + " busy hours");
            }
        }
        else
        {
            System.out.println("Invalid time span! No such begin or end date is contained !");
        }

    }

    //TO DO: implement findSlot
    @Override
    public void findSlot(LocalDate fromDate, double hoursDuration)
    {
        LocalTime appropriateStartTime=LocalTime.of(8,0);
        LocalTime appropriateEndTime=LocalTime.of(17,0);

        LocalDate currentDate=fromDate;

        while (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek()==DayOfWeek.SUNDAY)
        {

        }
    }

    @Override
    public String toString() {
        return "Personal Calendar:\n" +
                "Appointments By Date:" + appointmentsByDate +
                '}';
    }
}
