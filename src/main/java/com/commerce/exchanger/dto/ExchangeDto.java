package com.commerce.exchanger.dto;

import java.math.BigDecimal;
import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExchangeDto {

  @NotEmpty
  UUID userUuid;
  @NotEmpty
  @Min(value = 0L, message = "The value must be positive")
  BigDecimal amount;
  @NotEmpty
  String fromCurrency;
  @NotEmpty
  String toCurrency;
}
