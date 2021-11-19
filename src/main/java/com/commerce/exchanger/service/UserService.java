package com.commerce.exchanger.service;

import com.commerce.exchanger.dto.AccountInitDto;
import com.commerce.exchanger.dto.UserDto;
import com.commerce.exchanger.model.User;
import com.commerce.exchanger.repository.UserRepository;
import java.util.List;
import java.util.UUID;
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

  public void createAccount(AccountInitDto accountInitDto) {
    User user = new User();
    user.setUuid(UUID.randomUUID());
    user.setFirstName(accountInitDto.getFirstName());
    user.setLastName(accountInitDto.getLastName());
    user.setAccounts(List.of(
        accountService.createInitialAccount(accountInitDto.getInitialBalance()))
    );
    userRepository.save(user);
  }

  public UserDto getAccount(UUID accountUuid) {
    return userRepository.findById(accountUuid)
        .map(userDtoMapper::map)
        .orElse(null);
  }
}
