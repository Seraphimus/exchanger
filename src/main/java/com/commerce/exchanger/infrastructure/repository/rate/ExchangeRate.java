package com.commerce.exchanger.infrastructure.repository.rate;

import com.commerce.exchanger.domain.Rate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import lombok.Getter;

@Getter
@Entity(name = "ExchangeRate")
@IdClass(RateId.class)
public class ExchangeRate implements Serializable {

  @Id
  String fromCurrency;
  @Id
  String toCurrency;
  @Column(scale = 4, precision = 19)
  BigDecimal rate;
  boolean lastUpdateSucceeded;
  LocalDateTime lastUpdate;

  protected ExchangeRate() {

  }

  private ExchangeRate(String fromCurrency, String toCurrency, boolean lastUpdateSucceeded) {
    this.fromCurrency = fromCurrency;
    this.toCurrency = toCurrency;
    this.lastUpdateSucceeded = lastUpdateSucceeded;
  }

  private ExchangeRate(String fromCurrency, String toCurrency, BigDecimal rate) {
    this.fromCurrency = fromCurrency;
    this.toCurrency = toCurrency;
    this.rate = rate;
    this.lastUpdateSucceeded = true;
    this.lastUpdate = LocalDateTime.now();
  }

  public static ExchangeRate buildFailedExchangeRate(String fromCurrency, String toCurrency) {
    return new ExchangeRate(fromCurrency, toCurrency, false);
  }

  public static ExchangeRate buildSuccessfulUpdateRate(String fromCurrency, String toCurrency,
      BigDecimal rate) {
    return new ExchangeRate(fromCurrency, toCurrency, rate);
  }

  public static Rate mapToDomain(ExchangeRate rate) {
    return Rate.builder()
        .fromCurrency(rate.getFromCurrency())
        .toCurrency(rate.getToCurrency())
        .value(rate.getRate())
        .build();
  }
}