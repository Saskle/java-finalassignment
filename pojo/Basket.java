package pojo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.Objects;

// ----------------- PURPOSE: Defining & validating Basket & products in basket's data -----------------

public class Basket {
    private HashMap<Product, Integer> products; // the HashMap holds products and their quantity
    private BigDecimal totalExpenses;
    private int totalProductionHours;

    public Basket() {
        // upon creating a new order, initialize the HashMap and set expenses and production hours to zero
        this.products = new HashMap<>();
        this.totalExpenses = BigDecimal.ZERO;
        this.totalProductionHours = 0;
    }

    // TODO update these correctly (or check if I actually need them)
    public HashMap<Product,Integer> getProducts() {
        return this.products;
    }
    private void setProducts(HashMap<Product,Integer> products) {
        for (Map.Entry<Product, Integer> set : products.entrySet()) {
            this.products.put(set.getKey().clone(), set.getValue());
        }
    }
    public BigDecimal getTotalExpenses() {
        return this.totalExpenses;
    }
    private void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
    public int getTotalProductionHours() {
        return this.totalProductionHours;
    }
    private void setTotalProductionHours(int totalProductionHours) {
        this.totalProductionHours = totalProductionHours;
    }

    private void updateTotalExpenses() {
        // defining a function to map the stream on: multiply each product's price with it's stored quantity in the HashMap
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
            // guarantee that quantity never becomes negative
            if (products.get(product) <= 0) {
                products.remove(product);
            }
        } else {
            throw new IllegalArgumentException(product.getName() + " is not in the basket!");
        }
        updateProductionHours();
        updateTotalExpenses();
    }

    private String productsToString() {
        String productsAsString = "";

        // for every product in the HashMap, concat the string with the product.toString(), quantity and price subtotal
        for (Map.Entry<Product, Integer> set : products.entrySet()) {
            BigDecimal productSubTotal = set.getKey().getPrice().multiply(new BigDecimal(set.getValue()));
            productsAsString = productsAsString.concat(set.getKey() + "\t" + set.getValue() + "\t" + productSubTotal + "\n");
        }
        return productsAsString;
    }

    @Override
    public String toString() {
        return productsToString() +
            "\t\t\t\t Total Expenses: \t" + getTotalExpenses() + "\n" +
            "\t\t\t\t Total Production Hours: \t" + getTotalProductionHours() + "\n";
    }

    @Override
    public Basket clone() {
        Basket copy = new Basket();
        copy.setTotalExpenses(this.totalExpenses);
        copy.setTotalProductionHours(this.totalProductionHours);
        copy.setProducts(products);
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Basket)) {
            return false;
        }
        Basket basket = (Basket) o;
        return Objects.equals(products, basket.products) && Objects.equals(totalExpenses, basket.totalExpenses) && totalProductionHours == basket.totalProductionHours;
    }

    @Override
    public int hashCode() {
        return Objects.hash(products, totalExpenses, totalProductionHours);
    }
    
}
