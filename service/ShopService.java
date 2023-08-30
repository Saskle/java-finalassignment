package service;
import repository.*;

import java.math.BigDecimal;

import pojo.*;

// ----------------- PURPOSE: retrieve & save data upon initialisation / closing, insert calculated data -----------------
// for sake of simplicity, right now all the 'business' logic is here, perhaps break it up futher later

public class ShopService {
    
    public static final Product[] productCatalog = new Product[] {
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

    private CSVhandler csvHandler;
    private JSONhandler jsonHandler;

    private Order order; // current order

    public ShopService() {
        // not sure what to put here yet
    }

    // what do these methods return to the presentation layer? strings? the objects themselves?
    public void createOrder() {
        int id = createID();
        // check if order exists in json by checking equals / id (?)
        // if yes, genereate new one
        // if no, create new order with that id

    }
    public void getOrder(int orderID) {
        // retrieve order from json
    }

    public void saveOrder(Order order) {
        // check if this order already exists in json
        // if not, put it in there and save it
    }

    public void createCustomer() {
        int id = createID();
        // check if id is unique?
        this.order.setCustomer(new Customer(null, null, null)); // TODO constructor that allows setting id
    }
    public void getCustomer(int customerID) {
        // check if there is a customer related to the order
    }
    public void setCustomer() {
        // add customer to order
    }

    private int createID() { 
        // random Id generated between 1 - 10000 -> semi-unique (does it have to be unique tho?)
        return (int) (Math.random() * 10000 + 1); // TODO remove this logic from customer!
    }

    // CSV
    // methods for loading csv info to products, also into catalogue?
    // methods for loading opening hours from csv into class

    // JSON
    // method saveOrder() -> save to json via jsonHandler
    // method loadOrder()

}
