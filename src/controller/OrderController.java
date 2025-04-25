package controller;

import model.Cart;
import model.Order;
import model.User;
import model.Repository.OrderRepository;
import util.factory.PaymentProcessorFactory;
import util.observer.OrderTracker;
import view.OrderView;

import java.util.List;

public class OrderController {
private OrderRepository orderRepository;
private OrderView orderView;
private PaymentProcessorFactory paymentProcessorFactory;

public OrderController() {
this.orderRepository = new OrderRepository();
this.orderView = new OrderView();
this.paymentProcessorFactory = new PaymentProcessorFactory();
}

public Order placeOrder(Cart cart, String paymentMethod) {
if (cart.getItems().isEmpty()) {
    System.out.println("Cannot place an order with an empty cart.");
    return null;
}

// Create order
Order order = new Order(0, cart.getUser(), cart.getProductList(), paymentMethod);

// Add observer for order tracking
OrderTracker tracker = new OrderTracker();
order.addObserver(tracker);

// Process payment
boolean paymentSuccess = paymentProcessorFactory.createPaymentProcessor(paymentMethod)
        .processPayment(cart.calculateTotal());

if (!paymentSuccess) {
    System.out.println("Payment failed. Order cancelled.");
    return null;
}

// Save order to database
boolean success = orderRepository.save(order);

if (success) {
    // Clear cart after successful order
    cart.clearCart();
    
    // Update order status to trigger observer
    order.updateStatus("PAID");
    
    // Show confirmation
    orderView.displayOrderConfirmation(order);
    return order;
} else {
    System.out.println("Failed to place order.");
    return null;
}
}

public void showOrderDetails(int orderId) {
Order order = orderRepository.findById(orderId);
if (order != null) {
    orderView.displayOrderDetails(order);
} else {
    System.out.println("Order not found!");
}
}

public void showUserOrders(User user) {
List<Order> orders = orderRepository.findByUser(user);
orderView.displayOrderList(orders);
}

public void updateOrderStatus(int orderId) {
Order order = orderRepository.findById(orderId);

if (order == null) {
    System.out.println("Order not found!");
    return;
}

String newStatus = orderView.getStatusUpdateInput();
boolean success = orderRepository.updateStatus(orderId, newStatus);

if (success) {
    // Update order status to trigger observer
    order.updateStatus(newStatus);
    System.out.println("Order status updated successfully!");
} else {
    System.out.println("Failed to update order status.");
}
}
}