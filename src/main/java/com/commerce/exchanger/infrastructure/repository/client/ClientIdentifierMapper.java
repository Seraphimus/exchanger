package com.commerce.exchanger.infrastructure.repository.client;

import com.commerce.exchanger.domain.ClientIdentifier;
import java.util.UUID;

public class ClientIdentifierMapper {

  private ClientIdentifierMapper() {
  }

  public static UUID mapFromDomain(ClientIdentifier clientIdentifier) {
    return clientIdentifier.getIdentifier();
  }

  public static ClientIdentifier mapToDomain(UUID uuid) {
    return ClientIdentifier.mapValue(uuid);
  }
}
