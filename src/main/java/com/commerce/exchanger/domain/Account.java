package com.commerce.exchanger.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Account implements Serializable {

  String currency;
  BigDecimal balance;

  public static Account createBaseCurrencyAccount(BigDecimal balance) {
    return Account.builder()
        .currency("PLN")
        .balance(balance)
        .build();
  }

  public static Account createForeignCurrencyAccount(String currency, BigDecimal balance) {
    return Account.builder()
        .currency(currency)
        .balance(balance)
        .build();
  }

  public static Account readAccountData(String currency, BigDecimal balance) {
    return Account.builder()
        .currency(currency)
        .balance(balance)
        .build();
  }

  public boolean hasEnoughFunds(BigDecimal amount) {
    return balance.compareTo(amount) >= 0;
  }

  public Account subtractAmount(BigDecimal amount) {
    this.balance = this.balance.subtract(amount);
    return this;
  }

  public Account addAmount(BigDecimal amount) {
    this.balance = this.balance.add(amount);
    return this;
  }
}
