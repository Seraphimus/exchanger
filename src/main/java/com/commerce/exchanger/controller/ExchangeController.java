package com.commerce.exchanger.controller;

import com.commerce.exchanger.dto.ExchangeDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchanges")
public class ExchangeController {

  @PostMapping
  public void exchangeAmount(@RequestBody ExchangeDto exchangeDto) {

  }
}
