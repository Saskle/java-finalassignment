package repository;

import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalTime;

import pojo.Day;

// ----------------- PURPOSE: Reading Opening Hours CSV and convert it to a Day array -----------------

public class OpeningHoursCSVhandler extends CSVhandler {

    // TODO think if we can't make this an abstract class with static methods?
    public OpeningHoursCSVhandler(Path path) {
        setLines(path);
    }

     public Day[] readCSV() {

        // Format of OpeningHours.CSV:
        // [0] (first row) is id
        // [1] is name of day in lowercase
        // [2] opening time in format 00:00
        // [3] closing time in format 00:00

        // initalizing an array to store the days in
        Day[] workDays = new Day[7];

        // going through each line (assuming that the file only contains 7 rows for each day)
        for (int i = 0; i < lines.size(); i++) {

            // splitting the values of this line
            String[] values = lines.get(i).split(VALUE_DELIMITER);

            // split hours and minutes of time strings
            String[] openingTime = values[2].split(TIME_DELIMITER);
            String[] closingTime = values[3].split(TIME_DELIMITER);

            // initializing values to create Days with
            int id = 0;
            int openingHour = 0;
            int openingMinute = 0;
            int closingHour = 0;
            int closingMinute = 0;

            // parsing strings to integers
            try {
                id = Integer.parseInt(values[0]);
                openingHour = Integer.parseInt(openingTime[0]);
                openingMinute = Integer.parseInt(openingTime[1]);
                closingHour = Integer.parseInt(closingTime[0]);
                closingMinute = Integer.parseInt(closingTime[1]);
            } catch (NumberFormatException exception) {
                System.out.println(exception);
            }

            // parsing day and time to LocalTime and DayOfWeek respectively
            DayOfWeek dayName = DayOfWeek.valueOf(values[1].toUpperCase());            
            LocalTime opening = LocalTime.of(openingHour, openingMinute);
            LocalTime closing = LocalTime.of(closingHour, closingMinute);

            // creating a new day in the array
            workDays[i] = new Day(id, dayName, opening, closing);
        }
        return workDays;
    }

}
