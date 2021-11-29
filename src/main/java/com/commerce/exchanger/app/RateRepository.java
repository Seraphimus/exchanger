package com.commerce.exchanger.app;

import com.commerce.exchanger.domain.Rate;

public interface RateRepository {

  Rate get(String fromCurrency, String toCurrency);
}
