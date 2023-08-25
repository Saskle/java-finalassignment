package pojo;

import java.util.HashMap;

public class Order {
    private int id;
    private Customer customer;
    private HashMap<Integer, Product> products = new HashMap<>();


    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Customer getCustomer() {
        return this.customer.clone();
    }
    public void setCustomer(Customer customer) {
        this.customer = customer.clone();
    }

    public Product getProduct(int index) {
        // TODO
        return new Product();
    }

    public void setProduct(int index, Product product) {
        // TODO
    }

    public void deleteProduct(int index) {
        // TODO
    }
}
