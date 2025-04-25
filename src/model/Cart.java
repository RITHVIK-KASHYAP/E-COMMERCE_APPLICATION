package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private User user;
    private Map<Product, Integer> items; // Product and quantity
    
    public Cart(User user) {
        this.user = user;
        this.items = new HashMap<>();
    }
    
    public void addProduct(Product product, int quantity) {
        if (items.containsKey(product)) {
            items.put(product, items.get(product) + quantity);
        } else {
            items.put(product, quantity);
        }
    }
    
    public void removeProduct(Product product) {
        items.remove(product);
    }
    
    public void updateQuantity(Product product, int quantity) {
        if (quantity <= 0) {
            removeProduct(product);
        } else {
            items.put(product, quantity);
        }
    }
    
    public double calculateTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
    
    public void clearCart() {
        items.clear();
    }
    
    public Map<Product, Integer> getItems() {
        return items;
    }
    
    public User getUser() {
        return user;
    }
    
    public List<Product> getProductList() {
        return new ArrayList<>(items.keySet());
    }
}