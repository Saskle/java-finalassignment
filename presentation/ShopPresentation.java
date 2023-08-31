package presentation;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

import pojo.Customer; // remove this when json reader is implemented
import pojo.Order;
import pojo.Product;
import service.ShopService;

// ----------------- PURPOSE: Print application info to user, interpretet user input, start & close app -----------------

public class ShopPresentation {
 
    private Scanner scan = new Scanner(System.in);
    private ShopService shopService;

    public void startApp() {
        // intialize service
        shopService = new ShopService();

        System.out.println("\nWelcome to PhotoShop! ");
        System.out.println("Do you want to retrieve a previously saved order (1), or create a new order (2) ?");
        System.out.println("You can also close the application by entering 0.");
        System.out.print("Please enter the corresponding number of your choice: ");
        
        int response = validateInput(2); // we want either 0 or 1 or 2

        switch (response) {
            case 0: closeApp();
            case 1:
                // load in existing order (hardcode something for now)
                Order loadedOrder = new Order(4);
                loadedOrder.addProduct(new Product(1, "Paper 10 x 15 mat", new BigDecimal("1.40"), 1));
                loadedOrder.setCustomer(new Customer(1, "Saskia", "de Klerk", "saskle@calco.nl"));;

                System.out.println("The last saved order no. " + loadedOrder.getId() + " was made by " + loadedOrder.getCustomer().getFirstName() + " " + loadedOrder.getCustomer().getLastName());
                System.out.println("Please enter the order no. of the order you'd like to restore.");
                scan.nextLine(); // this one is eaten by nextInt();
                scan.nextLine();

                // pass dummyorder to shopservice
                shopService.setOrder(loadedOrder);
                showMainMenu();
                break;

            case 2: 
                System.out.println("Creating new order...");
                shopService.createOrder();
                showMainMenu();
                break;

            default: throw new InputMismatchException("Input at startApp() went wrong!");
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

        int menuChoice = validateInput(5);
        switch (menuChoice) {
            case 1: showProductCatalogue(); break;
            case 2: showCurrentOrder(); break;
            case 3: showCustomerData(); break;
            case 4: printInvoice(); break;
            case 5: closeApp(); break;
            default:
                System.out.println("Please enter a correct menu index."); // case 0 is not handled, so don't throw an exeception here
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

        System.out.println("Please enter the corresponding no. or name of the product to add to your order.");
        System.out.println("To return to the main menu, enter 0.");
        // TODO prompt for strings too (although that is not needed)

        int index = validateInput(shopService.productCatalog.length); // the range is the amount of products in the catalogue

        if (index == 0) {
            showMainMenu();
        }

        shopService.addProduct(index - 1); // product id's start at 1 instead of 0
        System.out.println("Product " + shopService.productCatalog[index - 1].getName() + " has been added."); // TODO print the name of the product instead

        System.out.println("Would you like to add another product? ");
        System.out.print("Enter 0 for no, 1 for yes: ");

        int response = validateInput(1);
        switch (response) {
            case 0: showMainMenu(); break;
            case 1: showProductCatalogue();
            default:
                throw new InputMismatchException("showProductCatalogue()'s switch case doesn't work properly");
        }

        showMainMenu();
    }
    
    public void printProductCatalogue() {
        // printing all products in catalogue, adding 1 to i so 0 is available for going back to main menu
        for (int i = 0; i < shopService.productCatalog.length; i++) {
            System.out.println((i + 1) + " - " + shopService.productCatalog[i]);
        }
    }

    public void showCurrentOrder() {
        System.out.println("CURRENT ORDER OVERVIEW");
        System.out.println(shopService.getOrder());
        System.out.println( "Would you like to: \n" +
                            "\t 1 - add new products to this order\n" +
                            "\t 2 - remove products from this order\n" + 
                            "\t 3 - change customer data\n" + 
                            "\t 0 - return to the Main Menu"); 
        System.out.print("Please enter the corresponding no. :");
        
        int response = validateInput(3);
        switch (response) {
            case 0: showMainMenu(); break;
            case 1: showProductCatalogue(); break;
            case 2: 
                System.out.println(shopService.getOrder()); // TODO create function to show only products
                System.out.print("Which product would you like to remove? "); // TODO prompt for strings / product names too
                int productIndex = validateInput(3); // lenght of order.products list
                shopService.removeProduct(productIndex);
                break;
            case 3: showCustomerData(); break;
            default: throw new InputMismatchException("showCurrentOrder()'s switch case doesn't work properly!");
        }

        showMainMenu();
    }
    
    public void showCustomerData() {
        System.out.println("CUSTOMER INFO");
        
        if (shopService.hasCustomer()) {
            System.out.println(shopService.getCustomer());
            System.out.println("Do you want to change this info?");
            System.out.print("Enter 0 for returning to the main menu, 1 for editing customer data: ");

            scan.nextLine(); // throwaway next line but doesn't work here ? (TODO)
            int response = validateInput(1);

            switch (response) {
                case 0: showMainMenu(); break;
                case 1: promptCustomerData();
                        showMainMenu();
                    break;
                default: throw new InputMismatchException("showCurrentOrder()'s switch case doesn't work properly!");
            }

        } else {
            System.out.println("No customer info as been added to this order yet.");
            promptCustomerData();
        }
        
        showMainMenu();
    }

    public void printInvoice() {
        System.out.println("CREATE INVOICE");

        // TODO check if there are actually products in the order

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

    public int validateInput(int range) {
        // first check if there is actually numberical input
        while (!scan.hasNextInt()) {
            System.out.println("You haven't entered a number. Please try again. ");
            scan.next();
        }
        int response = scan.nextInt();

        // if there is, is it within the range (inclusive) we want?
        if (response < 0 || response > range) {
            System.out.println("You haven't entered a number in the correct range. Please try again. ");
            response = validateInput(range);
        }
        return response;
    }
}
