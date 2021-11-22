package com.commerce.exchanger.service;

import com.commerce.exchanger.dto.AccountInitDto;
import com.commerce.exchanger.dto.UserDto;
import com.commerce.exchanger.model.ExchangeOperation;
import com.commerce.exchanger.model.User;
import com.commerce.exchanger.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserDtoMapper userDtoMapper;
  private final AccountService accountService;

  public UserService(UserRepository userRepository,
      UserDtoMapper userDtoMapper,
      AccountService accountService) {
    this.userRepository = userRepository;
    this.userDtoMapper = userDtoMapper;
    this.accountService = accountService;
  }

  @Transactional
  public void createAccount(AccountInitDto accountInitDto) {
    User user = new User();
    user.setUuid(UUID.randomUUID());
    user.setFirstName(accountInitDto.getFirstName());
    user.setLastName(accountInitDto.getLastName());
    user.setAccounts(List.of(
        accountService.createInitialAccount(accountInitDto.getInitialBalance(), user.getUuid()))
    );
    userRepository.save(user);
  }

  public User getUser(UUID accountUuid) {
    return userRepository.findById(accountUuid)
        .orElse(null);
  }

  public UserDto getUserDto(UUID accountUuid) {
    return userRepository.findById(accountUuid)
        .map(userDtoMapper::map)
        .orElse(null);
  }

  public boolean existsById(UUID userUuid) {
    return userRepository.existsById(userUuid);
  }

  @Transactional
  public void saveExchange(ExchangeOperation operation) {
    accountService.updateUserAccounts(operation);
    userRepository.save(operation.getUser());
  }

}
