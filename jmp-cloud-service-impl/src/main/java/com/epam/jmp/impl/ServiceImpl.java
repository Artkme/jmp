package com.epam.jmp.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.exception.SubscriptionNotFoundException;
import com.epam.jmp.service.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ServiceImpl implements Service {

    private final Map<String, Subscription> subscriptions = new HashMap<>();
    private final Set<User> users = new HashSet<>();

    @Override
    public void subscribe(BankCard bankCard) {
        subscriptions.computeIfAbsent(
                bankCard.getNumber(),
                num -> new Subscription(num, LocalDate.now())
        );
        users.add(bankCard.getUser());
    }

    @Override
    public Subscription getSubscriptionByBankCardNumber(String cardNumber) {
        return Optional.ofNullable(subscriptions.get(cardNumber))
                .orElseThrow(() -> new SubscriptionNotFoundException(cardNumber));
    }

    @Override
    public List<User> getAllUsers() {
        return users.stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition) {
        return subscriptions.values().stream()
                .filter(condition)
                .collect(Collectors.toUnmodifiableList());
    }
}
