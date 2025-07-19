package com.epam.jmp.app;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.bank.impl.BankImpl;
import com.epam.jmp.impl.ServiceImpl;
import com.epam.jmp.bank.service.Bank;
import com.epam.jmp.service.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Bank bank = new BankImpl();
        Service subscriptionService = new ServiceImpl();

        User user = new User("Saba", "saba_artkmeladze@epam.com",  LocalDate.of(2003, Month.MARCH, 15));
        BankCard debitCard  = bank.createBankCard(user, BankCardType.DEBIT,  1_000.0);
        BankCard creditCard = bank.createBankCard(user, BankCardType.CREDIT, 5_000.0);

        subscriptionService.subscribe(debitCard);
        subscriptionService.subscribe(creditCard);

        Optional<Subscription> sub = subscriptionService.getSubscriptionByBankCardNumber(debitCard.getNumber());
        sub.ifPresent(s -> System.out.println("Subscribed: " + s.getBankcard() + " since " + s.getStartDate()));

        List<User> users = subscriptionService.getAllUsers();
        System.out.println("All users: " + users);
    }
}
