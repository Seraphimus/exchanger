package com.commerce.exchanger.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.commerce.exchanger.dto.AccountInitDto;
import com.commerce.exchanger.dto.UserDto;
import com.commerce.exchanger.model.Account;
import com.commerce.exchanger.model.User;
import com.commerce.exchanger.repository.UserRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { UserDtoMapper.class, UserService.class })
class UserServiceTests {

  @InjectMocks
  @Autowired
  UserService userService;

  @Autowired
  UserDtoMapper mapper;

  @MockBean
  AccountService accountService;

  @MockBean
  UserRepository userRepository;

  @Test
  void shouldCreateAccount() {
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    Account testAccount = createTestAccount(BigDecimal.valueOf(10L));
    when(accountService.createInitialAccount(any())).thenReturn(testAccount);
    when(userRepository.save(userCaptor.capture())).thenReturn(new User());

    AccountInitDto dto = AccountInitDto.builder()
        .firstName("Test")
        .lastName("Testing")
        .initialBalance(BigDecimal.valueOf(10L))
        .build();

    userService.createAccount(dto);

    User capturedUser = userCaptor.getValue();
    Assertions.assertThat(capturedUser.getFirstName()).isEqualTo(dto.getFirstName());
    Assertions.assertThat(capturedUser.getLastName()).isEqualTo(dto.getLastName());
    Assertions.assertThat(capturedUser.getAccounts().size()).isEqualTo(1);
    Assertions.assertThat(capturedUser.getAccounts().get(0)).isEqualTo(testAccount);
  }

  @Test
  void shouldReturnAccountWhenGettingExistingAccount() {
    UUID uuid = UUID.randomUUID();
    when(userRepository.findById(uuid)).thenReturn(Optional.of(createTestUser(uuid)));

    UserDto userDto = userService.getAccount(uuid);

    Assertions.assertThat(userDto).isNotNull();
    Assertions.assertThat(userDto.getFirstName()).isEqualTo("Test1");
    Assertions.assertThat(userDto.getLastName()).isEqualTo("Testing1");
  }

  @Test
  void shouldReturnNullWhenGettingNonExistingAccount() {
    UUID uuid = UUID.randomUUID();
    when(userRepository.findById(uuid)).thenReturn(null);

    UserDto userDto = userService.getAccount(UUID.randomUUID());

    Assertions.assertThat(userDto).isNull();
  }

  private Account createTestAccount(BigDecimal balance) {
    Account account = new Account();
    account.setUuid(UUID.randomUUID());
    account.setCurrency("PLN");
    account.setBalance(balance);
    return account;
  }

  private User createTestUser(UUID uuid) {
    User user = new User();
    user.setUuid(uuid);
    user.setFirstName("Test1");
    user.setLastName("Testing1");
    user.setAccounts(List.of(createTestAccount(BigDecimal.ZERO)));
    return user;
  }
}
