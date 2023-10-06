package service;

import pojo.Basket;
import pojo.Product;
import repository.BasketJSONhandler;


// ----------------- PURPOSE: handling products in basket -----------------

public class BasketService {
    private Basket basket;
    private ProductService productService;
    private OrderService orderService;
    private BasketJSONhandler jsonHandler;

    // constructor injection -> making sure all services work with the same instance of Orderservice
    public BasketService(OrderService orderService) {
        this.orderService = orderService;
        productService = new ProductService();
        jsonHandler = new BasketJSONhandler(); 
        
        // basketservice always has a basket, which will be overriden if the user wants to restore the one saved in JSON
        basket = new Basket();    
    }

    // adding and removing products to basket
    public void addProducts(int id, int quantity) {
        basket.addProducts(productService.catalogue[id].clone(), quantity);
    }
    public void addProducts(String name, int quantity) {
        Product product = productService.getProduct(name); // is already returning a clone
        basket.addProducts(product, quantity);
    }
    public void removeProducts(int id, int quantity) {
        basket.removeProducts(productService.catalogue[id], quantity);
    }
    public void removeProducts(String name, int quantity) { 
        Product product = productService.getProduct(name);
        basket.removeProducts(product, quantity);
    }
    public boolean isEmpty() {
        return basket.getProducts().isEmpty();
    }
    public boolean hasProduct(Product product) {
        return basket.getProducts().containsKey(product);
    }

    // printing the basket to the user
    public String showBasket() {
        return basket.toString();
    }

    // passing the basket to Orderservice
    public void basketToOrder() {
        orderService.setBasket(basket);
    }

    // saving and loading from JSON
    public void saveBasket() {
        try {
            jsonHandler.saveJSON(basket);
        } catch (Exception exception) {
            System.out.println("There is no customer data to save to JSON: " + exception);
        }
    }
    public void loadBasket() {
        try {
            basket = jsonHandler.readJSON();
        } catch (NullPointerException exception) {
            System.out.println("Basket could not be deserialized: " + exception);
        }
    }
    public boolean hasSavedBasket() {
        return jsonHandler.fileExists();
    }
    public void deleteBasket() {
        jsonHandler.deleteFile();
    }

}
