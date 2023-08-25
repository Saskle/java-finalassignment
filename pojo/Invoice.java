package pojo;

import java.math.BigDecimal;

public class Invoice {
    private int id;
    private Order order;
    private BigDecimal totalcosts;



    public int getId() {
        return this.id;
    }
    public void setId(int id) { // random number
        this.id = id;
    }
    public Order getOrder() {
        return this.order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public BigDecimal getTotalcosts() {
        return this.totalcosts;
    }
    public void setTotalcosts(BigDecimal totalcosts) {
        this.totalcosts = totalcosts;
    }

}
