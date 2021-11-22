package com.commerce.exchanger.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExchangeOperation {
  User user;
  BigDecimal amountToExchange;
  String fromCurrency;
  String toCurrency;
  BigDecimal amountExchanged;
}
