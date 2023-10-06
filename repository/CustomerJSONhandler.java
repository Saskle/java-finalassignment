package repository;

import java.io.File;
import java.io.IOException;

import pojo.Customer;

// ----------------- PURPOSE: reading & writing current Customer to JSON -----------------

public class CustomerJSONhandler extends JSONhandler<Customer> {

    public CustomerJSONhandler() {
        // set the file path on initialisation
        file = new File("data//current_customer.json");
    }
    
    @Override
    public void saveJSON(Customer customer) {
        try {
            mapper.writeValue(file, customer);
        } catch (IOException exception) {
            System.out.println(exception);
        } 
    }
    
    @Override
    public Customer readJSON() {
        try {
            return mapper.readValue(file, Customer.class);
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return null; // this should be handled by the service class calling readJSON()
    }

    public boolean fileExists() {
        return file.exists();
    }

    public void deleteFile() {
        file.delete();
    }
}
