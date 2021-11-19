package com.commerce.exchanger.service;

import com.commerce.exchanger.model.Account;
import com.commerce.exchanger.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  @Value("${exchanger.initial.currency:PLN}")
  String initialAccountCurrency;

  private AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account createInitialAccount(BigDecimal initialBalance) {
    Account account = new Account();
    account.setUuid(UUID.randomUUID());
    account.setBalance(initialBalance);
    account.setCurrency(initialAccountCurrency);
    return accountRepository.save(account);
  }
}
