package com.commerce.exchanger.service;

import com.commerce.exchanger.dto.ExchangeDto;
import com.commerce.exchanger.exception.ExchangeErrorException;
import com.commerce.exchanger.model.Calculation;
import com.commerce.exchanger.model.ExchangeOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService {

  @Value("${exchanger.base.currency:PLN}")
  String baseCurrency;

  private final UserService userService;
  private final AccountService accountService;
  private final RateCalculatorService rateCalculatorService;

  public ExchangeService(UserService userService,
      AccountService accountService,
      RateCalculatorService rateCalculatorService) {
    this.userService = userService;
    this.accountService = accountService;
    this.rateCalculatorService = rateCalculatorService;
  }

  public void exchange(ExchangeDto exchangeDto) {
    if (!userService.existsById(exchangeDto.getUserUuid())) {
      throw new ExchangeErrorException("User not found");
    }

    if (!accountService.hasSufficientAmount(exchangeDto.getUserUuid(),
        exchangeDto.getFromCurrency(), exchangeDto.getAmount())) {
      throw new ExchangeErrorException("Not enough funds");
    }

    Calculation calculation = assembleCalculation(exchangeDto);
    ExchangeOperation exchangeOperation = ExchangeOperation.builder()
        .user(userService.getUser(exchangeDto.getUserUuid()))
        .amountToExchange(exchangeDto.getAmount())
        .fromCurrency(exchangeDto.getFromCurrency())
        .toCurrency(exchangeDto.getToCurrency())
        .amountExchanged(rateCalculatorService.calculateExchange(calculation))
        .build();
    userService.saveExchange(exchangeOperation);
  }

  private Calculation assembleCalculation(ExchangeDto exchangeDto) {
    String foreignCurrency =
        baseCurrency.equalsIgnoreCase(exchangeDto.getFromCurrency()) ? exchangeDto.getToCurrency()
            : exchangeDto.getFromCurrency();
    boolean isBuyingForeignCurrency = baseCurrency.equalsIgnoreCase(exchangeDto.getFromCurrency());

    return Calculation.builder()
        .amount(exchangeDto.getAmount())
        .foreignCurrencyCode(foreignCurrency)
        .isBuyingForeignCurrency(isBuyingForeignCurrency)
        .build();
  }
}
