package pojo;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// ----------------- PURPOSE: Defining & validating Customer data -----------------

public class Customer {
    private int customerID; 
    private String firstName;
    private String lastName;
    private String address;
    private String postalCode;
    private String city;
    private String email;
    private String phoneNr; 

    // first name, last name and email are obligatory, rest is not -> argument validation in service layer
    public Customer(int id, String firstName, String lastName, String email) {
        setCustomerID(id);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    // all-argument constructor for Jackson's JSON reading and writing
    @JsonCreator
    public Customer(    @JsonProperty("customerID") int customerID, 
                        @JsonProperty("firstName") String firstName, 
                        @JsonProperty("lastName") String lastName, 
                        @JsonProperty("address") String address, 
                        @JsonProperty("postalCode") String postalCode, 
                        @JsonProperty("city") String city, 
                        @JsonProperty("email") String email, 
                        @JsonProperty("phoneNr") String phoneNr) {
        setCustomerID(customerID);
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setPostalCode(postalCode);
        setCity(city);
        setEmail(email);
        setPhoneNr(phoneNr);
    }

    // GETTERS & SETTERS
    public int getCustomerID() {
        return this.customerID;
    }
    private void setCustomerID(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Customer's ID cannot be 0 or negative.");
        }
        this.customerID = id;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("Customer's first name cannot be null or empty.");
        }
        this.firstName = firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Customer's last name cannot be set to null or empty.");
        }
        this.lastName = lastName;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPostalCode() {
        return this.postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Customer's email cannot be set to null or empty.");
        }
        this.email = email;
    }
    public String getPhoneNr() {
        return this.phoneNr;
    }
    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    @Override
    public Customer clone() {
        Customer copy = new Customer(this.customerID, this.firstName, this.lastName, this.email);
        copy.setAddress(this.address);
        copy.setPostalCode(this.postalCode);
        copy.setCity(this.city);
        copy.setPhoneNr(this.phoneNr);
        return copy;
    }

    @Override
    public String toString() {
        return 
            "\tCustomer ID: \t" + getCustomerID() + "\n" +
            "\tName: \t\t" + getFirstName() + " " + getLastName() + "\n" +
            "\tAddress: \t" + getAddress() + "\n" +
            "\tPostal code: \t" + getPostalCode() + "\n" +
            "\tCity: \t\t" + getCity() + "\n" +
            "\tEmail: \t\t" + getEmail() + "\n" +
            "\tPhone Nr.: \t" + getPhoneNr() + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Customer)) {
            return false;
        }
        Customer customer = (Customer) o;
        return customerID == customer.customerID && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(address, customer.address) && Objects.equals(postalCode, customer.postalCode) && Objects.equals(city, customer.city) && Objects.equals(email, customer.email) && Objects.equals(phoneNr, customer.phoneNr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerID, firstName, lastName, address, postalCode, city, email, phoneNr);
    }    
}
