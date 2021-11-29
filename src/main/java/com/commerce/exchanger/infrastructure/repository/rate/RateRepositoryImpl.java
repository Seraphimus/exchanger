package com.commerce.exchanger.infrastructure.repository.rate;

import com.commerce.exchanger.app.RateRepository;
import com.commerce.exchanger.app.exception.ExchangeErrorException;
import com.commerce.exchanger.domain.Rate;
import org.springframework.stereotype.Repository;

@Repository
public class RateRepositoryImpl implements RateRepository {

  private final ExchangeRateJpaRepository exchangeRateJpaRepository;

  public RateRepositoryImpl(ExchangeRateJpaRepository exchangeRateJpaRepository) {
    this.exchangeRateJpaRepository = exchangeRateJpaRepository;
  }

  public Rate get(String fromCurrency, String toCurrency) {
    return exchangeRateJpaRepository.findByFromCurrencyAndToCurrencyAndLastUpdateSucceededIsTrue(
            fromCurrency, toCurrency)
        .map(ExchangeRate::mapToDomain)
        .orElseThrow(() -> new ExchangeErrorException("No valid exchange rates found"));
  }
}
