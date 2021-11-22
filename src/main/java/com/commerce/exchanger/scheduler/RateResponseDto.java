package com.commerce.exchanger.scheduler;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
class RateResponseDto implements Serializable {
  String table;
  String currency;
  String code;
  @JsonAlias("rates")
  List<ApiRate> apiRates;
}
