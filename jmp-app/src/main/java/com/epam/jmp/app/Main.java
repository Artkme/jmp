package com.epam.jmp.app;

import com.epam.jmp.bank.impl.BankImpl;
import com.epam.jmp.bank.service.Bank;
import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.exception.SubscriptionNotFoundException;
import com.epam.jmp.impl.ServiceImpl;
import com.epam.jmp.service.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Bank bank = new BankImpl();
        Service service = new ServiceImpl();

        User saba = new User("Saba", "saba_artkmeladze@epam.com", LocalDate.of(2003, Month.MARCH, 15));
        User lewis  = new User("Lewis", "lewis_hamilton@epam.com", LocalDate.of(2000, Month.JANUARY, 1));
        User sebastian  = new User("Sebastian", "sebastian_vettel@epam.com", LocalDate.of(2010, Month.JANUARY, 1));

        BankCard debitCard  = bank.createBankCard(saba, BankCardType.DEBIT, 1000);
        BankCard creditCard = bank.createBankCard(lewis, BankCardType.CREDIT, 5000);

        service.subscribe(debitCard);
        service.subscribe(creditCard);

        Subscription sub1 = service.getSubscriptionByBankCardNumber(debitCard.getNumber());
        System.out.println("Found subscription: " + sub1);

        List<User> allUsers = service.getAllUsers();
        System.out.println("All users: " + allUsers);

        double avgAge = service.getAverageUsersAge();
        System.out.printf("Average user age: %.1f years%n", avgAge);

        System.out.println("Is Saba payable? " + Service.isPayableUser(saba));
        System.out.println("Is Lewis payable? " + Service.isPayableUser(lewis));
        System.out.println("Is Sebastian payable? " + Service.isPayableUser(sebastian));


        List<Subscription> recent = service.getAllSubscriptionsByCondition(
                sub -> sub.getStartDate().isAfter(LocalDate.now().minusDays(1))
        );
        System.out.println("Recent subscriptions: " + recent);

        try {
            service.getSubscriptionByBankCardNumber("no-such-card");
        } catch (SubscriptionNotFoundException ex) {
            System.err.println("Expected error: " + ex.getMessage());
        }
    }
}
