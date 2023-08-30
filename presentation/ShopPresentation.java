package presentation;

import java.math.BigDecimal;
import java.util.Scanner;

import pojo.Customer; // remove this when json reader is implemented
import pojo.Order;
import pojo.Product;
import service.ShopService;

// ----------------- PURPOSE: Print application info to user, interpretet user input, start & close app -----------------

public class ShopPresentation {

    // create a global promptuserchoice function to check, with parameters as int range? 
    
    private Scanner scan = new Scanner(System.in);
    private ShopService shopService;

    public void startApp() {
        // intialize service
        shopService = new ShopService();

        System.out.println("\nWelcome to PhotoShop! ");
        System.out.println("Do you want to retrieve a previously saved order (1), or create a new order (2) ?");
        System.out.print("Please enter the corresponding number of your choice: ");
        
        while (!scan.hasNextInt()) {
            System.out.println("You haven't entered a number. ");
            scan.next();
        }
        int response = scan.nextInt();

        switch (response) {
            case 1:
                // load in existing order (hardcode something for now)
                Order loadedOrder = new Order(4);
                loadedOrder.addProduct(new Product(1, "Paper 10 x 15 mat", new BigDecimal("1.40"), 1));
                loadedOrder.setCustomer(new Customer(1, "Saskia", "de Klerk", "saskle@calco.nl"));;

                System.out.println("The last saved order no. " + loadedOrder.getId() + " was made by " + loadedOrder.getCustomer().getFirstName() + " " + loadedOrder.getCustomer().getLastName());
                System.out.println("Please enter the order no. of the order you'd like to restore.");
                scan.nextLine();

                // pass dummyorder to shopservice
                shopService.setOrder(loadedOrder);
                showMainMenu();
                break;

            case 2: 
                // new order
                shopService.createOrder();

                showMainMenu();
                break;

            default: // TODO create a default state here
                break;
        }
    }

    public void closeApp() {
        scan.close();
        System.out.println("Application closing.");
        // saveOrder();
        System.exit(0);
    }

    public void showMainMenu() {
        System.out.println("\nMAIN MENU");
        printMainMenu();

        // don't repeat yourself!
        while (!scan.hasNextInt()) {
            System.out.println("You haven't entered a number! ");
            scan.next();
            printMainMenu();
        }
        int menuChoice = scan.nextInt(); 

        switch (menuChoice) {
            case 1:
                showProductCatalogue(); break;
            case 2: 
                showCurrentOrder(); break;
            case 3: 
                showCustomerData(); break;
            case 4:
                printInvoice(); break;
            case 5:
                closeApp();
                break;
            default:
                System.out.println("Please enter a correct menu index.");
                showMainMenu();
                break;
            }
        }

    public void printMainMenu() {
        System.out.print("1 - Product Catalog\n2 - View Current Order\n3 - Customer Information\n4 - Create Invoice\n5 - Close Application\n");
        System.out.print("Please enter the no. of the menu to proceed: ");
    }
    public void showProductCatalogue() {
        System.out.println("PRODUCT CATALOG");

        printProductCatalogue();
        
        while (!scan.hasNextInt()) {
            System.out.println("You haven't entered a number! ");
            scan.next();
        }

        int index = scan.nextInt();
        shopService.addProduct(index);
        System.out.println("Product X has been added.");

        // again show products, prompt for new products to add?

        showMainMenu();
    }
    
    public void printProductCatalogue() {
        for (int i = 0; i < shopService.productCatalog.length; i++) {
            System.out.println(i + " - " + shopService.productCatalog[i]);
        }
        System.out.println("Please pick a product to add to your order.");
    }

    public void showCurrentOrder() {
        System.out.println("CURRENT ORDER OVERVIEW");
        System.out.println(shopService.getOrder());
        showMainMenu();
    }
    
    public void showCustomerData() {
        System.out.println("CUSTOMER INFO");
        
        if (shopService.hasCustomer()) {
            System.out.println(shopService.getCustomer());
            System.out.println("Do you want to change this info?");
            scan.nextLine(); // throwaway next line but doesn't work here (TODO)
            String response = scan.nextLine();

            if (response.equals("yes")) {
                promptCustomerData();
                showMainMenu();

            } else {
                showMainMenu();
            }

        } else {
            System.out.println("No customer info as been added to this order yet.");
            
        }

        promptCustomerData();

        showMainMenu();
    }

    public void printInvoice() {
        System.out.println("CREATE INVOICE");

                if (!shopService.hasCustomer()) {
                    promptCustomerData();
                }               
                shopService.createInvoice();
                System.out.println(shopService.getInvoice());

                showMainMenu();
    }

    public void promptCustomerData() {
        scan.nextLine(); // throwaway line for nextInt()
        System.out.print("Please add your first name: ");
        String firstName = scan.nextLine();
        System.out.print("Please add your last name: ");
        String lastName = scan.nextLine();
        System.out.print("Please add your email address: ");
        String email = scan.nextLine();

        shopService.createCustomer(firstName, lastName, email);
    }
}
