import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pojo.Basket;
import pojo.Customer;
import pojo.Product;
import presentation.*;

public class Main {
    public static void main(String[] args) {
        
        Product photo1 = new Product(1, "Paper 10 x 15 mat", new BigDecimal("1.40"), 1);
        //Product photo2 = new Product(2, "Paper 10 x 15 high gloss", new BigDecimal("1.50"), 1);
        //Product photo3 = new Product(3, "Paper 30 x 40 mat", new BigDecimal("4.50"), 2);
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

        // Customer saskia = new Customer(0, "Saskia", "de Klerk", "sas@saskle.nl");
        // saskia.setAddress("Kadoelermeer 13");
        // saskia.setPostalCode("3068 KE");
        // saskia.setCity("Rotterdam");

        // Customer boy = new Customer(1, "Boy", "Franssen", "boy2@gmail.com");

        try {
            ObjectMapper mapper = new ObjectMapper();

            Customer saskia = mapper.readValue(new File("data//customer1.json"), Customer.class);
            Customer boy = mapper.readValue(new File("data//customer2.json"), Customer.class);

            System.out.println(saskia);
            System.out.println(boy);

            

        } catch (IOException exception) {
            System.out.println(exception);
        }
        
    }
}
