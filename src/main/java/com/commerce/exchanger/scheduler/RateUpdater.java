package com.commerce.exchanger.scheduler;

import com.commerce.exchanger.model.Rate;
import com.commerce.exchanger.repository.RateRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

  private final RateRepository rateRepository;

  public RateUpdater(RateRepository rateRepository) {
    this.rateRepository = rateRepository;
  }

  @Scheduled(cron = "0 * * * * *")
  public void updateRates() {
    List<String> currencyCodes = resolveCurrenciesToUpdate();
    for (String currencyCode : currencyCodes) {
      RateResponseDto response = fetchDataFromNbpApi(currencyCode);
      Rate rate = new Rate();
      rate.setCurrencyCode(currencyCode);
      if (response == null) {
        rate.setLastUpdateSucceeded(false);
      } else {
        rate.setBid(response.getApiRates().get(0).getBid());
        rate.setAsk(response.getApiRates().get(0).getAsk());
        rate.setLastUpdateSucceeded(true);
        rate.setLastUpdate(LocalDateTime.now());
      }
      rateRepository.save(rate);
    }
  }

  private List<String> resolveCurrenciesToUpdate() {
    List<Rate> rates = rateRepository.findAll();
    if (rates.isEmpty()) {
      return currencies;
    }

    return rates.stream()
        .filter(rate -> !rate.isLastUpdateSucceeded() || !rate.getLastUpdate().toLocalDate()
            .equals(LocalDate.now()))
        .map(Rate::getCurrencyCode)
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

}
