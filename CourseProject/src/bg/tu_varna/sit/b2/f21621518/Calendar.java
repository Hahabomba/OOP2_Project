package bg.tu_varna.sit.b2.f21621518;

import java.time.LocalDate;
import java.time.LocalTime;

public interface Calendar
{
 void book(Appointment appointmentToBook);
 void unbook(LocalDate date, LocalTime startTime,LocalTime endTime);
 void agenda(LocalDate date);

 void change(LocalDate date,LocalTime startTime,String option,String newValue);

 void find(String string);

 void holiday(LocalDate date);

 void busyDays(LocalDate beginDate,LocalDate endDate);

 void findSlot(LocalDate fromDate,int hoursDuration);

 void findSlotWith(LocalDate fromDate,int hoursDuration,String calendar);

 void merge(String calendarFileLocation);
}
