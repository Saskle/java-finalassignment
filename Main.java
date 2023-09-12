import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pojo.Basket;
import pojo.Day;
import pojo.Invoice;
import pojo.Product;
import presentation.*;
import service.OrderService;

public class Main {
    public static void main(String[] args) {
        
        Product photo1 = new Product(1, "Paper 10 x 15 mat", new BigDecimal("1.40"), 1);
        Product photo2 = new Product(2, "Paper 10 x 15 high gloss", new BigDecimal("1.50"), 1);
        Product photo3 = new Product(3, "Paper 30 x 40 mat", new BigDecimal("4.50"), 2);
        //Product photo4 = new Product(4, "Paper 30 x 40 high gloss", new BigDecimal("5.00"), 2);
        //Product photo5 = new Product(5, "Canvas 30 x 40 mat", new BigDecimal("24.00"), 12);
        //Product photo6 = new Product(6, "Canvas 30 x 40 high gloss", new BigDecimal("27.50"), 2);
        //Product photo7 = new Product(7, "Canvas 100 x 150 mat", new BigDecimal("64.75"), 16);
        //Product photo8 = new Product(8, "Canvas 100 x 150 high gloss", new BigDecimal("72.50"), 16);
        //Product photo9 = new Product(9, "Glass 30 x 40 mat", new BigDecimal("27.50"), 14);
        //Product photo10 = new Product(10, "Glass 30 x 40 high gloss", new BigDecimal("27.50"), 14);
        //Product photo11 = new Product(11, "Glass 100 x 150 mat", new BigDecimal("82.50"), 20);
        //Product photo12 = new Product(12, "Glass 100 x 150 high gloss", new BigDecimal("82.50"), 20);

        //ShopPresentation shopPresentation = new ShopPresentation();
        //shopPresentation.startApp();


        /* 1. file to lines in something iterate-able, skip first line
         * 2. loop through each line, skip first one (headers) -> list of lines
         *          - for OPENINGHOURS, create a day for each line
         *          - get opening hours and split minutes / hours by string.split
         *          - fill correct parameters in day object
         *          - opening hours service should hold a collection of days (array)
         * 
         *          - for PRODUCTS, create a product for each line
         *          - cast data to right data type
         */

        try {
            Path path = Paths.get("data\\PhotoShop_OpeningHours.csv");
            List<String> lines = Files.lines(path)
                            .skip(1) // first line in csv's are headers/labels
                            .toList();
            
            // initalizing workday Collection
            Day[] workDays = new Day[7];

            // going through each line (assuming that the file only contains 7 rows for each day)
            for (int i = 0; i < lines.size(); i++) {

                // splitting the values of this line
                String[] values = lines.get(i).split(";"); // TODO make this regex a constant
            
                int dayID = 0;
                int openingHour = 0;
                int openingMinute = 0;
                int closingHour = 0;
                int closingMinute = 0;

                String[] openingTime = values[2].split(":");
                String[] closingTime = values[3].split(":");

                try {
                    dayID = Integer.parseInt(values[0]);
                    openingHour = Integer.parseInt(openingTime[0]);
                    openingMinute = Integer.parseInt(openingTime[1]);
                    closingHour = Integer.parseInt(closingTime[0]);
                    closingMinute = Integer.parseInt(closingTime[1]);
                } catch (NumberFormatException exception) {
                    System.out.println(exception);
                }

                // first row (index 0) is id
                // second row [1] is name
                // third and fourth row [2] [3] are opening and closing times

                
                LocalTime opening = LocalTime.of(openingHour, openingMinute);
                LocalTime closing = LocalTime.of(closingHour, closingMinute);

                workDays[i] = new Day(dayID, values[1], opening, closing);
                System.out.println(workDays[i]);

            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        // helo
    }

}
