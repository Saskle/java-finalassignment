package pojo;

import java.util.ArrayList;

// ----------------- PURPOSE: Handling & validating Order data -----------------

public class Order {
    private int id;
    private Customer customer;
    private ArrayList<Product> products;

    public Order(int id) {
        setId(id);
        this.products = new ArrayList<>();
    }

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
    public boolean hasCustomer() {
        return this.customer != null;
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> allProducts = new ArrayList<>();
        for (Product product : this.products) {
            allProducts.add(product);
        }
        return allProducts;
    }
    
    public Product getProduct(int index) {
        return products.get(index).clone();
    }

    public void addProduct(Product product) {
        products.add(product.clone());
    }

    public void deleteProduct(int index) {
        products.remove(index);
    }

    @Override
    public Order clone() { // deep copy of this order
        Order order = new Order(this.id);
        if (order.hasCustomer()) {
            order.setCustomer(this.customer.clone());
        }
        
        for (Product product : products) {
            order.products.add(product.clone());
        }
        return order;
    }

    @Override
    public String toString() {
        // TODO decide how much formatting is here VS in invoice
        String productsToString = "";
        for (Product product : products) {
            productsToString = productsToString.concat(product.getName() + ", ");
        }

        // protection when order is printed but no customer is entered yet
        String customer = "No customer";
        if (this.customer != null) {
            customer = this.customer.toString();
        }

        return "{" +
            " id='" + getId() + "'" +
            ", customer='" + customer + "'" +
            ", products='" + productsToString + "'" +
            "}";
    }

    
}
