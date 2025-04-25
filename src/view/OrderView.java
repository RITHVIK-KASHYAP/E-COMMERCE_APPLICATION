package view;

import model.Order;
import model.Product;

import java.util.List;
import java.util.Scanner;

public class OrderView {
    private Scanner scanner;
    
    public OrderView() {
        this.scanner = new Scanner(System.in);
    }
    
    public void displayOrderDetails(Order order) {
        System.out.println("\n===== ORDER DETAILS =====");
        System.out.println("Order ID: " + order.getId());
        System.out.println("Customer: " + order.getUser().getUsername());
        System.out.println("Date: " + order.getOrderDate());
        System.out.println("Status: " + order.getStatus());
        System.out.println("Payment Method: " + order.getPaymentMethod());
        
        System.out.println("\nItems:");
        for (Product product : order.getProducts()) {
            System.out.println("- " + product.getName() + " - $" + product.getPrice());
        }
        
        System.out.println("\nTotal Amount: $" + order.getTotalAmount());
        System.out.println("=======================\n");
    }
    
    public void displayOrderList(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("\n===== NO ORDERS FOUND =====\n");
            return;
        }
        
        System.out.println("\n===== YOUR ORDERS =====");
        for (Order order : orders) {
            System.out.println("ID: " + order.getId() + 
                    " | Date: " + order.getOrderDate() + 
                    " | Status: " + order.getStatus() + 
                    " | Total: $" + order.getTotalAmount());
        }
        System.out.println("=====================\n");
    }
    
    public int getOrderIdInput() {
        System.out.print("Enter order ID: ");
        return scanner.nextInt();
    }
    
    public String getStatusUpdateInput() {
        System.out.println("\n===== UPDATE ORDER STATUS =====");
        System.out.println("1. PENDING");
        System.out.println("2. PAID");
        System.out.println("3. SHIPPED");
        System.out.println("4. DELIVERED");
        System.out.println("5. CANCELLED");
        System.out.print("Select new status (1-5): ");
        
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: return "PENDING";
            case 2: return "PAID";
            case 3: return "SHIPPED";
            case 4: return "DELIVERED";
            case 5: return "CANCELLED";
            default: return "PENDING";
        }
    }
    
    public void displayOrderConfirmation(Order order) {
        System.out.println("\n===== ORDER CONFIRMATION =====");
        System.out.println("Your order has been placed successfully!");
        System.out.println("Order ID: " + order.getId());
        System.out.println("Total Amount: $" + order.getTotalAmount());
        System.out.println("Payment Method: " + order.getPaymentMethod());
        System.out.println("Current Status: " + order.getStatus());
        System.out.println("==============================\n");
    }
}
