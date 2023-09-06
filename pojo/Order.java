package pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

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
        Order order = new Order(this.id);
        if (this.hasCustomer()) {
            order.setCustomer(this.customer.clone());
        }
        
        if (!basket.getProducts().isEmpty()) {
            for (Map.Entry<Product, Integer> set : basket.getProducts().entrySet()) {
                order.basket.addProducts(null, id);
            } // TODO give basket a clone()
            order.basket.getProducts().forEach((product, quantity) -> order.basket.getProducts().put(product.clone(), quantity));
            order.basket.setTotalProductionHours(this.basket.getTotalProductionHours());
            order.basket.setTotalExpenses(this.basket.getTotalExpenses());
        }
        return order;
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

    
}
