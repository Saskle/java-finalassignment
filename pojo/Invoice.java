package pojo;

import java.math.BigDecimal;

public class Invoice {
    private int id;
    private Order order;
    private BigDecimal totalcosts;


    public Invoice(int id, Order order) {
        setId(id);
        setOrder(order);
        // for each product in order.products, totalcosts.add(product.getPrice());
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) { 
        // TODO: generate random number
        // or add 20230000 to it to create a 'realistic' invoice number?
        // OR take order.id and add a string prefix to it, like 'INV2032'
        this.id = id;
    }
    public Order getOrder() {
        return this.order.clone();
    }
    public void setOrder(Order order) {
        this.order = order.clone();
    }
    public BigDecimal getTotalcosts() {
        return this.totalcosts;
    }
    public void setTotalcosts(BigDecimal totalcosts) {
        this.totalcosts = totalcosts;
    }

    // format this to a nice invoice
    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", order='" + getOrder() + "'" +
            ", totalcosts='" + getTotalcosts() + "'" +
            "}";
    }


}
