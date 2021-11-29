package com.commerce.exchanger.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Rate {
  String fromCurrency;
  String toCurrency;
  @Column(scale = 4, precision = 19)
  BigDecimal value;
}
