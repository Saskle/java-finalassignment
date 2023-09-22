package pojo;

import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// ----------------- PURPOSE: Defining & validating Product data -----------------

public class Product {
    private int productID; 
    private String name;
    private BigDecimal price;
    private int creatingHours;

    // all-argument constructor for Jackson's JSON reading and writing
    @JsonCreator
    public Product( @JsonProperty("productID") int productID, 
                    @JsonProperty("name") String name, 
                    @JsonProperty("price") BigDecimal price, 
                    @JsonProperty("creatingHours") int creatingHours) {
        setProductID(productID);
        setName(name);
        setPrice(price);
        setCreatingHours(creatingHours);
    }

    // GETTERS & SETTERS
    public int getProductID() {
        return this.productID;
    }
    private void setProductID(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Product's id cannot be 0 or negative.");
        }
        this.productID = id;
    }
    public String getName() {
        return this.name;
    }
    private void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product's name cannot be null or empty.");   
        }
        this.name = name;
    }
    public BigDecimal getPrice() {
        return this.price;
    }
    private void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(new BigDecimal("0")) < 0) { 
            throw new IllegalArgumentException("Product's price cannot be negative or null.");
        }
        this.price = price;
    }
    public int getCreatingHours() {
        return this.creatingHours;
    }
    private void setCreatingHours(int creatingHours) {
        if (creatingHours < 0) {
            throw new IllegalArgumentException("Product's creating hours cannot be negative.");
        }
        this.creatingHours = creatingHours;
    }

    @Override
    public Product clone() {
        return new Product(this.productID, this.name, this.price, this.creatingHours);
    }

    @Override
    public String toString() {
        String nameTabulation = "\t\t";
        if (getName().length() > 22) {
            nameTabulation = "\t";
        }
        return "\t" + getName() + nameTabulation + getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        return productID == product.productID && Objects.equals(name, product.name) && Objects.equals(price, product.price) && creatingHours == product.creatingHours;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, name, price, creatingHours);
    }
}
