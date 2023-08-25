package pojo;

import java.math.BigDecimal;

public class Product {
    private int id; 
    private String name;
    private BigDecimal price;
    private int creatingHours;


    // all this needs to be loaded in from .csv, so no argument checkers?
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getPrice() {
        return this.price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public int getCreatingHours() {
        return this.creatingHours;
    }
    public void setCreatingHours(int creatingHours) {
        this.creatingHours = creatingHours;
    }

}
