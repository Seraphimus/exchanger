package com.commerce.exchanger.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class ClientExchange {

  Client client;
  Exchange exchange;
  Rate exchangeRate;

  public static ClientExchange buildExchangeAggregate(Client client, Exchange exchange,
      Rate rate) {
    return ClientExchange.builder()
        .client(client)
        .exchange(exchange)
        .exchangeRate(rate)
        .build();
  }
}
