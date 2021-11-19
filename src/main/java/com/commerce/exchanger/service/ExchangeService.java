package com.commerce.exchanger.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService {

  public BigDecimal exchange(BigDecimal amount, String currencyCode) {
    return BigDecimal.ZERO; //TODO implement
  }
}
