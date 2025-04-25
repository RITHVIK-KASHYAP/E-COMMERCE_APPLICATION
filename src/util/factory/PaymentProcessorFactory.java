package util.factory;

// Factory Pattern for payment processing
public class PaymentProcessorFactory {

public PaymentProcessor createPaymentProcessor(String paymentMethod) {
switch (paymentMethod) {
    case "CREDIT_CARD":
        return new CreditCardProcessor();
    case "PAYPAL":
        return new PayPalProcessor();
    case "BANK_TRANSFER":
        return new BankTransferProcessor();
    default:
        return new CreditCardProcessor(); // Default
}
}

public interface PaymentProcessor {
boolean processPayment(double amount);
}

private class CreditCardProcessor implements PaymentProcessor {
@Override
public boolean processPayment(double amount) {
    System.out.println("Processing credit card payment of $" + amount);
    // Credit card processing logic would go here
    return true; // Simulating successful payment
}
}

private class PayPalProcessor implements PaymentProcessor {
@Override
public boolean processPayment(double amount) {
    System.out.println("Processing PayPal payment of $" + amount);
    // PayPal processing logic would go here
    return true; // Simulating successful payment
}
}

private class BankTransferProcessor implements PaymentProcessor {
@Override
public boolean processPayment(double amount) {
    System.out.println("Processing bank transfer payment of $" + amount);
    // Bank transfer processing logic would go here
    return true; // Simulating successful payment
}
}
}