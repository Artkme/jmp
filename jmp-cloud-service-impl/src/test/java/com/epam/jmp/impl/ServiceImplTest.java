package com.epam.jmp.impl;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.epam.jmp.dto.User;
import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.exception.SubscriptionNotFoundException;
import com.epam.jmp.service.Service;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ServiceImplTest {

    private final ServiceImpl service = new ServiceImpl();
    private final User user = new User("Saba", "saba@epam.com", LocalDate.of(2003, Month.MARCH, 15));

    @Test
    void subscribeRegistersSubscriptionAndUser() {
        BankCard card = new BankCard("1234567890123456", user);
        service.subscribe(card);
        Subscription subscription = service.getSubscriptionByBankCardNumber(card.getNumber());
        assertNotNull(subscription);
        List<User> users = service.getAllUsers();
        assertEquals(1, users.size());
        assertTrue(users.contains(user));
    }

    @Test
    void getSubscriptionByBankCardNumberThrowsWhenNotFound() {
        assertThrows(SubscriptionNotFoundException.class, () -> service.getSubscriptionByBankCardNumber("0000"));
    }

    @Test
    void filterSubscriptionsByCondition() {
        BankCard card = new BankCard("1111222233334444", user);
        service.subscribe(card);
        assertTrue(service.getAllSubscriptionsByCondition(s -> false).isEmpty());
    }

    @Test
    void getAverageUsersAgeEmpty() {
        assertEquals(0.0, service.getAverageUsersAge());
    }

    @Test
    void getAverageUsersAgeAfterSubscribe() {
        BankCard card = new BankCard("2222333344445555", user);
        service.subscribe(card);
        double expected = ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now());
        assertEquals(expected, service.getAverageUsersAge());
    }

    @Test
    void isPayableUserOver18() {
        assertTrue(Service.isPayableUser(user));
    }

    @Test
    void isPayableUserUnder18() {
        User under = new User("lewis", "lewis@epam.com", LocalDate.now().minusYears(17));
        assertFalse(Service.isPayableUser(under));
    }
}
