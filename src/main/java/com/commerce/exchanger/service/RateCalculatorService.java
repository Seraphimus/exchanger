package com.commerce.exchanger.service;

import com.commerce.exchanger.exception.ExchangeErrorException;
import com.commerce.exchanger.model.Calculation;
import com.commerce.exchanger.model.Rate;
import com.commerce.exchanger.repository.RateRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class RateCalculatorService {

  private final RateRepository rateRepository;

  public RateCalculatorService(RateRepository rateRepository) {
    this.rateRepository = rateRepository;
  }

  public BigDecimal calculateExchange(Calculation calculation) {
    Rate rate = rateRepository.findRateByCurrencyCodeAndLastUpdateSucceededIsTrue(
            calculation.getForeignCurrencyCode())
        .orElseThrow(() -> new ExchangeErrorException("Failed fetching current rate"));

    return calculation.isBuyingForeignCurrency() ?
        calculateBaseToForeign(calculation, rate.getAsk()) :
        calculateForeignToBase(calculation, rate.getBid());
  }

  private BigDecimal calculateBaseToForeign(Calculation calculation, BigDecimal rate) {
    return calculation.getAmount().setScale(4, RoundingMode.HALF_UP)
        .divide(rate, RoundingMode.HALF_UP)
        .setScale(4, RoundingMode.HALF_UP);
  }

  private BigDecimal calculateForeignToBase(Calculation calculation, BigDecimal rate) {
    return calculation.getAmount().setScale(4, RoundingMode.HALF_UP).multiply(rate)
        .setScale(2, RoundingMode.HALF_UP);
  }
}
