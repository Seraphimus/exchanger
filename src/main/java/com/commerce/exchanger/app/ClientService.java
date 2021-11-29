package com.commerce.exchanger.app;

import com.commerce.exchanger.api.dto.ClientDto;
import com.commerce.exchanger.app.exception.ExchangeErrorException;
import com.commerce.exchanger.domain.Client;
import java.math.BigDecimal;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  private final ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public void buildClient(String firstName, String lastName, BigDecimal balance) {
    Client client = Client.buildClient(firstName, lastName, balance);
    clientRepository.save(client);
  }

  public ClientDto getClient(UUID clientUuid) {
    Client client = clientRepository.findById(clientUuid);
    if(client == null) {
      throw new ExchangeErrorException("Client unknown");
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
