import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pojo.Basket;
import pojo.Day;
import pojo.Invoice;
import pojo.Product;
import presentation.*;
import repository.CSVhandler;
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

        Path filePath = Paths.get("data\\PhotoShop_PriceList.csv");
        Product[] productCatalogue = CSVhandler.readProducts(filePath);

        for (Product product : productCatalogue) {
            System.out.println(product);
        }

        Path path = Paths.get("data\\PhotoShop_OpeningHours.csv");
        Day[] workDays = CSVhandler.readOpeningTimes(path);

        for (Day day : workDays) {
            System.out.println(day);
        }
    }
}
