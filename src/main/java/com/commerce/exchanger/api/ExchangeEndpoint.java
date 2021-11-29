package com.commerce.exchanger.api;


import com.commerce.exchanger.api.dto.ExchangeDto;
import com.commerce.exchanger.app.ExchangeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchanges")
public class ExchangeEndpoint {

  private final ExchangeService exchangeService;

  public ExchangeEndpoint(ExchangeService exchangeService) {
    this.exchangeService = exchangeService;
  }

  @PostMapping
  public void exchangeAmount(@Validated @RequestBody ExchangeDto exchangeDto) {
    exchangeService.performExchange(exchangeDto);
  }
}