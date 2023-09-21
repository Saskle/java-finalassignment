package pojo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

// ----------------- PURPOSE: Defining & validating Order data -----------------

public class Order {
    private int orderID;
    private Customer customer;
    public Basket basket; 

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime orderTime; // when the order is placed (invoice is printed)

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime pickUpTime; // when order is ready for pickup
    
    public Order(int id) {
        setOrderID(id);
        this.basket = new Basket();
    }

    // all-argument constructor for Jackson's JSON reading and writing
    @JsonCreator
    public Order(   @JsonProperty("orderID") int orderID, 
                    @JsonProperty("orderTime") LocalDateTime orderTime, 
                    @JsonProperty("pickUpTime") LocalDateTime pickUpTime, 
                    @JsonProperty("customer") Customer customer, 
                    @JsonProperty("basket") Basket basket) {
        setOrderID(orderID);
        setOrderTime(orderTime);
        setPickupTime(pickUpTime);
        setCustomer(customer);
        setBasket(basket);
    }
    
    // GETTERS & SETTERS
    public int getOrderID() {
        return this.orderID;
    }
    public void setOrderID(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Order's ID cannot be 0 or negative.");
        }
        this.orderID = id;
    }
    public LocalDateTime getOrderTime() {
        return this.orderTime;
    }
    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }
    public LocalDateTime getPickUpTime() {
        return this.pickUpTime;
    }
    public void setPickupTime(LocalDateTime pickUpTime) {
        if (pickUpTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("An order's pick up time cannot lie in the past!");
        }
        this.pickUpTime = pickUpTime;
    }
    public Customer getCustomer() {
        if (hasCustomer()) {
            return this.customer.clone();  
        } else {
            return null;
        }
    }
    public void setCustomer(Customer customer) {
        this.customer = customer.clone();
    }
    public boolean hasCustomer() {
        return this.customer != null;
    }
    public void setBasket(Basket basket) {
        this.basket = basket.clone();
    }
    public Basket getBasket() {
        return this.basket;
    }

    @Override
    public Order clone() { // deep copy of this order
        Order copy = new Order(this.orderID);
        if (this.hasCustomer()) {
            copy.setCustomer(this.customer.clone());
        }
        copy.basket = this.basket.clone();
        return copy;
    }

    @Override // this is in fact the invoice
    public String toString() {
        // create a formatter so the date is formatted nicely
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd MMMM yyyy HH:mm");

        return  "\n***********************************            INVOICE            ***********************************\n" +
                "Order ID: \t\t" + getOrderID() + "\n" +
                "Order placed at:\t" + getOrderTime().format(formatter) + "\n\n" +
                "CUSTOMER \n" + customer + "\n" +
                "PRODUCTS \n" + 
                "\tProduct name \t\t\tPrice \t\tAmount \t\tSubtotal\n" + basket + "\n" +
                
                "Order can be picked up at:\t\t\t" + getPickUpTime().format(formatter) + "\n\n" +
                "Thank you for ordering at PhotoShop!\n" +
                "Don't forget to send your printing files mentioning the order ID to printing@photoshop.com.\n\n" +
                "****************************************************************************************************\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Order)) {
            return false;
        }
        Order order = (Order) o;
        return orderID == order.orderID && Objects.equals(orderTime, order.orderTime) && Objects.equals(pickUpTime, order.pickUpTime) && Objects.equals(customer, order.customer) && Objects.equals(basket, order.basket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, orderTime, pickUpTime, customer, basket);
    }
}
