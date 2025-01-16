package model.utils;

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
    }

    public int compareDate(String date) {
        try {
            LocalDate otherDateString = LocalDate.parse(date, formatter);
            System.out.println("Comparing current date: " + currentDate + " with due date: " + otherDateString);
            int daysDifference = (int) ChronoUnit.DAYS.between(currentDate, otherDateString);
            System.out.println("Days difference: " + daysDifference);
            return daysDifference;
        } catch (Exception e) {
            System.out.println("Error parsing date: " + date);
            e.printStackTrace();
            return 0;
        }
    }
}
