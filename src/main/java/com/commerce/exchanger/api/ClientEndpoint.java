package com.commerce.exchanger.api;


import com.commerce.exchanger.api.dto.AccountCreateDto;
import com.commerce.exchanger.api.dto.ClientDto;
import com.commerce.exchanger.app.ClientService;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class ClientEndpoint {

  private final ClientService clientService;

  public ClientEndpoint(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping("/{clientUuid}")
  public ClientDto getUser(@PathVariable UUID clientUuid) {
    return clientService.getClient(clientUuid);
  }

  @PostMapping
  public void createUser(@Validated @RequestBody AccountCreateDto accountCreateDto) {
    clientService.buildClient(
        accountCreateDto.getFirstName(),
        accountCreateDto.getLastName(),
        accountCreateDto.getInitialBalance()
    );
  }
}
