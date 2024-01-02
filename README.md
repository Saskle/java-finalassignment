# Photoshop Product Ordering App



This project is my solution to the final assignment of the Java course taught at Calco. 


## Requirements

The following requirements needed to be met in the application. They were vague and uncomplete on purpose.


> For a photocopy store called PhotoShop, you have to make a good working application that can select different items from the store catalogue and add this to a shopping cart. After the user has selected all the different items an invoice has to be created.
> + The user needs to input either the id or name of the item to put it in the order. 
> + The user needs to be able to modify or add multiple items to the order. 
> + The user needs to be able to modify or add customer information for the order. 
> + The user needs to be able to create an invoice. 
> + On the invoice, the entire order's total cost must be shown. 
> + On the invoice, the completion time needs to be shown. 
> + The pickup date and time need to be within the store's opening hours. 
> + Keep in mind that PhotoShop only has one printer, so orders should stack accordingly.  
> + The shopping cart needs to be saved to and retrieved from either XML or Json, so that old orders can be reviewed. 
> + Use the CSV files for the opening hours and the price list. 
> + The program needs to be made completely in the command log.
> + Make sure your code is efficient, readable, extendable and maintainable.  
> + Your grandma should be able to use your application, so make sure is clear and easy to use. 

To prevent having to deal with unfinished orders and orders that are being printed both being saved to json and then having to figure out a way to mark them finished and unfinished, I save the basket to JSON when the order is not placed yet, and the order to json when it is finished and placed in the printer queue.
 

## UML
