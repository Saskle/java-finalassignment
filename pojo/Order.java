package pojo;

import java.time.LocalDateTime;
import java.util.Objects;

// ----------------- PURPOSE: Defining & validating Order data -----------------

public class Order {
    private int id;
    private LocalDateTime orderTime; // when the order is placed (invoice is printed)
    private LocalDateTime pickUpTime; // when order is ready for pickup
    private Customer customer;
    public Basket basket; // why not talk to the basket directly? or shall I make it independent and therefore have loose coupling?

    public Order(int id) {
        setId(id);
        this.basket = new Basket();
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
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
        return this.customer.clone();  
    }
    public void setCustomer(Customer customer) {
        this.customer = customer.clone();
    }
    public boolean hasCustomer() {
        return this.customer != null;
    }

    @Override
    public Order clone() { // deep copy of this order
        Order copy = new Order(this.id);
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
            " id='" + getId() + "'" +
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
        return id == order.id && Objects.equals(orderTime, order.orderTime) && Objects.equals(pickUpTime, order.pickUpTime) && Objects.equals(customer, order.customer) && Objects.equals(basket, order.basket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderTime, pickUpTime, customer, basket);
    }
}
