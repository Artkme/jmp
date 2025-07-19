package com.epam.jmp.service;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Predicate;

public interface Service {

    void subscribe(BankCard bankCard);

    Subscription getSubscriptionByBankCardNumber(String cardNumber);

    List<User> getAllUsers();

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition);

    default double getAverageUsersAge() {
        return getAllUsers().stream()
                .mapToDouble(u -> ChronoUnit.YEARS.between(u.getBirthday(), LocalDate.now()))
                .average()
                .orElse(0.0);
    }

    static boolean isPayableUser(User user) {
        long age = ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now());
        return age > 18;
    }
}
