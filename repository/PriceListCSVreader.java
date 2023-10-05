package repository;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;

import pojo.Product;

// ----------------- PURPOSE: Reading Price List CSV and convert it to a Product array -----------------

public class PriceListCSVreader extends CSVreader {

    private final static Path path = Paths.get("data\\PhotoShop_PriceList.csv");

    public PriceListCSVreader() {
        setLines(path);
    }
    
    @Override
    public Product[] readCSV() {

        // Format of PriceList.CSV:
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
            // right now, I only support working hours of whole hours
            String[] workingTime = values[3].split(TIME_DELIMITER);

            int id = 0;
            int workingHours = 0;

            try {
                id = Integer.parseInt(values[0]);
                workingHours = Integer.parseInt(workingTime[0]);         
            } catch (NumberFormatException exception) {
                System.out.println(exception);
            }

            // add a new Product using the parsed values to the array
            products[i] = new Product(id, values[1], price, workingHours);
        }
        return products;
    }

}
