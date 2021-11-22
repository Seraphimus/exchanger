package com.commerce.exchanger.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountInitDto {
  @NotBlank
  String firstName;
  @NotBlank
  String lastName;
  @NotNull
  @Min(value = 0L, message = "The value must be positive")
  BigDecimal initialBalance;
}
