package com.epam.jmp.bank.impl;

import com.epam.jmp.dto.*;
import com.epam.jmp.bank.service.Bank;

import java.util.function.BiFunction;

public class BankImpl implements Bank {

    @Override
    public BankCard createBankCard(User user, BankCardType cardType, double amount) {
        BiFunction<User, Double, BankCard> ctor =
                switch (cardType) {
                    case CREDIT -> CreditBankCard::new;
                    case DEBIT  -> DebitBankCard::new;
                };
        return ctor.apply(user, amount);
    }
}
