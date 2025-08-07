package util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateTimeConverter {



    public static String millisecToDDMMYYYY(String dateMillisec){

        DateFormat simple = new SimpleDateFormat("ddMMYYYY");
        long dateMillisecLong = Long.parseLong(dateMillisec);
        Date result = new Date(dateMillisecLong);
        return (simple.format(result));
    }
    public static String getExpectedDateFormat(String actualDOB, String actualFormat, String expectedFormat) throws ParseException {

        SimpleDateFormat formatterActual = new SimpleDateFormat(actualFormat);
        SimpleDateFormat formatterExpected = new SimpleDateFormat(expectedFormat);
        Date dob=formatterActual.parse(actualDOB);
        return formatterExpected.format(dob);
    }

    public static LocalDate getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate;

    }

    @Test
    public static void getCurrentDate1(){

//  calendar.getTimezone.getDefaultName();
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        DateTime dateTime = DateTime.now( DateTimeZone.getDefault() ); // Explicitly using default time zone.
        System.out.println(zonedDateTime + "/n new time  " + dateTime);
        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        String dateInString = "2013-02-04 10:11:59";
        DateTime dateTime1 = DateTime.parse(dateInString, formatter);
        System.out.print(dateTime1);

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.US);

        String strLocalDate = "2015-10-23T03:34:40";

        LocalDateTime localDate = LocalDateTime.parse(strLocalDate, formatter1);

        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDate);
        System.out.println(localDate);
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDate));
        System.out.println(DateTimeFormatter.ofPattern("HH:mm:ss yyyy-MM-dd ").format(localDate));
    }

    public static LocalDate getLastDateOfMonth(String dateFormat, LocalDate currentDate) {
        LocalDate lastDateOfMonth;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        lastDateOfMonth = currentDate.withDayOfMonth(currentDate.getMonth()
                .length(currentDate.isLeapYear()));
        System.out.println("statement generation date " + lastDateOfMonth);
        return lastDateOfMonth.parse(lastDateOfMonth.format(formatter));

    }

    public static LocalDate getFirstDateOfMonth(String dateFormat, LocalDate currentDate) {
        LocalDate firstDateOfMonth;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        firstDateOfMonth = currentDate.withDayOfMonth(1);
        return firstDateOfMonth.parse(firstDateOfMonth.format(formatter));

    }

    public static ZonedDateTime getZonedDateTime() {

        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        DateTime dateTime = DateTime.now( DateTimeZone.getDefault() ); // Explicitly using default time zone.
        return zonedDateTime;
    }

    public static DateTime getDateTimeFromString(String dateInString){
//        DateTime dateTime = new DateTime();
        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
//        DateTime dateTime = DateTime.parse(dateInString, formatter);
        DateTime dateTime = formatter.parseDateTime(dateInString);
        System.out.print(dateTime);
        return dateTime;
    }

    public static String getLocalDateTimeFromString(String dateTimeInString, String dateValue){

        LocalDateTime localDateTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeInString);
        LocalDateTime localDate = LocalDateTime.parse(dateValue, formatter);
        System.out.println("final string value "+DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDate));
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDate);

//        return lastDateOfMonth.parse(lastDateOfMonth.format(formatter));

    }


}
