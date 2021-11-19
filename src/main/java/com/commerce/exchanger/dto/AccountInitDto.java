package com.commerce.exchanger.dto;

import java.math.BigDecimal;
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
  BigDecimal initialBalance;
}
