package service;

import pojo.Customer;
import repository.CustomerJSONhandler;

// ----------------- PURPOSE: handling & validating customer data (current customer) -----------------

public class CustomerService extends IDservice {
    private Customer customer;
    private OrderService orderService;
    private CustomerJSONhandler jsonHandler;

    // allow email addresses that contains numbers and letters, - and . inbetween; must have @
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    // allow 10-digit phone numbers with whitespace, . - () and +
    private static final String PHONE_REGEX = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$";

    // allow postal codes consisting of 4 numbers and 2 letters, where the first number cannot be 0
    private static final String POSTALCODE_REGEX = "[1-9]{1}[0-9]{3}[a-zA-Z]{2}";

    // constructor injection -> making sure all services work with the same instance of Orderservice
    public CustomerService(OrderService orderService) {
        this.orderService = orderService;
        jsonHandler = new CustomerJSONhandler();  
    }

    public void createCustomer(String firstName, String lastName, String email) {
        // for the sake of simplicity we assume the ID is unique
        int id = generateID(); 
        this.customer = new Customer(id, firstName, lastName, email);
    }

    public Customer getCustomer() {
        if (hasCustomer()) {
            return this.customer.clone();    
        }
        return null; 
    }
    public boolean hasCustomer() {
        if (customer == null) {
            return false;
        } return true;
    }

    public String showCustomer() {
        if (hasCustomer()) {
            return getCustomer().toString();
        } return "\tNo customer found.\n";
    }

    // passing the customer to Orderservice
    public void customerToOrder() {
        orderService.setCustomer(customer);
    }

    // saving and loading from JSON
    public void saveCustomer() {
        try {
            jsonHandler.saveJSON(customer);
        } catch (NullPointerException exception) {
            System.out.println("There is no customer data to save to JSON: " + exception);
        }
    }
    public void loadCustomer() {
        try {
            customer = jsonHandler.readJSON();
        } catch (NullPointerException exception) {
            System.out.println("Customer couldn't be deserialized: " + exception);
        }
    }
    public boolean hasSavedCustomer() {
        return jsonHandler.fileExists();
    }
    public void deleteCustomer() {
        jsonHandler.deleteFile();
    }
    
    // validating fields
    public boolean isEmail(String email) {
        if (email.matches(EMAIL_REGEX)) {
            return true;
        }
        return false;
    }
    public boolean isPhoneNr(String phoneNr) {
        if (phoneNr.matches(PHONE_REGEX)) {
            return true;
        }
        return false;
    }
    public boolean isPostalCode(String postalCode) {
        if (postalCode.matches(POSTALCODE_REGEX)) {
            return true;
        }
        return false;
    }

    // updating fields
    public void setFirstName(String firstName) {
        customer.setFirstName(firstName);
    }
    public void setLastName(String lastName) {
        customer.setLastName(lastName);
    }
    public void setEmail(String email) {
        customer.setEmail(email);
    }
    public void setPhoneNr(String phoneNr) {
        customer.setPhoneNr(phoneNr);
    }
    public void setAddress(String address) {
        customer.setAddress(address);
    }
    public void setPostalCode(String postalCode) {
        customer.setPostalCode(postalCode);
    }
    public void setCity(String city) {
        customer.setCity(city);
    }
}
