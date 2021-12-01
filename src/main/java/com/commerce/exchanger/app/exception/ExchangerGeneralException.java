package com.commerce.exchanger.app.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@NoArgsConstructor
public class ExchangerGeneralException extends RuntimeException {

  public ExchangerGeneralException(String message) {
    super(message);
  }
}
