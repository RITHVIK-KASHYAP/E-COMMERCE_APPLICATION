package view;

import model.Cart;
import model.Product;

import java.util.Map;
import java.util.Scanner;

public class CartView {
    private Scanner scanner;
    
    public CartView() {
        this.scanner = new Scanner(System.in);
    }
    
    public void displayCart(Cart cart) {
        if (cart.getItems().isEmpty()) {
            System.out.println("\n===== YOUR CART IS EMPTY =====\n");
            return;
        }
        
        System.out.println("\n===== YOUR SHOPPING CART =====");
        int index = 1;
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double itemTotal = product.getPrice() * quantity;
            
            System.out.println(index + ". " + product.getName() + " - $" + product.getPrice() + " x " + quantity + " = $" + itemTotal);
            index++;
        }
        
        System.out.println("\nTotal: $" + cart.calculateTotal());
        System.out.println("=============================\n");
    }
    
    public int getProductIndexToRemove() {
        System.out.print("Enter the number of the product to remove: ");
        return scanner.nextInt();
    }
    
    public int getQuantityToUpdate() {
        System.out.print("Enter new quantity (0 to remove): ");
        return scanner.nextInt();
    }
    
    public int getProductIdToAdd() {
        System.out.print("Enter product ID to add to cart: ");
        return scanner.nextInt();
    }
    
    public int getQuantityToAdd() {
        System.out.print("Enter quantity: ");
        return scanner.nextInt();
    }
    
    public boolean getCheckoutConfirmation() {
        System.out.print("Proceed to checkout? (y/n): ");
        String response = scanner.next().toLowerCase();
        return response.equals("y") || response.equals("yes");
    }
    
    public String getPaymentMethod() {
        System.out.println("\n===== PAYMENT METHOD =====");
        System.out.println("1. Credit Card");
        System.out.println("2. PayPal");
        System.out.println("3. Bank Transfer");
        System.out.print("Select payment method (1-3): ");
        
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: return "CREDIT_CARD";
            case 2: return "PAYPAL";
            case 3: return "BANK_TRANSFER";
            default: return "CREDIT_CARD";
        }
    }
}