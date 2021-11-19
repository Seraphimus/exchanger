package com.commerce.exchanger;

import com.commerce.exchanger.controller.ExchangeController;
import com.commerce.exchanger.controller.UserController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExchangerApplicationTests {

  @Autowired
  private ExchangeController exchangeController;

  @Autowired
  UserController userController;

  @Test
  void contextLoads() {
    Assertions.assertThat(exchangeController).isNotNull();
    Assertions.assertThat(userController).isNotNull();
  }

}
