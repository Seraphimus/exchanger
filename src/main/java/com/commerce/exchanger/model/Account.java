package com.commerce.exchanger.model;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(indexes = {@Index(name = "userCurrencyIndex", columnList = "uuid, currency")})
@Getter
@Setter
public class Account {

  @Id
  UUID uuid;
  String currency;
  BigDecimal balance;

}
