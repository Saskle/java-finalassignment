package service;

import pojo.Product;
import repository.PriceListCSVreader;

// ----------------- PURPOSE: initializing product catalogue and providing access to it -----------------

public class ProductService {
    public final Product[] catalogue; // read-only
    private PriceListCSVreader csvHandler;
    
    public ProductService () {
        // upon intialisation, create product catalog string to fill with all products
        this.csvHandler = new PriceListCSVreader();
        catalogue = this.csvHandler.readCSV();
    }

    public Product getProduct(String name) {
        for (int i = 0; i < catalogue.length; i++) {
            if (catalogue[i].getName().equals(name)) {
                return catalogue[i].clone();
            }
        }
        return null; // client's responsibility to check if it is a product beforehand
    }

    public int getProductIndex(String name) {
        for (int i = 0; i < catalogue.length; i++) {
            if (catalogue[i].getName().equals(name)) {
                return i;
            }
        }
        return 0; // client's responsibility to check if it is a product beforehand
    }

    public boolean isProduct(String name) {
        for (int i = 0; i < catalogue.length; i++) {
            if (catalogue[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }





}
