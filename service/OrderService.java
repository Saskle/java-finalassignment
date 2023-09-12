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
        return  "Order no. " + order.getId() + "\n" +
                "Customer info \n" + showCustomer() + " \n" +
                "Products\n" + 
                order.basket + "\n";
    }

    public void createCustomer(String firstName, String lastName, String email) {
        int id = createID(); // for the sake of simplicity we assume the ID is unique
        this.order.setCustomer(new Customer(id, firstName, lastName, email));
    }

    public String showCustomer() {
        if (this.order.hasCustomer()) {
            return this.order.getCustomer().toString();
        } else {
            return "\tNo customer found.";
        }
    }

    public boolean hasCustomer() {
        return this.order.hasCustomer(); // TODO think if this can be implemented better
    }
    
    public void addProducts(int id, int quantity) {
        order.basket.addProducts(productCatalog[id].clone(), quantity);
    }
    public void addProducts(String name, int quantity) {
        Product product = getProduct(name); // is already returning a clone
        order.basket.addProducts(product, quantity);
    }
    public void removeProducts(int id, int quantity) {
        order.basket.removeProducts(productCatalog[id], quantity);
    }
    public void removeProducts(String name, int quantity) { 
        Product product = getProduct(name);
        order.basket.removeProducts(product, quantity);
    }
    public boolean hasProducts() {
        return !order.basket.getProducts().isEmpty();
    }
    public boolean hasProduct(Product product) {
        return order.basket.getProducts().containsKey(product);
    }
    public Product getProduct(String name) {
        for (int i = 0; i < productCatalog.length; i++) {
            if (productCatalog[i].getName().equals(name)) {
                return productCatalog[i].clone();
            }
        }
        return null; // not sure if this is right
    }
    public boolean isProduct(String name) {
        for (int i = 0; i < productCatalog.length; i++) {
            if (productCatalog[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String showBasket() {
        return order.basket.toString();
    }

    public int basketSize() {
        return order.basket.getProducts().size();
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
