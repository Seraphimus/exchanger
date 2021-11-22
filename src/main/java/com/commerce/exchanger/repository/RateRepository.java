package com.commerce.exchanger.repository;

import com.commerce.exchanger.model.Rate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rate, String> {

  Optional<Rate> findRateByCurrencyCodeAndLastUpdateSucceededIsTrue(String currencyCode);

}
