package view;

import model.Product;
import java.util.List;
import java.util.Scanner;

public class ProductView {
    private Scanner scanner;
    
    public ProductView() {
        this.scanner = new Scanner(System.in);
    }
    
    public void displayProductDetails(Product product) {
        System.out.println("\n===== PRODUCT DETAILS =====");
        System.out.println("ID: " + product.getId());
        System.out.println("Name: " + product.getName());
        System.out.println("Description: " + product.getDescription());
        System.out.println("Price: $" + product.getPrice());
        System.out.println("In Stock: " + product.getStockQuantity());
        System.out.println("Category: " + product.getCategory());
        System.out.println("===========================\n");
    }
    
    public void displayProductList(List<Product> products) {
        System.out.println("\n===== PRODUCT LIST =====");
        for (Product product : products) {
            System.out.println(product.getId() + ". " + product.getName() + " - $" + product.getPrice() + " (" + product.getCategory() + ")");
        }
        System.out.println("=======================\n");
    }
    
    public int getProductIdInput() {
        System.out.print("Enter product ID: ");
        return scanner.nextInt();
    }
    
    public String getCategoryInput() {
        System.out.print("Enter category name: ");
        return scanner.next();
    }
    
    public Product getNewProductInput() {
        scanner.nextLine(); // Clear buffer
        
        System.out.println("\n===== ADD NEW PRODUCT =====");
        System.out.print("Product name: ");
        String name = scanner.nextLine();
        
        System.out.print("Description: ");
        String description = scanner.nextLine();
        
        System.out.print("Price: ");
        double price = scanner.nextDouble();
        
        System.out.print("Stock quantity: ");
        int stockQuantity = scanner.nextInt();
        
        scanner.nextLine(); // Clear buffer
        
        System.out.print("Category: ");
        String category = scanner.nextLine();
        
        return new Product(0, name, description, price, stockQuantity, category);
    }
    
    public Product getUpdateProductInput(Product product) {
        scanner.nextLine(); // Clear buffer
        
        System.out.println("\n===== UPDATE PRODUCT =====");
        System.out.print("Product name (" + product.getName() + "): ");
        String name = scanner.nextLine();
        name = name.isEmpty() ? product.getName() : name;
        
        System.out.print("Description (" + product.getDescription() + "): ");
        String description = scanner.nextLine();
        description = description.isEmpty() ? product.getDescription() : description;
        
        System.out.print("Price (" + product.getPrice() + "): ");
        String priceStr = scanner.nextLine();
        double price = priceStr.isEmpty() ? product.getPrice() : Double.parseDouble(priceStr);
        
        System.out.print("Stock quantity (" + product.getStockQuantity() + "): ");
        String stockStr = scanner.nextLine();
        int stockQuantity = stockStr.isEmpty() ? product.getStockQuantity() : Integer.parseInt(stockStr);
        
        System.out.print("Category (" + product.getCategory() + "): ");
        String category = scanner.nextLine();
        category = category.isEmpty() ? product.getCategory() : category;
        
        return new Product(product.getId(), name, description, price, stockQuantity, category);
    }
}
