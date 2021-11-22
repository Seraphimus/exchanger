package com.commerce.exchanger.controller;

import com.commerce.exchanger.dto.ExchangeDto;
import com.commerce.exchanger.service.ExchangeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchanges")
public class ExchangeController {

  private final ExchangeService exchangeService;

  public ExchangeController(ExchangeService exchangeService) {
    this.exchangeService = exchangeService;
  }

  @PostMapping
  public void exchangeAmount(@Validated @RequestBody ExchangeDto exchangeDto) {
    exchangeService.exchange(exchangeDto);
  }
}
