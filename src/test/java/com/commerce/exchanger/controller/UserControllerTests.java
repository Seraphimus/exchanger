package com.commerce.exchanger.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.commerce.exchanger.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  void shouldReturnOkWhenCreatingNewAccount() throws Exception {
    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n"
            + "  \"firstName\": \"asd\",\n"
            + "  \"lastName\": \"zxc\",\n"
            + "  \"initialBalance\": 0\n"
            + "}")
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk());
  }

  @Test
  void shouldReturnErrorWithNoFirstNameDataWhenCreatingNewAccount() throws Exception {
    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n"
            + "  \"lastName\": \"zxc\",\n"
            + "  \"initialBalance\": 0\n"
            + "}")
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnErrorWithNoLastNameDataWhenCreatingNewAccount() throws Exception {
    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n"
            + "  \"firstName\": \"asd\",\n"
            + "  \"initialBalance\": 0\n"
            + "}")
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnErrorWithNoInitialBalanceNameDataWhenCreatingNewAccount() throws Exception {
    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n"
            + "  \"firstName\": \"asd\",\n"
            + "  \"lastName\": \"zxc\",\n"
            + "}")
        .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isBadRequest());
  }
}