package pojo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;

// ----------------- PURPOSE: Defining & validating Basket & products in basket's data -----------------

public class Basket {
    HashMap<Product, Integer> products; // the HashMap holds products and their quantity
    BigDecimal totalExpenses;
    int totalProductionHours;

    public Basket() {
        // upon creating a new order, initialize the HashMap and set expenses and production hours to zero
        this.products = new HashMap<>();
        this.totalExpenses = BigDecimal.ZERO;
        this.totalProductionHours = 0;
    }

    // TODO update these correctly
    public HashMap<Product,Integer> getProducts() {
        return this.products;
    }
    public BigDecimal getTotalExpenses() {
        return this.totalExpenses;
    }
    public int getTotalProductionHours() {
        return this.totalProductionHours;
    }

    private void updateTotalExpenses() {
        // defining a function to map the stream on (BigDecimal is immutable)
        Function<Product, BigDecimal> mapper = product -> product.getPrice().multiply(new BigDecimal(products.get(product)));
        
        // create a set out of our products HashMap so we can use stream()
        Set<Product> productSet = products.keySet();

        // go through our products, map them by the function and add them in a new BigDecimal which we return to totalExpenses
        totalExpenses = productSet.stream()
                                    .map(mapper)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void updateProductionHours() {
        // loop through products, multiply their production hours with their quantity and add to this basket's total
        products.forEach((product, quantity) -> totalProductionHours += product.getCreatingHours() * quantity);
    }

    // TODO test if this works properly, if products are deemed equal like this
    public void addProducts(Product product, int quantity) {
        if (this.products.containsKey(product)) {
            this.products.put(product.clone(), this.products.get(product) + quantity);
        } else {
            products.put(product.clone(), quantity);
        }
        updateProductionHours();
        updateTotalExpenses();
    }

    public void removeProducts(Product product, int quantity) {
        if (products.containsKey(product)) {
            this.products.put(product.clone(), this.products.get(product) - quantity);
            if (products.get(product) >= 0) {
                products.remove(product);
            }
        } else {
            throw new IllegalArgumentException(product + " is not in the basket!");
        }
        updateProductionHours();
        updateTotalExpenses();
    }

    @Override
    public String toString() {
        return getProducts() + "\n" +
            ", totalExpenses='" + getTotalExpenses() + "'\n" +
            ", totalProductionHours='" + getTotalProductionHours() + "'\n";
    }


}
