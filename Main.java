import java.math.BigDecimal;

import pojo.Basket;
import pojo.Invoice;
import pojo.Product;
import presentation.*;
import service.OrderService;

public class Main {
    public static void main(String[] args) {
        
        Product photo1 = new Product(1, "Paper 10 x 15 mat", new BigDecimal("1.40"), 1);
        Product photo2 = new Product(2, "Paper 10 x 15 high gloss", new BigDecimal("1.50"), 1);
        Product photo3 = new Product(3, "Paper 30 x 40 mat", new BigDecimal("4.50"), 2);
        //Product photo4 = new Product(4, "Paper 30 x 40 high gloss", new BigDecimal("5.00"), 2);
        //Product photo5 = new Product(5, "Canvas 30 x 40 mat", new BigDecimal("24.00"), 12);
        //Product photo6 = new Product(6, "Canvas 30 x 40 high gloss", new BigDecimal("27.50"), 2);
        //Product photo7 = new Product(7, "Canvas 100 x 150 mat", new BigDecimal("64.75"), 16);
        //Product photo8 = new Product(8, "Canvas 100 x 150 high gloss", new BigDecimal("72.50"), 16);
        //Product photo9 = new Product(9, "Glass 30 x 40 mat", new BigDecimal("27.50"), 14);
        //Product photo10 = new Product(10, "Glass 30 x 40 high gloss", new BigDecimal("27.50"), 14);
        //Product photo11 = new Product(11, "Glass 100 x 150 mat", new BigDecimal("82.50"), 20);
        //Product photo12 = new Product(12, "Glass 100 x 150 high gloss", new BigDecimal("82.50"), 20);

        ShopPresentation shopPresentation = new ShopPresentation();
        shopPresentation.startApp();

        // look into this for splitting Scanner input on ":"
        // https://stackoverflow.com/questions/41473861/how-do-i-split-user-input-from-the-console
        // https://stackoverflow.com/questions/3481828/how-do-i-split-a-string-in-java 

        // Scanner scan = new Scanner(System.in);
        // OrderService shopService = new OrderService();
        // shopService.createOrder();

        // System.out.print("Please enter the products to add: ");
        // String response = scan.nextLine();


        // user input can vary from 1, 1:1, productname : 3 and just productname

        // check first if there's an : 
        // if there is, split and trim
        // then, is it an integer or not (regex for validation?)
        // if it is -> validate range and add product + quantity if there was a :
        // if it isn't -> validate if it's a product name and if it isn't, keep asking, otherwise add product
        // there has to be an extra check if the user decided to type a number the second try

    //     while (!scan.hasNext()) {
    //         if (response.contains(":")) {
    //             //response = scan.nextLine();
    //             String[] responseParts = response.split(":");

    //             // TODO trim out spaces?
    //             System.out.println(responseParts[0]);
    //             System.out.println(responseParts[1]);

    //             // check if input is numerical or not (isNumber is faster)
    //             if (responseParts[0].matches("[0-9]+")) {
    //                 if (isInteger(responseParts[1])) {
    //                     int product = Integer.parseInt(responseParts[0]);
    //                     int quantity = Integer.parseInt(responseParts[1]);
    //                     shopService.addProducts(product, quantity);
    //                     System.out.println("Product has been added to the shopping cart.");
    //                     System.out.println(shopService.showBasket());
    //                 } else {
    //                     // ask for input again (?)
    //                     System.out.print("Please enter valid input.");
    //                 }
    //             } else if (shopService.isProduct(responseParts[0])) {
    //                 if (isInteger(responseParts[1])) {
    //                     int quantity = Integer.parseInt(responseParts[1]);
    //                     shopService.addProducts(responseParts[0], quantity);
    //                     System.out.println("Product has been added to the shopping cart.");
    //                     System.out.println(shopService.showBasket());
    //                 } else {
    //                     // ask for input again (?)
    //                     System.out.print("Please enter valid input.");
    //                 }
    //             }

    //         } else {
    //             if (isInteger(response)) {
    //                 int product = Integer.parseInt(response);
    //                 shopService.addProducts(product, 1);
    //                 System.out.println("Product has been added to the shopping cart.");
    //                 System.out.println(shopService.showBasket());
    //             } else if (shopService.isProduct(response)) {
    //                 shopService.addProducts(response, 1);
    //                 System.out.println("Product has been added to the shopping cart.");
    //                 System.out.println(shopService.showBasket());
    //             }
    //         }
    //     }
    //     scan.close();
    }

    public static boolean isInteger(String text){
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {}
        return false;
    }
}
