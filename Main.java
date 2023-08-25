import java.math.BigDecimal;

import pojo.Customer;
import pojo.Order;
import pojo.Product;

public class Main {
    public static void main(String[] args) {
        
        // hardcoded product info, remove when implementing csv reader!
        Product photo1 = new Product(1, "Paper 10 x 15 mat", new BigDecimal("1.40"), 1);
        Product photo2 = new Product(2, "Paper 10 x 15 high gloss", new BigDecimal("1.50"), 1);
        Product photo3 = new Product(3, "Paper 30 x 40 mat", new BigDecimal("4.50"), 2);
        Product photo4 = new Product(4, "Paper 30 x 40 high gloss", new BigDecimal("5.00"), 2);
        Product photo5 = new Product(5, "Canvas 30 x 40 mat", new BigDecimal("24.00"), 12);
        Product photo6 = new Product(6, "Canvas 30 x 40 high gloss", new BigDecimal("27.50"), 2);
        Product photo7 = new Product(7, "Canvas 100 x 150 mat", new BigDecimal("64.75"), 16);
        Product photo8 = new Product(8, "Canvas 100 x 150 high gloss", new BigDecimal("72.50"), 16);
        Product photo9 = new Product(9, "Glass 30 x 40 mat", new BigDecimal("27.50"), 14);
        Product photo10 = new Product(10, "Glass 30 x 40 high gloss", new BigDecimal("27.50"), 14);
        Product photo11 = new Product(11, "Glass 100 x 150 mat", new BigDecimal("82.50"), 20);
        Product photo12 = new Product(12, "Glass 100 x 150 high gloss", new BigDecimal("82.50"), 20);


        Customer c_one = new Customer("Saskia", "de Klerk", "sas@calco.nl");
        Customer c_two = new Customer("John", "Doe", "johndoe@email.us");
        Customer c_three = new Customer("Willem-Alexander", "van Oranje-Nassau", "prinspils@koningshuis.nl");
        
        System.out.println(c_one.getId() + " " + c_one.getFirstName() + " " + c_one.getLastName() + " "  + c_one.getEmail());

        Order order = new Order(1);
        order.addProduct(photo1);
        order.addProduct(photo2);
        order.addProduct(photo3);

        order.setCustomer(c_one);

        System.out.println(order);
        


    }
}