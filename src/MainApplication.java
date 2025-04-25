import controller.ProductController;
import controller.UserController;
import controller.OrderController;
import model.Cart;
import model.Product;
import model.User;
import view.CartView;
import java.util.ArrayList;
// import java.util.List;    // optional, but recommended if you refer to List<Product>


import java.util.Scanner;

public class MainApplication {
private static Scanner scanner = new Scanner(System.in);
private static UserController userController = new UserController();
private static ProductController productController = new ProductController();
private static OrderController orderController = new OrderController();
private static CartView cartView = new CartView();
private static User currentUser = null;
private static Cart userCart = null;

public static void main(String[] args) {
System.out.println("Welcome to the E-commerce Application!");

while (true) {
    if (currentUser == null) {
        displayAuthMenu();
    } else {
        displayMainMenu();
    }
}
}

private static void displayAuthMenu() {
System.out.println("\n===== AUTHENTICATION =====");
System.out.println("1. Login");
System.out.println("2. Register");
System.out.println("3. Exit");
System.out.print("Choose an option (1-3): ");

int choice = scanner.nextInt();
scanner.nextLine(); // Clear buffer

switch (choice) {
    case 1:
        login();
        break;
    case 2:
        register();
        break;
    case 3:
        System.out.println("Thank you for using our application. Goodbye!");
        System.exit(0);
        break;
    default:
        System.out.println("Invalid option. Please try again.");
}
}

private static void login() {
System.out.print("Username: ");
String username = scanner.nextLine();

System.out.print("Password: ");
String password = scanner.nextLine();

currentUser = userController.login(username, password);

if (currentUser != null) {
    userCart = new Cart(currentUser);
}
}

private static void register() {
System.out.print("Username: ");
String username = scanner.nextLine();

System.out.print("Password: ");
String password = scanner.nextLine();

System.out.print("Email: ");
String email = scanner.nextLine();

System.out.print("Address: ");
String address = scanner.nextLine();

System.out.print("Phone: ");
String phone = scanner.nextLine();

currentUser = userController.register(username, password, email, address, phone);

if (currentUser != null) {
    userCart = new Cart(currentUser);
}
}

private static void displayMainMenu() {
System.out.println("\n===== MAIN MENU =====");
System.out.println("1. Browse Products");
System.out.println("2. View Cart");
System.out.println("3. View Orders");
System.out.println("4. Logout");
System.out.print("Choose an option (1-4): ");

int choice = scanner.nextInt();
scanner.nextLine(); // Clear buffer

switch (choice) {
    case 1:
        browseProducts();
        break;
    case 2:
        manageCart();
        break;
    case 3:
        viewOrders();
        break;
    case 4:
        logout();
        break;
    default:
        System.out.println("Invalid option. Please try again.");
}
}

private static void browseProducts() {
while (true) {
    System.out.println("\n===== PRODUCT MENU =====");
    System.out.println("1. View All Products");
    System.out.println("2. Search by Category");
    System.out.println("3. View Product Details");
    System.out.println("4. Add to Cart");
    System.out.println("5. Back to Main Menu");
    System.out.print("Choose an option (1-5): ");
    
    int choice = scanner.nextInt();
    scanner.nextLine(); // Clear buffer
    
    switch (choice) {
        case 1:
            productController.showAllProducts();
            break;
        case 2:
            System.out.print("Enter category: ");
            String category = scanner.nextLine();
            productController.showProductsByCategory(category);
            break;
        case 3:
            System.out.print("Enter product ID: ");
            int productId = scanner.nextInt();
            productController.showProductDetails(productId);
            break;
        case 4:
            addToCart();
            break;
        case 5:
            return;
        default:
            System.out.println("Invalid option. Please try again.");
    }
}
}

private static void addToCart() {
int productId = cartView.getProductIdToAdd();
Product product = productController.getProductById(productId);

if (product == null) {
    System.out.println("Product not found!");
    return;
}

int quantity = cartView.getQuantityToAdd();

if (quantity <= 0 || quantity > product.getStockQuantity()) {
    System.out.println("Invalid quantity!");
    return;
}

userCart.addProduct(product, quantity);
System.out.println("Product added to cart!");
}

private static void manageCart() {
while (true) {
    cartView.displayCart(userCart);
    
    System.out.println("\n===== CART MENU =====");
    System.out.println("1. Update Quantity");
    System.out.println("2. Remove Item");
    System.out.println("3. Checkout");
    System.out.println("4. Back to Main Menu");
    System.out.print("Choose an option (1-4): ");
    
    int choice = scanner.nextInt();
    scanner.nextLine(); // Clear buffer
    
    switch (choice) {
        case 1:
            updateCartItemQuantity();
            break;
        case 2:
            removeCartItem();
            break;
        case 3:
            checkout();
            break;
        case 4:
            return;
        default:
            System.out.println("Invalid option. Please try again.");
    }
}
}

private static void updateCartItemQuantity() {
if (userCart.getItems().isEmpty()) {
    System.out.println("Cart is empty!");
    return;
}

int index = cartView.getProductIndexToRemove();
if (index < 1 || index > userCart.getItems().size()) {
    System.out.println("Invalid product index!");
    return;
}

Product product = new ArrayList<>(userCart.getItems().keySet()).get(index - 1);
int quantity = cartView.getQuantityToUpdate();

userCart.updateQuantity(product, quantity);
System.out.println("Cart updated!");
}

private static void removeCartItem() {
if (userCart.getItems().isEmpty()) {
    System.out.println("Cart is empty!");
    return;
}

int index = cartView.getProductIndexToRemove();
if (index < 1 || index > userCart.getItems().size()) {
    System.out.println("Invalid product index!");
    return;
}

Product product = new ArrayList<>(userCart.getItems().keySet()).get(index - 1);
userCart.removeProduct(product);
System.out.println("Product removed from cart!");
}

private static void checkout() {
if (userCart.getItems().isEmpty()) {
    System.out.println("Cannot checkout with an empty cart!");
    return;
}

if (cartView.getCheckoutConfirmation()) {
    String paymentMethod = cartView.getPaymentMethod();
    orderController.placeOrder(userCart, paymentMethod);
}
}

private static void viewOrders() {
orderController.showUserOrders(currentUser);

System.out.println("\n===== ORDERS MENU =====");
System.out.println("1. View Order Details");
System.out.println("2. Back to Main Menu");
System.out.print("Choose an option (1-2): ");

int choice = scanner.nextInt();
scanner.nextLine(); // Clear buffer

if (choice == 1) {
    System.out.print("Enter order ID: ");
    int orderId = scanner.nextInt();
    orderController.showOrderDetails(orderId);
}
}

private static void logout() {
currentUser = null;
userCart = null;
System.out.println("Logged out successfully!");
}
}