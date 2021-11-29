package com.commerce.exchanger.domain;

import java.io.Serializable;
import java.util.UUID;
import lombok.Value;

@Value
public class ClientIdentifier implements Serializable {

  UUID identifier;

  private ClientIdentifier(UUID uuid) {
    this.identifier = uuid;
  }

  public static ClientIdentifier generateIdentifier() {
    return new ClientIdentifier(UUID.randomUUID());
  }

  public static ClientIdentifier mapValue(UUID uuid) {
    return new ClientIdentifier(uuid);
  }
}
