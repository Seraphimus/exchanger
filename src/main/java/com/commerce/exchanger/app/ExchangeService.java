package com.commerce.exchanger.app;

import com.commerce.exchanger.api.dto.ExchangeDto;
import com.commerce.exchanger.domain.Client;
import com.commerce.exchanger.domain.CurrencyPair;
import com.commerce.exchanger.domain.Exchange;
import com.commerce.exchanger.domain.Rate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService {

  private final ClientRepository clientRepository;
  private final RateRepository rateRepository;

  @Value("${exchanger.base.currency}")
  public String baseCurrency;

  public ExchangeService(ClientRepository clientRepository,
      RateRepository rateRepository) {
    this.clientRepository = clientRepository;
    this.rateRepository = rateRepository;
  }

  public void performExchange(ExchangeDto exchangeDto) {
    Client client = clientRepository.findById(exchangeDto.getClientUuid());
    Rate rate = rateRepository.get(exchangeDto.getFromCurrency(), exchangeDto.getToCurrency());
    CurrencyPair pair = CurrencyPair.of(exchangeDto.getFromCurrency(), exchangeDto.getToCurrency());
    Exchange exchange = Exchange.buildExchange(pair, baseCurrency, exchangeDto.getAmount());

    client = exchange.performExchangeForClientWithRate(client, rate);

    clientRepository.save(client);
  }
}
