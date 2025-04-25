package util.observer;

// Observer Pattern Implementation for Order Tracking
public class OrderTracker implements Observer {
@Override
public void update() {
System.out.println("OrderTracker: Order status has been updated!");
System.out.println("Notification sent to customer...");
}
}