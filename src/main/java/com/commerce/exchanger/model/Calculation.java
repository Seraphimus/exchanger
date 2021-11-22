package com.commerce.exchanger.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Calculation {

  BigDecimal amount;
  String foreignCurrencyCode;
  boolean isBuyingForeignCurrency;
}
