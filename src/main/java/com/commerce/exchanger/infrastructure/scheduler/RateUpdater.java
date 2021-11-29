package com.commerce.exchanger.infrastructure.scheduler;

import com.commerce.exchanger.infrastructure.repository.rate.ExchangeRate;
import com.commerce.exchanger.infrastructure.repository.rate.ExchangeRateJpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

@Service
public class RateUpdater {

  @Value("${exchanger.rate.api.url}")
  String rateApiBaseUrl;

  @Value("${exchanger.imported.currencies}")
  List<String> currencies;

  @Value("${exchanger.base.currency:PLN}")
  private String baseCurrency;

  private final ExchangeRateJpaRepository exchangeRateJpaRepository;

  public RateUpdater(ExchangeRateJpaRepository exchangeRateJpaRepository) {
    this.exchangeRateJpaRepository = exchangeRateJpaRepository;
  }

  @Scheduled(cron = "0 * * * * *")
  public void updateRates() {
    List<String> currencyCodes = resolveCurrenciesToUpdate();
    for (String currencyCode : currencyCodes) {
      RateResponseDto response = fetchDataFromNbpApi(currencyCode);
      exchangeRateJpaRepository.saveAll(buildRateDaoPair(currencyCode, response));
    }
  }

  private List<String> resolveCurrenciesToUpdate() {
    List<ExchangeRate> rates = exchangeRateJpaRepository.findAll();
    if (rates.isEmpty()) {
      return currencies;
    }

    return rates.stream()
        .filter(rate -> !rate.isLastUpdateSucceeded() || !rate.getLastUpdate().toLocalDate()
            .equals(LocalDate.now()))
        .map(ExchangeRate::getFromCurrency)
        .filter(s -> !s.equalsIgnoreCase(baseCurrency))
        .collect(Collectors.toList());
  }

  private RateResponseDto fetchDataFromNbpApi(String currency) {
    return WebClient.create(rateApiBaseUrl)
        .get()
        .uri(uriBuilder -> uriBuilder.path("/" + currency + "/today/").build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(RateResponseDto.class)
        .onErrorResume(WebClientException.class, e -> Mono.empty())
        .block();
  }

  private List<ExchangeRate> buildRateDaoPair(String currencyCode,
      RateResponseDto rateResponseDto) {
    ExchangeRate rateBaseToForeign;
    ExchangeRate rateForeignToBase;

    if (rateResponseDto == null) {
      rateBaseToForeign = ExchangeRate.buildFailedExchangeRate(baseCurrency, currencyCode);
      rateForeignToBase = ExchangeRate.buildFailedExchangeRate(currencyCode, baseCurrency);
    } else {
      rateBaseToForeign = ExchangeRate.buildSuccessfulUpdateRate(baseCurrency, currencyCode,
          rateResponseDto.getAsk());
      rateForeignToBase = ExchangeRate.buildSuccessfulUpdateRate(currencyCode, baseCurrency,
          rateResponseDto.getBid());
    }

    return List.of(rateBaseToForeign, rateForeignToBase);
  }
}
