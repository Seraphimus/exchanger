package com.commerce.exchanger.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Exchange {

  @Id
  UUID uuid;
  UUID accountUuid;
  BigDecimal amount;
  String currencyFrom;
  String currencyTo;
  LocalDateTime operationDate;
}
