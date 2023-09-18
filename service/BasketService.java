package service;

import pojo.Basket;
import pojo.Product;


// ----------------- PURPOSE: handling products in basket -----------------

public class BasketService {
    private Basket basket;
    private ProductService productService;

    public BasketService() {
        productService = new ProductService();
        basket = new Basket();
    }


    public Basket getBasket() {
        return this.basket.clone();
    }
    public void setBasket(Basket basket) { // will need this for loading previous orders
        this.basket = basket.clone();
    }
    public ProductService getProductService() {
        return this.productService;
    }
    public void setProductService(ProductService productService) {
        this.productService = productService;
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

    public String showBasket() {
        return basket.toString();
    }

    public int basketSize() {
        return basket.getProducts().size();
    }


}
