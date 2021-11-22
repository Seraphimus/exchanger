package com.commerce.exchanger.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Rate {

  @Id
  String currencyCode;
  @Column(scale = 4, precision = 19)
  BigDecimal bid;
  @Column(scale = 4, precision = 19)
  BigDecimal ask;
  boolean lastUpdateSucceeded;
  LocalDateTime lastUpdate;
}
