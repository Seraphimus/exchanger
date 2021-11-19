package com.commerce.exchanger.service;

import com.commerce.exchanger.dto.UserDto;
import com.commerce.exchanger.model.Account;
import com.commerce.exchanger.model.User;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserDtoMapper {

  public UserDto map(User user) {
    return UserDto.builder()
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .accounts(mapAccounts(user.getAccounts()))
        .build();
  }

  private Map<String, BigDecimal> mapAccounts(List<Account> accounts) {
    return accounts.stream()
        .collect(Collectors.toMap(Account::getCurrency, Account::getBalance));
  }
}
