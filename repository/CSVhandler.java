package repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import pojo.Day;
import pojo.Product;

public abstract class CSVhandler {

    // OpeningHours.csv
    // 3;Tuesday;09:00;18:00
    // 4;Wednesday;09:00;18:00
    // 5;Thursday;09:00;18:00
    // 6;Friday;09:00;21:00
    // 7;Satuday;09:00;16:00
    
    // PriceList.csv
    // 5;Canvas 30 x 40 matte;24.00;12:00
    // 6;Canvas 30 x 40 high gloss;27.50;12:00
    // 7;Canvas 100 x 150 matte;64.75;16:00
    // 8;Canvas 100 x 150 high gloss;72.50;16:00

    // fuction reading for this needs to be uniform, handling the specific data is going to be in the classes (?)
    // that means the data will be stored in a 2DArray<String>?

    // make the day names uppercase so it complies with DayOfWeek enum!

    private static List<String> lines;

    // define here the delimiter for all CSVs
    private final static String VALUE_DELIMITER = ";";
    private final static String TIME_DELIMITER = ":";

    public List<String> getLines() {
        return lines; // TODO create a proper copy here
    }

    public static void setLines(Path path) {
        try {
            lines = Files.lines(path)
                                .skip(1) // first line in csv's are headers/labels
                                .toList();
        } catch (IOException exception) {
            System.out.println(exception);
        }
    }


    public static Day[] readOpeningTimes(Path path) {

        setLines(path);

        // Format of .CSV:
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
            System.out.println(workDays[i]);
        }
        return workDays;
    }

    public static Product[] readProducts(Path path) {

        setLines(path);

        // Format of .CSV:
        // [0] (first row) is id
        // [1] product name
        // [2] price, must be parsed to BigDecimal
        // [3] working hours in format 00:00

        // creating an array of products the lenght of the amount of lines
        Product[] products = new Product[lines.size()];

        // iterating through the lines one by one
        for (int i = 0; i < lines.size(); i++) {

            // splitting the values of this line
            String[] values = lines.get(i).split(VALUE_DELIMITER); 

            // parsing price to BigDecimal
            BigDecimal price = new BigDecimal(values[2]);

            // splitting the working time on ":"
            // FOR NOW, I only support working hours of whole hours
            String[] workingTime = values[3].split(TIME_DELIMITER);

            int id = 0;
            int workingHours = 0;
            try {
                id = Integer.parseInt(values[0]);
                workingHours = Integer.parseInt(workingTime[0]);                
            } catch (NumberFormatException exception) {
                System.out.println(exception);
            }

            // create a new Product using the parsed values
            products[i] = new Product(id, values[1], price, workingHours);
            System.out.println(products[i]);
        }
        return products;

    }






}
