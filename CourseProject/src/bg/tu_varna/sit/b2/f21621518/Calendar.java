package bg.tu_varna.sit.b2.f21621518;

import java.time.LocalDate;
import java.time.LocalTime;

public interface Calendar
{
 void book(Appointment appointmentToBook);
 void unbook(LocalDate date, LocalTime startTime,LocalTime endTime);
 void agenda(LocalDate date);

 void find(String string);
}
