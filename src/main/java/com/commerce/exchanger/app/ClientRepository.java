package com.commerce.exchanger.app;

import com.commerce.exchanger.domain.Client;
import java.util.UUID;

public interface ClientRepository {

  void save(Client client);

  Client findById(UUID clientUuid);
}
