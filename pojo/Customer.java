package pojo;

public class Customer {
    private int id; 
    private String firstName;
    private String lastName;
    private String address;
    private String postalCode;
    private String city;
    private String email;
    private String phoneNr; // I need a way to save the 0 in 0612345678, might check if it is actually a number tho

    // first name, last name and email are obligatory, rest is not
    public Customer(String firstName, String lastName, String email) {
        setId();
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    // constructor for the clone method
    // public Customer(Customer source) {
    //     setId(source.id);
    //     setFirstName(source.firstName);
    //     setLastName(source.lastName);
    //     setAddress(source.address);
    //     setPostalCode(source.postalCode);
    //     setCity(source.city);
    //     setEmail(source.email);
    //     setPhoneNr(source.phoneNr);
    // }


    public int getId() {
        return this.id;
    }
    public void setId() {
        // random Id generated between 1 - 10000 -> semi-unique (does it have to be unique tho?)
        this.id = (int) (Math.random() * 10000 + 1);
    }
    public void setId(int id) { //  overload for clone() method
        this.id = id;
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
        //if (address == null || address.isEmpty()) {
        //    throw new IllegalArgumentException("Customer's address cannot be set to null or empty.");
        //}
        this.address = address;
    }
    public String getPostalCode() {
        return this.postalCode;
    }
    public void setPostalCode(String postalCode) {
        //if (postalCode == null || postalCode.isEmpty()) {
        //    throw new IllegalArgumentException("Customer's postal code cannot be set to null or empty.");
        //}
        this.postalCode = postalCode;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        //if (city == null || city.isEmpty()) {
        //    throw new IllegalArgumentException("Customer's city cannot be set to null or empty.");
        //}
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
        //if (phoneNr == null || phoneNr.isEmpty()) { // set a maximum size (of digits) too?
        //    throw new IllegalArgumentException("Customer's phone number cannot be set to null or empty.");
        //}
        this.phoneNr = phoneNr;
    }

    @Override
    public Customer clone() {
        Customer customer = new Customer(this.firstName, this.lastName, this.email);
        customer.setId(this.id);
        customer.setAddress(this.address);
        customer.setPostalCode(this.postalCode);
        customer.setCity(this.city);
        customer.setPhoneNr(this.phoneNr);
        return customer;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", address='" + getAddress() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", city='" + getCity() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNr='" + getPhoneNr() + "'" +
            "}";
    }


    
}