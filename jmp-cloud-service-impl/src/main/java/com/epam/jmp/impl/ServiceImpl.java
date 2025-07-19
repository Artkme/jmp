package com.epam.jmp.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.Service;

import java.time.LocalDate;
import java.util.*;


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
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        return Optional.ofNullable(subscriptions.get(cardNumber));
    }


    @Override
    public List<User> getAllUsers() {
        return users.stream().toList();
    }
}
