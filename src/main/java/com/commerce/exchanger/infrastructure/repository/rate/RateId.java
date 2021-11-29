package com.commerce.exchanger.infrastructure.repository.rate;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RateId implements Serializable {

  String fromCurrency;
  String toCurrency;
}
