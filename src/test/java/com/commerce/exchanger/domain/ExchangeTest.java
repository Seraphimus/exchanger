package com.commerce.exchanger.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.commerce.exchanger.app.exception.ExchangerGeneralException;
import java.math.BigDecimal;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class ExchangeTest {

  @Test
  void shouldThrowWhenAttemptingToPerformExchangeForUnknownClient() {
    CurrencyPair pair = CurrencyPair.of("PLN", "USD");
    Rate rate = Rate.builder().build();
    Exchange exchange = Exchange.buildExchange(pair, "PLN", BigDecimal.valueOf(100));

    assertThrows(
        ExchangerGeneralException.class,
        () -> exchange.performExchangeForClientWithRate(null, rate));
  }

  @Test
  void shouldThrowWhenAttemptingToPerformExchangeForClientWithInsufficientFunds() {
    CurrencyPair pair = CurrencyPair.of("PLN", "USD");
    Rate rate = Rate.builder().build();
    Exchange exchange = Exchange.buildExchange(pair, "PLN", BigDecimal.valueOf(100));
    Client client = Client.buildNewClient("a", "b", BigDecimal.ONE, "PLN");

    assertThrows(
        ExchangerGeneralException.class,
        () -> exchange.performExchangeForClientWithRate(client, rate));
  }

  @Test
  void shouldCorrectlyCalculateFromBaseCurrencyToForeign() {
    CurrencyPair pair = CurrencyPair.of("PLN", "USD");
    Rate rate = Rate.builder()
        .fromCurrency("PLN")
        .toCurrency("USD")
        .value(BigDecimal.valueOf(4.0683))
        .build();
    Exchange exchange = Exchange.buildExchange(pair, "PLN", BigDecimal.valueOf(100));
    Client client = generateClientWithAccounts();

    Client actual = exchange.performExchangeForClientWithRate(client, rate);

    assertEquals(BigDecimal.valueOf(224.5803), actual.getAccounts().get("USD").getBalance());
    assertEquals(BigDecimal.valueOf(100), actual.getAccounts().get("PLN").getBalance());
  }

  @Test
  void shouldCorrectlyCalculateFromForeignCurrencyToBase() {
    CurrencyPair pair = CurrencyPair.of("USD", "PLN");
    Rate rate = Rate.builder()
        .fromCurrency("USD")
        .toCurrency("PLN")
        .value(BigDecimal.valueOf(4.1505))
        .build();
    Exchange exchange = Exchange.buildExchange(pair, "PLN", BigDecimal.valueOf(100));
    Client client = generateClientWithAccounts();

    Client actual = exchange.performExchangeForClientWithRate(client, rate);

    assertEquals(BigDecimal.valueOf(100), actual.getAccounts().get("USD").getBalance());
    assertEquals(BigDecimal.valueOf(615.05), actual.getAccounts().get("PLN").getBalance());
  }

  private Client generateClientWithAccounts() {
    Account plnAccount = Account.buildCurrencyAccount("PLN", BigDecimal.valueOf(200));
    Account usdAccount = Account.buildCurrencyAccount("USD", BigDecimal.valueOf(200));
    HashMap<String, Account> accounts = new HashMap<>();
    accounts.put("PLN", plnAccount);
    accounts.put("USD", usdAccount);
    ClientIdentifier id = ClientIdentifier.generateIdentifier();
    return Client.buildExistingClient(id, "a", "b", accounts);
  }
}