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

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd MMMM yyyy HH:mm");

        // protection when order is printed but no customer is entered yet
        // TODO this could be removed probably
        String customer = "No customer";
        if (this.customer != null) {
            customer = this.customer.toString();
        }

        return "Order id:\t" + getOrderID() + "\n" +
            "Customer:\n" + customer + "\n" +
            "Products: " + basket + "\n" +
            "Order placed at:\t\t" + getOrderTime().format(formatter) + "\n" +
            "Order can be picked up at:\t" + getPickUpTime().format(formatter);
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
