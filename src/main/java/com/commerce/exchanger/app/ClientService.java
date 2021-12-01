package com.commerce.exchanger.app;

import com.commerce.exchanger.api.dto.ClientDto;
import com.commerce.exchanger.app.exception.ExchangerGeneralException;
import com.commerce.exchanger.domain.Client;
import java.math.BigDecimal;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  @Value("${exchanger.base.currency}")
  public String baseCurrency;

  private final ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public void createClient(String firstName, String lastName, BigDecimal balance) {
    Client client = Client.buildNewClient(firstName, lastName, balance, baseCurrency);
    clientRepository.save(client);
  }

  public ClientDto getClient(UUID clientUuid) {
    Client client = clientRepository.findById(clientUuid);
    if(client == null) {
      throw new ExchangerGeneralException("Client unknown");
    }
    var acc = client.getAccounts().entrySet().stream().collect(
        Collectors.toMap(Entry::getKey, entry -> entry.getValue().getBalance()));

    return ClientDto.builder()
        .firstName(client.getFirstName())
        .lastName(client.getLastName())
        .accounts(acc)
        .build();
  }
}
