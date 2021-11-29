package com.commerce.exchanger.infrastructure.repository.rate;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExchangeRateJpaRepository extends JpaRepository<ExchangeRate, String> {

  Optional<ExchangeRate> findByFromCurrencyAndToCurrencyAndLastUpdateSucceededIsTrue(
      String fromCurrency,
      String toCurrency);
}
