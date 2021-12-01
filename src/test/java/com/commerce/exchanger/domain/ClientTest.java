package com.commerce.exchanger.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.commerce.exchanger.app.exception.ExchangerGeneralException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClientTest {

  @Test
  void shouldThrowExceptionWhenCreatingClientWithNoFirstName() {
    assertThrows(ExchangerGeneralException.class,
        () -> Client.buildNewClient(null, "test", BigDecimal.ONE, "PLN"));
  }

  @Test
  void shouldThrowExceptionWhenCreatingClientWithBlankFirstName() {
    assertThrows(ExchangerGeneralException.class,
        () -> Client.buildNewClient("", "test", BigDecimal.ONE, "PLN"));
  }

  @Test
  void shouldThrowExceptionWhenCreatingClientWithNoLastName() {
    assertThrows(ExchangerGeneralException.class,
        () -> Client.buildNewClient("test", null, BigDecimal.ONE, "PLN"));
  }

  @Test
  void shouldThrowExceptionWhenCreatingClientWithBlankLastName() {
    assertThrows(ExchangerGeneralException.class,
        () -> Client.buildNewClient("test", "", BigDecimal.ONE, "PLN"));
  }

  @Test
  void shouldThrowExceptionWhenCreatingClientWithNoBalance() {
    assertThrows(ExchangerGeneralException.class,
        () -> Client.buildNewClient("test", "test", null, "PLN"));
  }

  @Test
  void shouldThrowExceptionWhenCreatingClientWithNegativeBalance() {
    BigDecimal negative = BigDecimal.valueOf(-200.00);
    assertThrows(ExchangerGeneralException.class,
        () -> Client.buildNewClient("test", "test", negative, "PLN"));
  }

  @Test
  void shouldSuccessfullyPerformExchangeAndCreateAdditionalAccount() {
    Client client = Client.buildNewClient("a", "b", BigDecimal.valueOf(100), "PLN");
    CurrencyPair pair = CurrencyPair.of("PLN", "USD");
    BigDecimal amount = BigDecimal.valueOf(10);
    BigDecimal calculatedAmount = BigDecimal.valueOf(2.45);

    int currentAccountSize = client.getAccounts().size();
    client.performExchange(pair, amount, calculatedAmount);

    assertEquals(1, currentAccountSize);
    assertEquals(2, client.getAccounts().size());
    assertEquals(BigDecimal.valueOf(90), client.getAccounts().get("PLN").getBalance());
    assertEquals(BigDecimal.valueOf(2.45), client.getAccounts().get("USD").getBalance());
  }

}
