package com.commerce.exchanger.controller;

import com.commerce.exchanger.dto.UserDto;
import com.commerce.exchanger.dto.AccountInitDto;
import com.commerce.exchanger.service.UserService;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{userUuid}")
  public UserDto getUser(@PathVariable UUID userUuid) {
    return userService.getAccount(userUuid);
  }

  @PostMapping
  public void createUser(
      @Validated @RequestBody AccountInitDto accountInitDto) {
    userService.createAccount(accountInitDto);
  }
}
