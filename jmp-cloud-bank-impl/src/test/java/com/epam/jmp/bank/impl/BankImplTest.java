package com.epam.jmp.bank.impl;

import com.epam.jmp.bank.service.Bank;
import com.epam.jmp.dto.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static junit.framework.Assert.*;

public class BankImplTest {
    private final Bank bank = new BankImpl();
    private final User user = new User("Saba", "saba@epam.com", LocalDate.of(2003, Month.MARCH, 15));

    @Test
    void createCreditBankCard() {
        BankCard card = bank.createBankCard(user, BankCardType.CREDIT, 100.0);
        assertTrue(card instanceof CreditBankCard);
        assertEquals(user, card.getUser());
        assertNotNull(card.getNumber());
        assertEquals(16, card.getNumber().length());
    }

    @Test
    void createDebitBankCard() {
        BankCard card = bank.createBankCard(user, BankCardType.DEBIT, 200.0);
        assertTrue(card instanceof DebitBankCard);
        assertEquals(user, card.getUser());
        assertNotNull(card.getNumber());
        assertEquals(16, card.getNumber().length());
    }
}
