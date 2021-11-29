package com.commerce.exchanger.infrastructure.repository.client;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AccountId implements Serializable {

  UUID client;
  String currency;
}
