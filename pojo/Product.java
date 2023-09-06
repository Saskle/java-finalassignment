package pojo;

import java.math.BigDecimal;

// ----------------- PURPOSE: Handling & validating Product data -----------------

public class Product {
    private int id; 
    private String name;
    private BigDecimal price;
    private int creatingHours;

    // might remove this later, need it for testing order / invoice setting
    public Product(int id, String name, BigDecimal price, int creatingHours) {
        setId(id);
        setName(name);
        setPrice(price);
        setCreatingHours(creatingHours);
    }

    // all this needs to be loaded in from .csv, so no argument checkers?
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Product's id cannot be negative.");
        }
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product's name cannot be null or empty.");   
        }
        this.name = name;
    }
    public BigDecimal getPrice() {
        return this.price;
    }
    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(new BigDecimal("0")) < 0) { 
            throw new IllegalArgumentException("Product's price cannot be negative or null.");
        }
        this.price = price;
    }
    public int getCreatingHours() {
        return this.creatingHours;
    }
    public void setCreatingHours(int creatingHours) {
        if (creatingHours < 0) {
            throw new IllegalArgumentException("Product's creating hours cannot be negative.");
        }
        this.creatingHours = creatingHours;
    }

    @Override
    public Product clone() {
        return new Product(this.id, this.name, this.price, this.creatingHours);
    }

    @Override
    public String toString() {
        return "\t" + getName() + "\t\t" + getPrice() + "\t\t";
    }

}
