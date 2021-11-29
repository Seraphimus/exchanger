package com.commerce.exchanger.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Client implements Serializable {

  ClientIdentifier identifier;
  String firstName;
  String lastName;
  Map<String, Account> accounts;

  public static Client buildClient(String firstName, String lastName, BigDecimal balance) {
    Account accountModel = Account.createBaseCurrencyAccount(balance);
    return Client.builder()
        .identifier(ClientIdentifier.generateIdentifier())
        .firstName(firstName)
        .lastName(lastName)
        .accounts(Map.of(accountModel.getCurrency(), accountModel))
        .build();
  }

  public boolean cannotExchange(String currency, BigDecimal amount) {
    return !accounts.containsKey(currency) || !accounts.get(currency).hasEnoughFunds(amount);
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
        Account.createForeignCurrencyAccount(pair.getToCurrency(), BigDecimal.ZERO));
    return accounts.get(pair.getToCurrency());
  }
}
