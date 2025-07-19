package com.epam.jmp.bank.impl;

import com.epam.jmp.dto.*;
import com.epam.jmp.bank.service.Bank;

public class BankImpl implements Bank {
    @Override
    public BankCard createBankCard(User user, BankCardType cardType, double amount) {
        return switch (cardType) {
            case CREDIT -> new CreditBankCard(BankCard.generateCardNumber(), user, amount);
            case DEBIT -> new DebitBankCard(BankCard.generateCardNumber(), user, amount);
        };
    }
}
