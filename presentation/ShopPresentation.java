package presentation;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

import pojo.Customer; // remove this when json reader is implemented
import pojo.Order; // remove this when json reader is implemented
import pojo.Product; // remove this when json reader is implemented
import service.InvoiceService;
import service.OrderService;

// ----------------- PURPOSE: Print application info to user, interpretet user input, start & close app -----------------

public class ShopPresentation {
 
    private Scanner scan = new Scanner(System.in);
    private OrderService shopService;

    public void startApp() {
        // intialize service
        shopService = new OrderService();

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
                loadedOrder.basket.addProducts(new Product(1, "Paper 10 x 15 mat", new BigDecimal("1.40"), 1), 2);
                loadedOrder.setCustomer(new Customer(1, "Saskia", "de Klerk", "saskle@calco.nl"));;

                System.out.println("The last saved order no. " + loadedOrder.getId() + " was made by " + loadedOrder.getCustomer().getFirstName() + " " + loadedOrder.getCustomer().getLastName());
                System.out.println("Please enter the order no. of the order you'd like to restore.");
                scan.nextLine(); // this one is eaten by nextInt();
                scan.nextLine();

                // pass dummyorder to shopservice
                shopService.setOrder(loadedOrder);
                shopService.createCustomer("Saskia", "de Klerk", "saskle@calco.nl");
                showMainMenu();
                break;

            case 2: 
                System.out.println("Creating new order...");
                shopService.createOrder();
                showMainMenu();
                break;

            default: throw new InputMismatchException("Input for startApp() isn't correctly validated!");
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
            case 4: checkOut(); break;
            case 5: closeApp(); break;
            default:
                System.out.println("Please enter a correct menu index."); // case 0 is not handled, so don't throw an exeception here
                showMainMenu();
                break;
            }
        }

    public void printMainMenu() {
        System.out.print(   "1 - Product Catalog\n" +
                            "2 - View Current Order\n" +
                            "3 - Customer Information\n" + 
                            "4 - Create Invoice\n" + 
                            "5 - Close Application\n");
        System.out.print("Please enter the no. of the menu to proceed: ");
    }
    
    public void showProductCatalogue() {
        System.out.println("\nPRODUCT CATALOG");
        printProductCatalogue();

        System.out.println("Please enter the corresponding no. or name of the product to add to your order.");
        System.out.println("You can add multiple products at once by specifying id and quantity like (2:3).");
        System.out.println("To return to the main menu, enter 0.");
        // TODO prompt for strings too (although that is not needed)

        int index;
        if (scan.hasNextInt()) {
            // if the user enters numbers, validate and use that imput
            index = validateInput(shopService.productCatalog.length); // the range is the amount of products in the catalogue
            
            if (index == 0) {
            showMainMenu();
            }

            shopService.addProducts(index - 1, 1); // product id's start at 1 instead of 0
            System.out.println("Product " + shopService.productCatalog[index - 1].getName() + " has been added."); // TODO print the name of the product instead

        } else {
            // if the user enters a string, use that
            String productName = promptProductName();
            shopService.addProducts(productName, 1);
            System.out.println(productName + " has been added.");
        }

        

        
        System.out.println("Would you like to add another product? ");
        System.out.print("Enter 0 for no, 1 for yes: ");

        int response = validateInput(1);
        switch (response) {
            case 0: showMainMenu(); break;
            case 1: showProductCatalogue();
            default:
                throw new InputMismatchException("Input for showProductCatalogue() isn't correctly validated!");
        }

        showMainMenu();
    }
    
    public void printProductCatalogue() {
        System.out.println("ID \tName \t\t\t\tPrice");
        // printing all products in catalogue, adding 1 to i so 0 is available for going back to main menu
        for (int i = 0; i < shopService.productCatalog.length; i++) {
            System.out.println((i + 1) + ". " + shopService.productCatalog[i]);
        }
    }

    public void showCurrentOrder() {
        System.out.println("\nCURRENT ORDER OVERVIEW");
        System.out.println(shopService.showOrder());
        System.out.println( "Would you like to: \n" +
                            "\t 1 - add new products to this order\n" +
                            "\t 2 - remove products from this order\n" + 
                            "\t 3 - change customer data\n" + 
                            "\t 4 - place order and print invoice\n" + 
                            "\t 0 - return to the Main Menu"); 
        System.out.print("Please enter the corresponding no.: ");
        
        int response = validateInput(4);
        switch (response) {
            case 0: showMainMenu(); break;
            case 1: showProductCatalogue(); break;
            case 2: 
                System.out.println(shopService.showBasket());
                System.out.print("Which product would you like to remove? "); // TODO prompt for strings / product names too
                
                // if (scan.hasNextInt()) {
                //     validateInput(shopService.basketSize());
                //     // match index on right product
                // }

                String productName = promptProductName();
                shopService.removeProducts(productName, 1);
                System.out.println(productName + " has been removed.\n");
                showCurrentOrder();
                break;
            case 3: showCustomerData(); break;
            case 4: checkOut(); break;
            default: throw new InputMismatchException("Input for showCurrentOrder() isn't correctly validated!");
        }
        showMainMenu();
    }
    
    public void showCustomerData() {
        System.out.println("CUSTOMER INFO");
        
        if (shopService.hasCustomer()) {
            System.out.println(shopService.showCustomer());
            System.out.println("Do you want to change this info?");
            System.out.print("Enter 0 for returning to the main menu, 1 for editing customer data: ");

            scan.nextLine(); // throwaway next line but doesn't work here ? (TODO)
            int response = validateInput(1);

            switch (response) {
                case 0: showMainMenu(); break;
                case 1: promptCustomerData();
                        System.out.println("Customer data updated.");
                        showCustomerData();
                    break;
                default: throw new InputMismatchException("Input for showCurrentOrder() isn't correctly validated!");
            }

        } else {
            System.out.println("No customer info as been added to this order yet.");
            promptCustomerData();
        }
        
        showMainMenu();
    }

    public void checkOut() {
        System.out.println("CREATE INVOICE");

        if(!shopService.hasProducts()) {
            System.out.println("There are no projects in this order yet!");
            showProductCatalogue();
        }

        if (!shopService.hasCustomer()) {
            System.out.println("There is no customer data for this order yet!");
            promptCustomerData();
        }   
        InvoiceService invoiceService = new InvoiceService();
        invoiceService.createInvoice(shopService.getOrder());
        System.out.println("Total work hours for this order is: " + invoiceService.getTotalWorkHours());
        System.out.println(invoiceService.getInvoice());

        System.out.println("\nThank you for ordering at PhotoShop!");
        System.out.println("Don't forget to send your printing files mentioning the invoice nr. to printing@photoshop.com!\n");

        closeApp();
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

    public String promptProductName() {

        // as long as scan.next() is not a product name, keep asking
        // if it is a product name, return it as a string and let the menu decide what to do with it

        scan.nextLine(); // throwaway next line

        String productName = scan.nextLine();
        while (!shopService.isProduct(productName)) {
            System.out.println("You haven't entered a correct product name. Please try again. ");
            productName = scan.nextLine();
        }
        return productName;
    }

}
