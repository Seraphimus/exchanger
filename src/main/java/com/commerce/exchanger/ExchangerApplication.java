package com.commerce.exchanger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExchangerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ExchangerApplication.class, args);
  }

}
