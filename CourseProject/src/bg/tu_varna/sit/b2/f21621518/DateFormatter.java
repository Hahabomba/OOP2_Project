package bg.tu_varna.sit.b2.f21621518;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Returns LocalDate someDate in String representation ( Format : dd.MM.yyyy)
public class DateFormatter
{
    private static final DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static String formatDate(LocalDate date)
    {
        return date.format(formatter);
    }
}
