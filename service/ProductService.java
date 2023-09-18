package service;

import java.nio.file.Path;
import java.nio.file.Paths;

import pojo.Product;
import repository.PriceListCSVhandler;

// ----------------- PURPOSE: initializing product catalogue and providing access to it -----------------

public class ProductService {
    public final Product[] catalogue; // read-only
    private PriceListCSVhandler csvHandler;
    private final static Path productsPath = Paths.get("data\\PhotoShop_PriceList.csv");

    public ProductService () {
        // upon intialisation, create product catalog string to fill with all products
        this.csvHandler = new PriceListCSVhandler(productsPath);
        catalogue = this.csvHandler.readCSV();
    }

    public Product getProduct(String name) {
        for (int i = 0; i < catalogue.length; i++) {
            if (catalogue[i].getName().equals(name)) {
                return catalogue[i].clone();
            }
        }
        return null; // TODO not sure if this is right (maybe throw an exception?)
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
