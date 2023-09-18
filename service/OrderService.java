package service;

import repository.*;
import pojo.*;

// ----------------- PURPOSE: handling order data & starting app -----------------

public class OrderService extends Service {

    private JSONhandler jsonHandler;
    private ScheduleService scheduleService;
    private Order order; // only current order is stored
 
    // TODO format Strings to print for presentation, but final formatting is for the presentation layer

    public OrderService() {
    }

    // what do these methods return to the presentation layer? strings? 
    public void createOrder() {
        int id = generateID();
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
        return  order.toString();
    }

    // public void createCustomer(String firstName, String lastName, String email) {
    //     int id = createID(); // for the sake of simplicity we assume the ID is unique
    //     this.order.setCustomer(new Customer(id, firstName, lastName, email));
    // }

    // public String showCustomer() {
    //     if (this.order.hasCustomer()) {
    //         return this.order.getCustomer().toString();
    //     } else {
    //         return "\tNo customer found.";
    //     }
    // }

    // public boolean hasCustomer() {
    //     return this.order.hasCustomer(); // TODO think if this can be implemented better
    // }
    
    // public void addProducts(int id, int quantity) {
    //     order.basket.addProducts(productCatalog[id].clone(), quantity);
    // }
    // public void addProducts(String name, int quantity) {
    //     Product product = getProduct(name); // is already returning a clone
    //     order.basket.addProducts(product, quantity);
    // }
    // public void removeProducts(int id, int quantity) {
    //     order.basket.removeProducts(productCatalog[id], quantity);
    // }
    // public void removeProducts(String name, int quantity) { 
    //     Product product = getProduct(name);
    //     order.basket.removeProducts(product, quantity);
    // }
    // public boolean hasProducts() {
    //     return !order.basket.getProducts().isEmpty();
    // }
    // public boolean hasProduct(Product product) {
    //     return order.basket.getProducts().containsKey(product);
    // }
    // public Product getProduct(String name) {
    //     for (int i = 0; i < productCatalog.length; i++) {
    //         if (productCatalog[i].getName().equals(name)) {
    //             return productCatalog[i].clone();
    //         }
    //     }
    //     return null; // not sure if this is right
    // }
    // public boolean isProduct(String name) {
    //     for (int i = 0; i < productCatalog.length; i++) {
    //         if (productCatalog[i].getName().equals(name)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    // public String showBasket() {
    //     return order.basket.toString();
    // }

    // public int basketSize() {
    //     return order.basket.getProducts().size();
    // }


    // CSV
    // methods for loading csv info to products, also into catalogue?
    // methods for loading opening hours from csv into class

    // JSON
    // method saveOrder() -> save to json via jsonHandler
    // method loadOrder()

}
