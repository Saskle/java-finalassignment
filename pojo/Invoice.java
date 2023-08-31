package pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

// ----------------- PURPOSE: Storing order data for printing invoices -----------------

public class Invoice {
    private int id;
    private Order order;
    private BigDecimal totalcosts;
    private LocalDateTime completionTime;
    private OpeningHours openingHours;


    public Invoice(int id, Order order) {
        setId(id);
        setOrder(order);
        setTotalcosts();
        setCompletionTime();
        this.openingHours = new OpeningHours(order);
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

    public BigDecimal getTotalcosts() {
        return this.totalcosts;
    }
    public void setTotalcosts() { 
        BigDecimal totalcosts = new BigDecimal("0");
        for (int i = 0; i < order.getAllProducts().size(); i++) {
            totalcosts = totalcosts.add(order.getProduct(i).getPrice());
        }
        this.totalcosts = totalcosts;
    }
    public void setTotalcosts(BigDecimal totalcosts) { //overload for setting directly (delete if unwanted!)
        this.totalcosts = totalcosts;
    }

    public LocalDateTime getCompletionTime() {
        return this.completionTime;
    }
    public void setCompletionTime() {
        int totalWorkHours = 0;
        for (int i = 0; i < order.getAllProducts().size(); i++) {
            totalWorkHours += order.getProduct(i).getCreatingHours();
        }

        // TODO: make this compliant with opening hours
        LocalDateTime now = LocalDateTime.now();
        this.completionTime = now.plusHours((long)totalWorkHours);
    }

    // format this to a nice invoice
    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", order='" + getOrder() + "'" +
            ", totalcosts='" + getTotalcosts() + "'" +
            ", completionTime='" + getCompletionTime() + "'" +
            "}";
    }


}
