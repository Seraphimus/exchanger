package com.commerce.exchanger.dto;

import java.math.BigDecimal;
import java.util.Map;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {

  @NotEmpty
  String firstName;
  @NotEmpty
  String lastName;
  @NotEmpty
  Map<String, BigDecimal> accounts;
}
