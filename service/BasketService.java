package service;

import pojo.Basket;
import pojo.Product;
import repository.BasketJSONhandler;


// ----------------- PURPOSE: handling products in basket -----------------

public class BasketService {
    private Basket basket;
    private ProductService productService;
    private OrderService orderService;
    private BasketJSONhandler jsonhandler;

    // constructor injection -> making sure all services work with the same instance
    public BasketService(OrderService orderService) {
        this.orderService = orderService;
        productService = new ProductService();
        jsonhandler = new BasketJSONhandler();        
    }

    public Basket getBasket() {
        return this.basket.clone();
    }
    public void setBasket(Basket basket) { // will need this for loading previous orders
        this.basket = basket.clone();
    }
    public void newBasket() {
        basket = new Basket();
    }

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

    public boolean hasProducts() {
        return !basket.getProducts().isEmpty();
    }
    public boolean hasProduct(Product product) {
        return basket.getProducts().containsKey(product);
    }

    // TODO remove this?
    public String showBasket() {
        return basket.toString();
    }

    public int basketSize() {
        return basket.getProducts().size();
    }

    public void passBasket() {
        orderService.setBasket(basket);
    }

    public void saveBasket() {
        jsonhandler.saveJSON(basket);
    }

    public void loadBasket() {
        basket = jsonhandler.readJSON();
    }

    public boolean hasBasket() {
        return jsonhandler.fileExists();
    }

    public void deleteBasket() {
        jsonhandler.deleteFile();
    }

}
