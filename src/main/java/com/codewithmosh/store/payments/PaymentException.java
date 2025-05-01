package com.codewithmosh.store.payments;

public class PaymentException extends RuntimeException {
    public PaymentException() {
        super("Payment failed");
    }

    public PaymentException(String message) {
        super(message);
    }
}
