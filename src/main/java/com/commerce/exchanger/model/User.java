package com.commerce.exchanger.model;

import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

  @Id
  UUID uuid;
  String firstName;
  String lastName;

  @OneToMany
  List<Account> accounts;
}
