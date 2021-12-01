package com.commerce.exchanger.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class AccountTest {

  @Test
  void shouldCorrectlyAddAmount() {
    Account account = Account.buildCurrencyAccount("PLN", BigDecimal.valueOf(123.23));

    BigDecimal actual = account.addAmount(BigDecimal.valueOf(12)).getBalance();

    assertEquals(BigDecimal.valueOf(135.23), actual);
  }

  @Test
  void shouldCorrectlySubtractAmount() {
    Account account = Account.buildCurrencyAccount("PLN", BigDecimal.valueOf(123.23));

    BigDecimal actual = account.subtractAmount(BigDecimal.valueOf(23.2)).getBalance();

    assertEquals(BigDecimal.valueOf(100.03), actual);
  }

  @Test
  void shouldReturnTrueWhenAccountHasEnoughFunds() {
    Account account = Account.buildCurrencyAccount("PLN", BigDecimal.valueOf(123.23));

    boolean actual = account.hasEnoughFunds(BigDecimal.valueOf(100));

    assertTrue(actual);
  }

  @Test
  void shouldReturnFalseWhenAccountHasNotEnoughFunds() {
    Account account = Account.buildCurrencyAccount("PLN", BigDecimal.valueOf(123.23));

    boolean actual = account.hasEnoughFunds(BigDecimal.valueOf(200));

    assertFalse(actual);
  }
}
