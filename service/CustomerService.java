package service;

import pojo.Customer;

// ----------------- PURPOSE: handling & validating customer data (current customer) -----------------

public class CustomerService extends Service {
    private Customer customer;

    public CustomerService() {
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
        } else {
            return true;
        }
    }

    public String showCustomer() {
        if (hasCustomer()) {
            return getCustomer().toString();
        } else {
            return "\tNo customer found.";
        }
    }

    // TODO adding other fields, validating their input
}
