package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateComparison {
    DateTimeFormatter formatter;
    LocalDate currentDate;
    String currentDateString;

    public DateComparison() {
        currentDate = LocalDate.now();
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        currentDateString = currentDate.format(formatter);

        System.out.println("Data corrente: " + currentDateString);
    }

    public int compareDate(String date) {
        LocalDate otherDateString = LocalDate.parse(date, formatter);

        return (int) ChronoUnit.DAYS.between(currentDate, otherDateString);
    }


}
