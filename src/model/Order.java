package model;

import java.util.Date;
import java.util.List;
import util.observer.Subject;
// import util.observer.Observer;
// import java.util.ArrayList;

// Using the Observer pattern to track order status
public class Order extends Subject {
    private int id;
    private User user;
    private List<Product> products;
    private double totalAmount;
    private Date orderDate;
    private String status;
    private String paymentMethod;
    
    public Order(int id, User user, List<Product> products, String paymentMethod) {
        this.id = id;
        this.user = user;
        this.products = products;
        this.paymentMethod = paymentMethod;
        this.orderDate = new Date();
        this.status = "PENDING";
        calculateTotalAmount();
    }
    
    private void calculateTotalAmount() {
        this.totalAmount = products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }
    
    public void updateStatus(String status) {
        this.status = status;
        notifyObservers(); // Notify all observers about the status change
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public User getUser() {
        return user;
    }
    
    public List<Product> getProducts() {
        return products;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public Date getOrderDate() {
        return orderDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                ", products=" + products.size() +
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}