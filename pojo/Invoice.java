package pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// ----------------- PURPOSE: Storing order data for invoices -----------------

public class Invoice {
    private int id;
    private Order order;
    private BigDecimal totalCosts;
    private int totalWorkHours;
    private LocalDateTime orderTime;
    private LocalDateTime pickUpTime;


    public Invoice(int id, Order order, LocalDateTime pickUpTime) {
        setId(id);
        setOrder(order);
        setTotalcosts();
        setOrderTime();
        setPickUpTime(pickUpTime);
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) { 
        // OR take order.id and add a string prefix to it, like 'INV2032' ?
        this.id = id;
    }
    public Order getOrder() {
        return this.order.clone();
    }
    public void setOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("An invoice's order cannot be null.");
        }
        this.order = order.clone();
    }

    public BigDecimal getTotalCosts() {
        return this.totalCosts;
    }
    public void setTotalcosts() { 
        this.totalCosts = order.basket.getTotalExpenses();
    }
    public void setTotalCosts(BigDecimal totalcosts) { //overload for setting directly (delete if unwanted!)
        this.totalCosts = totalcosts;
    }

    public int getTotalWorkHours() {
        return this.totalWorkHours;
    }
    public void setTotalWorkHours(int totalWorkHours) {
        if (totalWorkHours <= 0) {
            throw new IllegalArgumentException("An invoice's total work hours can't be 0 or less!");
        }
        this.totalWorkHours = totalWorkHours;
    }

    public LocalDateTime getOrderTime() {
        return this.orderTime;
    }
    public void setOrderTime() {
        this.orderTime = LocalDateTime.now();
    }

    public LocalDateTime getPickUpTime() {
        return this.pickUpTime;
    }
    public void setPickUpTime(LocalDateTime pickUpTime) {
        if (pickUpTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("An invoice's pick up time cannot lie in the past!");
        }
        this.pickUpTime = pickUpTime;
    }

    // format this to a nice invoice
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd MMMM yyyy HH:mm");
        return "{" +
            " id='" + getId() + "'" +
            ", order='" + getOrder() + "'" +
            ", total costs='" + getTotalCosts() + "'" +
            ", total production hours='" + getTotalWorkHours() + "'" +
            ", completionTime='" + getPickUpTime().format(formatter) + "'" +
            "}";
    }


}
