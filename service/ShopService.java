package service;
import repository.*;

import java.math.BigDecimal;

import pojo.*;

// ----------------- PURPOSE: all business / service related tasks (for now) -----------------

public class ShopService {
    
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

    private CSVhandler csvHandler;
    private JSONhandler jsonHandler;

    private Order order; // only current order is stored
    private Invoice invoice;

    public ShopService() {
        // upon intialisation, create product catalog string to fill with all products
        loadResources();
    }

    public void loadResources() {
        // get csv & json data into classes (?)
        // or make this private and just use constructor for this?
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
    public String getOrder() {
        return this.order.toString();
    }

    // remove this when csv loader is implemented!
    public void setOrder(Order order) {
        this.order = order.clone();
    }

    public void saveOrder() {
        // check if this order already exists in json
        // if not, put it in there and save it
    }

    public void createCustomer(String firstName, String lastName, String email) {
        int id = createID(); // for the sake of simplicity we assume the ID is unique
        this.order.setCustomer(new Customer(id, firstName, lastName, email));
    }
    public String getCustomer() {
        // check if there is a customer related to the order ?
        return this.order.getCustomer().toString();
        
    }
    public void setCustomer(Customer customer) {
        // add customer to order
        this.order.setCustomer(customer.clone());
    }
    public boolean hasCustomer() {
        return this.order.hasCustomer(); // TODO think if this can be implemented better
    }

    public void addProduct(int id) {
        this.order.addProduct(productCatalog[id].clone());
    }
    public void addProduct(String name) {
        this.order.addProduct(null); // TODO look up index
    }
    public String getProduct(int id) {
        return this.order.getProduct(id).toString();
    }

    public void createInvoice() {
        int id = createID(); // for the sake of simplicity we assume the ID is unique
        this.invoice = new Invoice(id, this.order.clone());
    }

    public String getInvoice() {
        return this.invoice.toString();
    }

    private int createID() { 
        // random Id generated between 1 - 10000 -> semi-unique (does it have to be unique tho?)
        return (int) (Math.random() * 10000 + 1);
    }

    // CSV
    // methods for loading csv info to products, also into catalogue?
    // methods for loading opening hours from csv into class

    // JSON
    // method saveOrder() -> save to json via jsonHandler
    // method loadOrder()

}
