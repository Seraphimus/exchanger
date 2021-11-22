package com.commerce.exchanger.service;

import com.commerce.exchanger.model.Account;
import com.commerce.exchanger.model.ExchangeOperation;
import com.commerce.exchanger.model.User;
import com.commerce.exchanger.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  @Value("${exchanger.base.currency:PLN}")
  String baseAccountCurrency;

  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account createInitialAccount(BigDecimal initialBalance, UUID userUuid) {
    Account account = new Account();
    account.setUuid(UUID.randomUUID());
    account.setBalance(initialBalance);
    account.setCurrency(baseAccountCurrency);
    account.setUser(userUuid);
    return accountRepository.save(account);
  }

  public boolean hasSufficientAmount(UUID userUuid, String currencyCode, BigDecimal amount) {
    return accountRepository.findByCurrencyAndUser(currencyCode, userUuid)
        .map(account -> account.getBalance().compareTo(amount) >= 0)
        .orElse(false);

  }

  public void updateUserAccounts(ExchangeOperation operation) {
    Account fromAccount = getAccountByCurrency(operation.getUser(),
        operation.getFromCurrency()).orElseThrow(IllegalArgumentException::new);
    fromAccount.setBalance(fromAccount.getBalance().subtract(operation.getAmountToExchange()));

    Optional<Account> toAccount = getAccountByCurrency(operation.getUser(),
        operation.getToCurrency());
    Account account;
    if (toAccount.isEmpty()) {
      account = new Account();
      account.setUuid(UUID.randomUUID());
      account.setCurrency(operation.getToCurrency());
      account.setBalance(operation.getAmountExchanged());
      account.setUser(operation.getUser().getUuid());
    } else {
      account = toAccount.get();
      account.setBalance(account.getBalance().add(operation.getAmountExchanged()));
    }

    accountRepository.saveAll(List.of(fromAccount, account));
  }

  private Optional<Account> getAccountByCurrency(User user, String currency) {
    return user.getAccounts().stream()
        .filter(account -> currency.equalsIgnoreCase(account.getCurrency()))
        .findFirst();
  }
}
