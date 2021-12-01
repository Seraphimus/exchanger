package com.commerce.exchanger.domain;

import com.commerce.exchanger.app.exception.ExchangerGeneralException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Client implements Serializable {

  ClientIdentifier identifier;
  String firstName;
  String lastName;
  Map<String, Account> accounts;

  public static Client buildNewClient(String firstName, String lastName, BigDecimal balance,
      String baseCurrency) {
    validateClientValues(firstName, lastName, balance);
    Account accountModel = Account.buildCurrencyAccount(baseCurrency, balance);
    Map<String, Account> accounts = new HashMap<>();
    accounts.put(accountModel.getCurrency(), accountModel);
    return Client.builder()
        .identifier(ClientIdentifier.generateIdentifier())
        .firstName(firstName)
        .lastName(lastName)
        .accounts(accounts)
        .build();
  }

  public static Client buildExistingClient(ClientIdentifier identifier, String firstName,
      String lastName, Map<String, Account> accounts) {
    return Client.builder()
        .identifier(identifier)
        .firstName(firstName)
        .lastName(lastName)
        .accounts(accounts)
        .build();
  }

  public boolean canExchange(String currency, BigDecimal amount) {
    return accounts.containsKey(currency) && accounts.get(currency).hasEnoughFunds(amount);
  }

  public void performExchange(CurrencyPair pair, BigDecimal amount,
      BigDecimal calculatedExchangedAmount) {
    accounts.get(pair.getFromCurrency()).subtractAmount(amount);
    Account toAccount;
    if (!accounts.containsKey(pair.getToCurrency())) {
      toAccount = createAccount(pair);
    } else {
      toAccount = accounts.get(pair.getToCurrency());
    }
    toAccount.addAmount(calculatedExchangedAmount);
  }

  private Account createAccount(CurrencyPair pair) {
    accounts.put(pair.getToCurrency(),
        Account.buildCurrencyAccount(pair.getToCurrency(), BigDecimal.ZERO));
    return accounts.get(pair.getToCurrency());
  }

  private static void validateClientValues(String firstName, String lastName, BigDecimal balance) {
    if (StringUtils.isBlank(firstName)) {
      throw new ExchangerGeneralException("Invalid first name value");
    }

    if (StringUtils.isBlank(lastName)) {
      throw new ExchangerGeneralException("Invalid last name value");
    }

    if (balance == null || balance.compareTo(BigDecimal.ZERO) < 0) {
      throw new ExchangerGeneralException("Invalid balance value");
    }
  }
}
