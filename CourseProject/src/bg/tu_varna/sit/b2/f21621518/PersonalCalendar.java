package bg.tu_varna.sit.b2.f21621518;

import bg.tu_varna.sit.b2.f21621518.CommandParser.CommandParser;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
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

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Appointment> getAppointments() {
        return appointments;
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
            System.out.println("Can not unbook appointment on"+dateAsText+"\nInvalid appointment! No such appointment exists!");
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

    private boolean isOverlapping(Appointment appointment,LocalTime newStartTime, LocalTime newEndTime)
    {
        if (newStartTime.isAfter(appointment.getEndTime()))
        {
            return false;
        }

        if (newEndTime.isBefore(appointment.getStartTime()))
        {
            return false;
        }

        return true;
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
                    String formattedNewDate=DateFormatter.formatDate(newDate);

                    //New Date is not contained -> It is supposed to be available
                    if (!(this.appointmentsByDate.containsKey(formattedNewDate)))
                    {
                        //dateFormatted is the date of appointmentToChange
                        //We remove appointmentToChange from calendar; Set its new date and then place it in the according place in calendar
                        this.appointmentsByDate.get(dateFormatted).remove(appointmentToChange);

                        //Set the new Date of appointment
                        appointmentToChange.setDate(newDate);

                        //Place this appointment in new place in calendar
                        this.appointmentsByDate.put(formattedNewDate,new ArrayList<>());
                        this.appointmentsByDate.get(formattedNewDate).add(appointmentToChange);
                    }
                    else
                    {
                        boolean dayIsAvailable=false;

                        for (Appointment currentAppointment: this.appointmentsByDate.get(formattedNewDate))
                        {
                            if (currentAppointment.getStartTime()==appointmentToChange.getStartTime())
                            {
                                dayIsAvailable=false;
                                break;
                            }
                            dayIsAvailable=true;
                        }

                        if (dayIsAvailable)
                        {
                            appointmentToChange.setDate(newDate);
                            this.appointmentsByDate.get(formattedNewDate).add(appointmentToChange);
                        }
                        else
                        {
                            System.out.println("Can not change date to" + newDate+" because the day is not free!");
                        }
                    }
                    break;

                case "starttime":
                    //LocalTime parse gets instance of LocalTime from string
                    //Format ( "10:15:45")
                    LocalTime newStartTime=LocalTime.parse(newValue);
                    boolean startIsAvailable=false;

                    //Check if startTime is available
                    for (Appointment currentAppointment: this.appointmentsByDate.get(DateFormatter.formatDate(date)))
                    {
                        if (currentAppointment.getStartTime()!=newStartTime)
                        {
                            startIsAvailable=true;
                            break;
                        }
                    }

                    //If startTime is available
                    if (startIsAvailable)
                    {
                        LocalTime initialEndTime=appointmentToChange.getEndTime();
                        long durationMinutes=Duration.between(startTime,initialEndTime).toMinutes();

                        LocalTime newEndTime=newStartTime.plusMinutes(durationMinutes);

                        boolean isDurationAvailable=true;

                        for (Appointment currentAppointment: this.appointmentsByDate.get(dateFormatter.formatDate(date)))
                        {
                            if (currentAppointment!=appointmentToChange && isOverlapping(currentAppointment,newStartTime,newEndTime))
                            {
                                isDurationAvailable=false;
                                break;
                            }
                        }

                        if (!isDurationAvailable)
                        {
                            System.out.println("The new appointments duration is not available for the specified date!");
                        }
                        else
                        {
                            appointmentToChange.setStartTime(newStartTime);
                            appointmentToChange.setEndTime(newEndTime);
                        }

                    }
                    else
                    {
                        System.out.println("Can not change start time to" + newStartTime+" because it is used!");
                    }

                    break;
                case "endtime":
                    LocalTime newEndTime=LocalTime.parse(newValue);

                    boolean endIsAvailable=false;

                    //Check if endTime is available
                    for (Appointment currentAppointment: this.appointmentsByDate.get(DateFormatter.formatDate(date)))
                    {
                        if (currentAppointment.getEndTime()!=newEndTime)
                        {
                            endIsAvailable=true;
                            break;
                        }
                    }

                    if (endIsAvailable)
                    {
                        LocalTime initialEndTime=appointmentToChange.getEndTime();

                        boolean isDurationAvailable=true;

                        for (Appointment currentAppointment: this.appointmentsByDate.get(dateFormatter.formatDate(date)))
                        {
                            if (currentAppointment!=appointmentToChange && isOverlapping(currentAppointment,startTime,newEndTime))
                            {
                                isDurationAvailable=false;
                                break;
                            }
                        }

                        if (!isDurationAvailable)
                        {
                            System.out.println("The new appointments duration is not available for the specified date!");
                        }
                        else
                        {
                            long durationMinutes=Duration.between(startTime,initialEndTime).toMinutes();
                            appointmentToChange.setEndTime(newEndTime);

                            LocalTime newStartTimeParsed=newEndTime.minusMinutes(durationMinutes);

                            appointmentToChange.setStartTime(newStartTimeParsed);

                        }
                        System.out.println("Appointment End Time updated successfully.");
                        return;
                    }
                    break;

                case "name":
                    appointmentToChange.setName(newValue);
                    break;
                case "note":
                    appointmentToChange.setNote(newValue);
                    break;

                default:
                    System.out.println("The option entered is invalid!\nPlease, choose valid option!");
                    return;
            }

            System.out.println("Successfull operation of updating appointment!");

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
        String correctDateFormat=dateFormatter.formatDate(date);

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
        String correctBeginDate=dateFormatter.formatDate(beginDate);
        String correctEndDate=dateFormatter.formatDate(endDate);

        boolean beginDateIsContained=this.appointmentsByDate.containsKey(correctBeginDate);
        boolean endDateIsContained=this.appointmentsByDate.containsKey(correctEndDate);

        busyHoursByDay.clear();

        if (beginDateIsContained && endDateIsContained)
        {
            for (LocalDate currentDate=beginDate;currentDate.isBefore(endDate.plusDays(1));currentDate=currentDate.plusDays(1))
            {
                String correctFormatCurrDate=dateFormatter.formatDate(currentDate);

               if (this.appointmentsByDate.containsKey(correctFormatCurrDate))
               {
                   List<Appointment> appointmentsList = this.appointmentsByDate.get(correctFormatCurrDate);

                   for (Appointment appointment : appointmentsList)
                   {
                       String dayOfWeek = currentDate.getDayOfWeek().toString();

                       double currentBusyHours = busyHoursByDay.getOrDefault(dayOfWeek, 0.0);

                       double appointmentDurationHours = appointment.getEndTime().getHour() - appointment.getStartTime().getHour();

                       busyHoursByDay.put(dayOfWeek, currentBusyHours + appointmentDurationHours);
                   }
               }

            }

            List<Map.Entry<String, Double>> sortedBusyDays = new ArrayList<>(busyHoursByDay.entrySet());
            sortedBusyDays.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            System.out.println("Busy Days Statistics for -> "+"Begin Date: "+correctBeginDate+" End Date: "+correctEndDate);

            for (Map.Entry<String, Double> kvp : sortedBusyDays)
            {
                String dayOfWeek = kvp.getKey();
                double busyHours = kvp.getValue();

                System.out.println(dayOfWeek + ": " + busyHours + " busy hours");
            }
        }
        else
        {
            System.out.println("Invalid time span! No such begin or end date is contained !");
        }

    }

    //TO DO: implement findSlot
    private boolean isSlotAvailable(LocalTime start, LocalTime end, int hoursDuration)
    {
        return Duration.between(start, end).toHours() >= hoursDuration;
    }

    @Override
    public void findSlot(LocalDate fromDate, int hoursDuration)
    {
        //Initializing boundaries (From task description we have to search for available slot between 8 AM to 17 PM)
        //StartTime bound 8 AM
        LocalTime startTimeBoundary = LocalTime.of(8, 0);
        //EndTime bound 17 PM
        LocalTime endTimeBoundary = LocalTime.of(17, 0);

        LocalDate currentDate = fromDate;

        //WeekDay means day from the week
        boolean currentDateIsWeekDay=currentDate.getDayOfWeek() != DayOfWeek.SATURDAY && currentDate.getDayOfWeek() != DayOfWeek.SUNDAY;

        while (currentDateIsWeekDay)
        {
            boolean currentDayIsWeekday = (currentDate.getDayOfWeek() != DayOfWeek.SATURDAY) && (currentDate.getDayOfWeek() != DayOfWeek.SUNDAY);

                    String formattedDate = dateFormatter.formatDate(currentDate);

                    if (appointmentsByDate.containsKey(formattedDate))
                    {
                        List<Appointment> appointmentList = appointmentsByDate.get(formattedDate);

                        LocalTime availableStartTime = startTimeBoundary;
                        LocalTime availableEndTime = endTimeBoundary;

                        for (Appointment appointment : appointmentList)
                        {
                            LocalTime appointmentStart = appointment.getStartTime();
                            LocalTime appointmentEnd = appointment.getEndTime();

                            if (availableStartTime.isBefore(appointmentStart))
                            {
                                // Found a slot before the first appointment

                                LocalTime appointmentSlotStart = availableStartTime;
                                LocalTime appointmentSlotEnd = appointmentStart;

                                if (isSlotAvailable(appointmentSlotStart, appointmentSlotEnd, hoursDuration))
                                {
                                    System.out.println("Free appointment slot found!\nAppointment available info:");
                                    System.out.println("Date: " + formattedDate);
                                    System.out.println("Appointment Start Time: " + appointmentSlotStart);
                                    System.out.println("Appointment End Time: " + appointmentSlotEnd);
                                    return;
                                }
                            }

                            availableStartTime = appointmentEnd;
                        }

                        if (availableStartTime.isBefore(availableEndTime)) {
                            // Found a slot after the last appointment
                            LocalTime appointmentSlotStart = availableStartTime;
                            LocalTime appointmentSlotEnd = availableEndTime;

                            if (isSlotAvailable(appointmentSlotStart, appointmentSlotEnd, hoursDuration)) {
                                System.out.println("Free appointment slot found!\nAppointment available info:");
                                System.out.println("Date: " + formattedDate);
                                System.out.println("Appointment Start Time: " + appointmentSlotStart);
                                System.out.println("Appointment End Time: " + appointmentSlotEnd);
                                return;
                            }
                        }
                    }
                    else
                    {
                        // No existing appointments on this day

                        LocalTime appointmentSlotStart = startTimeBoundary;
                        LocalTime appointmentSlotEnd = endTimeBoundary;

                        if (isSlotAvailable(appointmentSlotStart, appointmentSlotEnd, hoursDuration))
                        {
                            System.out.println("Free appointment slot found!\nAppointment available info:");
                            System.out.println("Date: " + formattedDate);
                            System.out.println("Appointment Start Time: " + appointmentSlotStart);
                            System.out.println("Appointment End Time: " + appointmentSlotEnd);
                            return;
                        }
                    }
                }
            currentDate = currentDate.plusDays(1);
        System.out.println("No available appointment slot found in range from 8 to 17 o'clock.");
    }

    private Appointment parseAppointmentFromLine(String line)
    {
        // Parse each line and create an Appointment object based on your file format
        // Return the parsed Appointment object

        // Example format: startTime-endTime
        String[] parts = line.split("-");

        LocalDate date = LocalDate.parse(parts[0]);
        LocalTime startTime = LocalTime.parse(parts[1]);
        LocalTime endTime = LocalTime.parse(parts[2]);
        String name=parts[3];
        String note=parts[4];

        return new Appointment(date, startTime,endTime,name,note);
    }
    public List<Appointment> loadCalendarFromFile(String fileName)
    {
        CommandParser commandParser=new CommandParser();
        try
        {
            Path filePath=Paths.get(fileName);
            commandParser.open(filePath);

            List<Appointment> currentCalendarAppointments=new ArrayList<>();

            for (String line: commandParser.getContent())
            {
                Appointment currentAppointment=parseAppointmentFromLine(line);
                currentCalendarAppointments.add(currentAppointment);

                return currentCalendarAppointments;
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    @Override
    public void findSlotWith(LocalDate fromDate, int hoursDuration, String calendarFileName)
    {
        List<Appointment> appointmentListLoaded=loadCalendarFromFile(calendarFileName);

        System.out.println("Searching for available slot in the current calendar:");
        findSlot(fromDate, hoursDuration);

        // Find slot in the saved calendar
        System.out.println("Searching for available slot in the saved calendar: " + calendarFileName);

        PersonalCalendar calendarFromFile=new PersonalCalendar();

        calendarFromFile.setAppointments(appointmentListLoaded);

        calendarFromFile.findSlot(fromDate,hoursDuration);
    }

    @Override
    public void merge(String calendarFileLocation)
    {
        List<Appointment> appointmentsLoaded=loadCalendarFromFile(calendarFileLocation);

        PersonalCalendar calendarFromFile=new PersonalCalendar();

        calendarFromFile.setAppointments(appointmentsLoaded);


        for (Appointment currentAppointment: appointmentsLoaded)
        {
            if (this.appointments.contains(currentAppointment))
            {
                System.out.println("Conflict occured with current appointment!");
            }
            System.out.println(currentAppointment);

            System.out.println("You have to choose an action to continue:");
            System.out.println("1. Keep existing appointment");
            System.out.println("2. Move loaded appointment to another date and time");

            Scanner scanner=new Scanner(System.in);

            int choiceEntered=scanner.nextInt();

            if (choiceEntered==1)
            {
                //Keep existing appointment
                continue;
            }
            else if (choiceEntered==2)
            {
                //Move appointment to another date and time
                //Get new date

                System.out.println("Enter new date for appointment:");
                String newDate=scanner.next();
                LocalDate newDateParsed = LocalDate.parse(newDate);

                System.out.println("Enter new start time for appointment:");
                String newStartTime=scanner.next();
                LocalTime newTimeParsed=LocalTime.parse(newStartTime);

                System.out.println("Enter new end time for appointment:");
                String newEndTime=scanner.next();
                LocalTime newEndTimeParsed=LocalTime.parse(newEndTime);


                String newFormattedDate=dateFormatter.formatDate(newDateParsed);

                Appointment newAppointment=new Appointment(newDateParsed,newTimeParsed,newEndTimeParsed,currentAppointment.getName(),currentAppointment.getNote());
                if (this.appointmentsByDate.containsKey(newFormattedDate))
                {
                    this.appointmentsByDate.get(newFormattedDate).add(newAppointment);
                }
                else
                {
                    this.appointmentsByDate.put((newFormattedDate),new ArrayList<>());
                    this.appointmentsByDate.get(newFormattedDate).add(newAppointment);
                }

                this.appointments.remove(currentAppointment);
                this.appointments.add(newAppointment);

                System.out.println("Appointment moved to new date, new start time,new end time in current calendar!.");

            }
        }
    }

    @Override
    public String toString()
    {
        return "Personal Calendar:\n" +
                "Appointments By Date:" + appointmentsByDate +
                '}';
    }
}
