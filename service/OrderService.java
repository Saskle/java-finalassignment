package service;

import java.math.BigDecimal;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import repository.*;
import pojo.*;

// ----------------- PURPOSE: handling order, product and customer data -----------------

public class OrderService {
    
    // hardcoded product info, remove when implementing csv reader!
    public Product[] productCatalog = new Product[] {
        new Product(1, "Paper 10 x 15 mat", new BigDecimal("1.40"), 1),
        new Product(2, "Paper 10 x 15 high gloss", new BigDecimal("1.50"), 1),
        new Product(3, "Paper 30 x 40 mat", new BigDecimal("4.50"), 2),
        new Product(4, "Paper 30 x 40 high gloss", new BigDecimal("5.00"), 2),
        new Product(5, "Canvas 30 x 40 mat", new BigDecimal("24.00"), 12),
        new Product(6, "Canvas 30 x 40 high gloss", new BigDecimal("27.50"), 2),
        new Product(7, "Canvas 100 x 150 mat", new BigDecimal("64.75"), 16),
        new Product(8, "Canvas 100 x 150 high gloss", new BigDecimal("72.50"), 16),
        new Product(9, "Glass 30 x 40 mat", new BigDecimal("27.50"), 14),
        new Product(10, "Glass 30 x 40 high gloss", new BigDecimal("27.50"), 14),
        new Product(11, "Glass 100 x 150 mat", new BigDecimal("82.50"), 20),
        new Product(12, "Glass 100 x 150 high gloss", new BigDecimal("82.50"), 20)
     };

    //private CSVhandler csvHandler;
    //private JSONhandler jsonHandler;

    private Order order; // only current order is stored
 
    // TODO format Strings to print for presentation, but final formatting is for the presentation layer

    public OrderService() {
        // upon intialisation, create product catalog string to fill with all products
    }

    // what do these methods return to the presentation layer? strings? 
    public void createOrder() {
        int id = createID();
        // check if order exists in json by checking equals / id (?)
        this.order = new Order(id);

    }
    public void retrieveOrder() {
        // retrieve order from json
    }

    // TODO this is needed for InvoiceService
    public Order getOrder() {
        return this.order.clone();
    }

    // remove this when csv loader is implemented!
    public void setOrder(Order order) {
        this.order = order.clone();
    }

    
    public String showOrder() {
        String customer = "";
        if (this.order.hasCustomer()) {
            customer = order.getCustomer().toString();
        } else {
            customer = "No customer found.";
        }
        return  "Order no. " + order.getId() + "\n" +
                "\tCustomer info: \n" + customer + " \n" +
                "\tProducts\n" + 
                "\t" + showAllProducts() + "\n";
    }

    public void createCustomer(String firstName, String lastName, String email) {
        int id = createID(); // for the sake of simplicity we assume the ID is unique
        this.order.setCustomer(new Customer(id, firstName, lastName, email));
    }

    public String showCustomer() {
        if (this.order.hasCustomer()) {
            return this.order.getCustomer().toString();
        } else {
            return "No customer found.";
        }
    }

    public boolean hasCustomer() {
        return this.order.hasCustomer(); // TODO think if this can be implemented better
    }

    // public void printCustomer() {
    //     String customer =  this.order.getCustomer().toString();
    //     Stream<String> stream = Pattern.compile("\n").splitAsStream(customer);
    //     int counter = 1;
    //     stream.forEach(line -> System.out.println(counter + ". " + line));
    // }

    public void addProduct(int id) {
        this.order.addProduct(productCatalog[id].clone());
    }
    public void addProduct(String name) {
        this.order.addProduct(null); // TODO look up index
    }
    public String getProduct(int id) {
        return this.order.getProduct(id).toString();
    }
    public void removeProduct(int index) {
        this.order.deleteProduct(index);
    }
    public boolean hasProducts() {
        return !this.order.getAllProducts().isEmpty();
    }

    public String showAllProducts() {
        String allProducts = "";
        for (Product product : order.getAllProducts()) {
            allProducts.concat(product + "\n");
        }
        return allProducts;
    }


    private int createID() { 
        // random Id generated between 1 - 10000, so semi-unique 
        return (int) (Math.random() * 10000 + 1);
    }

    // CSV
    // methods for loading csv info to products, also into catalogue?
    // methods for loading opening hours from csv into class

    // JSON
    // method saveOrder() -> save to json via jsonHandler
    // method loadOrder()

}
