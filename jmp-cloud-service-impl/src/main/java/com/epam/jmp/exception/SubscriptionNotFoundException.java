package com.epam.jmp.exception;

public class SubscriptionNotFoundException extends RuntimeException {
    public SubscriptionNotFoundException(String cardNumber) {
        super("Subscription not found for card number: " + cardNumber);
    }
}
