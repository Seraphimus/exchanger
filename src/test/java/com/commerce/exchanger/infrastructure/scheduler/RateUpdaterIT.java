package com.commerce.exchanger.infrastructure.scheduler;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;


class RateUpdaterIT {

  @Test
  void shouldReturnData() {
    String rateApiBaseUrl = "https://api.nbp.pl/api/exchangerates/rates/c";
    RateResponseDto result = WebClient.create(rateApiBaseUrl)
        .get()
        .uri(uriBuilder -> uriBuilder.path("/usd/today/").build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(RateResponseDto.class)
        .block();

    Assertions.assertThat(result).isNotNull();
  }
}
