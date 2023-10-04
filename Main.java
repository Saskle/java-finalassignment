import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import pojo.Basket;
import pojo.Customer;
import pojo.Order;
import pojo.Product;
import presentation.*;
import static presentation.PrintFormatter.*;

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

        ShopPresentation shopPresentation = new ShopPresentation();
        shopPresentation.startApp();

        // String text = "Hello.\nHow do you do?\n";
        // System.out.println(text);
        // System.out.println(addBox(text));
        // System.out.println(addBox("12345"));
        // System.out.println(addBox("123456"));

        //System.out.println(addBox("This is going to be a longer string for testing purposes.\n Wonder how you'll handle that.\n"));

        // System.out.println(ANSI_GREEN + "This text is red!" + ANSI_RESET);
        // System.out.println(ANSI_RED + "This text is red!" + ANSI_RESET);
        // System.out.println(ANSI_YELLOW + "This text is red!" + ANSI_RESET);
        // System.out.println(ANSI_BLUE + "This text is red!" + ANSI_RESET);
        // System.out.println(ANSI_PURPLE + "This text is red!" + ANSI_RESET);
        // System.out.println(ANSI_CYAN + "This text is red!" + ANSI_RESET);
        // System.out.println(ANSI_WHITE + "This text is red!" + ANSI_RESET);
        // System.out.println(ANSI_BLACK + "This text is red!" + ANSI_RESET);



        // ObjectMapper mapper = JsonMapper.builder()
        //      .addModule(new JavaTimeModule())
        //      .build();

        // LocalDateTime now = LocalDateTime.now();

        // try {
        //     mapper.writeValue(new File("data\\\\latestPickUpTime.json"), now);    
        // } catch (Exception e) {
        //     // TODO: handle exception
        // }
        
        // adding our custom Key Deserializer for HashMap<Product, Integer> to the mapper
        // SimpleModule simpleModule = new SimpleModule();
        // simpleModule.addKeyDeserializer(Product.class, new ProductDeserializer());
        // mapper.registerModule(simpleModule);

        // Basket basket = new Basket();
        // basket.addProducts(photo3, 2);
        // basket.addProducts(photo1, 4);

        // try {
        //     // writing the Basket object to JSON
        //     mapper.writeValue(new File("data//basket.json"), basket);

        //     // creating a new Basket object of that JSON
        //     Basket newBasket = mapper.readValue(new File("data//basket.json"), Basket.class);
        //     System.out.println(newBasket);
        // } catch (IOException exception) {
        //     System.out.println(exception);
        // }

        // Order order = new Order(2);
        // Customer customer = new Customer(1, "Saskia", "de Klerk", "saskle@calco.nl");
        // order.basket.addProducts(photo2, 1);
        // order.basket.addProducts(photo1, 2);
        // order.setCustomer(customer);
        // order.setOrderTime(LocalDateTime.now());
        // order.setPickupTime(LocalDateTime.of(2023, 10, 28, 10, 22, 50));

        // try {
        //     mapper.writeValue(new File("data//order.json"), order);
        //     Order orderPrint = mapper.readValue(new File("data//order.json"), Order.class);
        //     System.out.println(orderPrint);

        //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd MMMM yyyy HH:mm");
        //     System.out.println(orderPrint.getOrderTime().format(formatter));
        //     System.out.println(orderPrint.getPickUpTime().format(formatter));

        // } catch (Exception e) {
        //     System.out.println(e);
        // }

        
    }
}
