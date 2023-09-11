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
        
        int response = validateNumericalInput(2); // we want either 0 or 1 or 2

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

        int menuChoice = validateNumericalInput(5);
        switch (menuChoice) {
            case 1: showProductCatalogue();
            case 2: showCurrentOrder();
            case 3: showCustomerData();
            case 4: checkOut();
            case 5: closeApp();
            default:
                System.out.println("Please enter a correct menu index."); // case 0 is not handled, so don't throw an exeception here
                showMainMenu();
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

        System.out.println("You can add multiple products at once by specifying id or name and quantity like (2:3).");
        System.out.println("To return to the main menu, enter 0.");
        System.out.println("Please enter the corresponding no. or name of the product to add to your order: ");

        scan.nextLine(); // throwaway next line
        String input = scan.nextLine();
        if (input.contains(":")) {
            // if the user enters anything with ":", go on validate that
            validateMultipleInput(input, shopService.productCatalog.length);

        } else if (isInteger(input)) {
            // if the user enters numbers, validate and use that imput
            int index = Integer.parseInt(input);

            // index 0 is reserved for returning to the main menu
            if (index == 0) {
            showMainMenu();
            }

            shopService.addProducts(index - 1, 1); // product id's start at 1 instead of 0
            System.out.println("Product " + shopService.productCatalog[index - 1].getName() + " has been added."); 

        } else {
            // if the user enters a (single) string, use that
            String productName = validateProductName(input);
            shopService.addProducts(productName, 1);
            System.out.println(productName + " has been added.");
        }

        System.out.println(shopService.showBasket());
        System.out.println("Would you like to add another product? ");
        System.out.print("Enter 0 for no, 1 for yes: ");

        int response = validateNumericalInput(1);
        switch (response) {
            case 0: showMainMenu();
            case 1: showProductCatalogue();
            default: throw new InputMismatchException("Input for showProductCatalogue() isn't correctly validated!");
        }
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
        
        int response = validateNumericalInput(4);
        switch (response) {
            case 0: showMainMenu();
            case 1: showProductCatalogue();
            case 2: 
                System.out.println(shopService.showBasket());
                System.out.print("Which product would you like to remove? ");
                
                // if (scan.hasNextInt()) {
                //     validateInput(shopService.basketSize());
                //     // match index on right product
                // }

                String productName = validateProductName(scan.nextLine());
                shopService.removeProducts(productName, 1);
                System.out.println(productName + " has been removed.\n");
                showCurrentOrder();
            case 3: showCustomerData();
            case 4: checkOut();
            default: throw new InputMismatchException("Input for showCurrentOrder() isn't correctly validated!");
        }
    }
    
    public void showCustomerData() {
        System.out.println("CUSTOMER INFO");
        
        if (shopService.hasCustomer()) {
            System.out.println(shopService.showCustomer());
            System.out.println("Do you want to change this info?");
            System.out.print("Enter 0 for returning to the main menu, 1 for editing customer data: ");

            int response = validateNumericalInput(1);
            switch (response) {
                case 0: showMainMenu();
                case 1: promptCustomerData();
                        System.out.println("Customer data updated.");
                        showCustomerData();
                    break;
                default: throw new InputMismatchException("Input for showCurrentOrder() isn't correctly validated!");
            }

        } else {
            System.out.println("No customer info as been added to this order yet.");
            promptCustomerData();
            showCustomerData();
        }
    }

    public void checkOut() {
        System.out.println("CREATE INVOICE");

        // making sure the basket contains products before proceeding
        if(!shopService.hasProducts()) {
            System.out.println("There are no projects in this order yet!");
            showProductCatalogue();
        }

        // making sure there is customer data for this order
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

    public int validateNumericalInput(int range) {
        // first check if there is actually numberical input
        while (!scan.hasNextInt()) {
            System.out.println("You haven't entered a number. Please try again. ");
            scan.next();
        }
        int response = scan.nextInt();

        // if there is, is it within the range (inclusive) we want?
        if (response <= 0 || response > range) {
            System.out.println("You haven't entered a number in the correct range. Please try again. ");
            response = validateNumericalInput(range);
        }
        return response;
    }

    public int validateNumericalInput(String text, int range) {
        // first check if there is actually numberical input
        while (!isInteger(text)) {
            System.out.println("You haven't entered a number. Please try again. ");
            text = scan.next();
        }
        int response = Integer.parseInt(text);

        // if there is, is it within the range (inclusive) we want?
        if (response <= 0 || response > range) {
            System.out.println("You haven't entered a number in the correct range. Please try again. ");
            response = validateNumericalInput(range);
        }
        return response;
    }

    public String validateProductName(String productName) {
        while (!shopService.isProduct(productName)) {
            System.out.println("You haven't entered a correct product name. Please try again. ");
            productName = scan.nextLine();
        }
        return productName;
    }

    public void validateMultipleInput(String response, int range) {

        String[] responseParts = response.split(":");
        int quantity;

        // check if input before ":" is numerical
        if (isInteger(responseParts[0])) {
            
            int productID = validateNumericalInput(responseParts[0], shopService.productCatalog.length);

            // if (isInteger(responseParts[1])) {
                
            //     quantity = Integer.parseInt(responseParts[1]);
            //     shopService.addProducts(productID - 1, quantity); // product id's start at 1 instead of 0                
            // } else {
            //     // if input after ":" isn't a number, ask again
            //     System.out.println("Please enter a number after : .");
            //     System.out.println("How many of " + shopService.productCatalog[productID].getName() + " would you like to add? ");
            //     quantity = validateNumericalInput(99); // let's assume no one would like to add more than 99 products at once
            //     shopService.addProducts(productID - 1, quantity);
            // }

            quantity = validateNumericalInput(responseParts[1], 99);
            shopService.addProducts(productID - 1, quantity);
            System.out.println(shopService.productCatalog[productID - 1].getName() + " has been added to the shopping cart.");

        // if input is not numerical, it should be a product's name
        } else if (shopService.isProduct(responseParts[0])) {
            
            // if (isInteger(responseParts[1])) {
            //     quantity = Integer.parseInt(responseParts[1]);
            // } else {
            //     // if input after : isn't a number, ask again
            //     System.out.println("Please enter a number after : .");
            //     System.out.println("How many of " + shopService.getProduct(responseParts[0]).getName() + " would you like to add? ");
            //     quantity = validateNumericalInput(99); // let's assume no one would like to add more than 99 products at once
            // }

            quantity = validateNumericalInput(responseParts[1], 99);
            shopService.addProducts(responseParts[0], quantity);
            System.out.println(responseParts[0] + " has been added to the shopping cart.");

        } else {
            // first part of command is not understandable at all, give up and show menu instead
            System.out.println("Please enter a correct product name or id.");
        }
    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) { }
        return false;
    }
}
