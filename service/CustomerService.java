package service;

import pojo.Customer;
import repository.CustomerJSONhandler;

// ----------------- PURPOSE: handling & validating customer data (current customer) -----------------

public class CustomerService extends Service {
    private Customer customer;
    private OrderService orderService;
    private CustomerJSONhandler jsonHandler;

    // constructor injection -> making sure all services work with the same instance
    public CustomerService(OrderService orderService) {
        this.orderService = orderService;
        jsonHandler = new CustomerJSONhandler();  
        //initalisation
    }

    public Customer getCustomer() {
        if (hasCustomer()) {
            return this.customer.clone();    
        }
        return null; // TODO is this safe?
    }
    public void setCustomer(Customer customer) { // TODO is this needed?
        this.customer = customer;
    }

    public void createCustomer(String firstName, String lastName, String email) {
        // for the sake of simplicity we assume the ID is unique
        int id = generateID(); 
        setCustomer(new Customer(id, firstName, lastName, email));
    }

    public boolean hasCustomer() {
        if (customer == null) {
            return false;
        } return true;
    }

    public String showCustomer() {
        if (hasCustomer()) {
            return getCustomer().toString();
        } return "\tNo customer found.";
    }

    public void customerToOrder() {
        orderService.setCustomer(customer);
    }

    // saving and loading from JSON
    public void saveCustomer() {
        jsonHandler.saveJSON(customer);
    }
    public void loadCustomer() {
        customer = jsonHandler.readJSON();
    }
    public boolean hasSavedCustomer() {
        return jsonHandler.fileExists();
    }
    public void deleteCustomer() {
        jsonHandler.deleteFile();
    }
    
    // TODO adding other fields, validating their input
}
