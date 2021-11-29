package com.commerce.exchanger.api.dto;

import java.math.BigDecimal;
import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExchangeDto {

  @NotNull
  UUID clientUuid;
  @NotNull
  @Min(value = 0L, message = "The value must be positive")
  BigDecimal amount;
  @NotEmpty
  String fromCurrency;
  @NotEmpty
  String toCurrency;
}
