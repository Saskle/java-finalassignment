package pojo;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

// ----------------- PURPOSE: Defining & validating Order data -----------------

public class Order {
    private int orderID;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime orderTime; // when the order is placed (invoice is printed)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime pickUpTime; // when order is ready for pickup
    private Customer customer;
    public Basket basket; // why not talk to the basket directly? or shall I make it independent and therefore have loose coupling?

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
        this.basket = basket;
    }
    

    public int getOrderID() {
        return this.orderID;
    }
    public void setOrderID(int id) {
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
        // TODO remove this so I can format it whenever I like in presentation?

        // protection when order is printed but no customer is entered yet
        String customer = "No customer";
        if (this.customer != null) {
            customer = this.customer.toString();
        }

        return "{" +
            " id='" + getOrderID() + "'" +
            ", customer='" + customer + "'" +
            ", products='" + basket + "'" +
            "}";
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
