package com.commerce.exchanger;

import com.commerce.exchanger.api.ClientEndpoint;
import com.commerce.exchanger.api.ExchangeEndpoint;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExchangerApplicationTests {

  @Autowired
  private ExchangeEndpoint exchangeEndpoint;

  @Autowired
  ClientEndpoint clientEndpoint;

  @Test
  void contextLoads() {
    Assertions.assertThat(exchangeEndpoint).isNotNull();
    Assertions.assertThat(clientEndpoint).isNotNull();
  }

}
