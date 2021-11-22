package com.commerce.exchanger.dto;

import com.commerce.exchanger.validation.ExchangeDtoAnnotation;
import java.math.BigDecimal;
import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@ExchangeDtoAnnotation
public class ExchangeDto {

  @NotNull
  UUID userUuid;
  @NotNull
  @Min(value = 0L, message = "The value must be positive")
  BigDecimal amount;
  @NotEmpty
  String fromCurrency;
  @NotEmpty
  String toCurrency;
}
