package com.commerce.exchanger.infrastructure.repository.client;

import com.commerce.exchanger.domain.Account;
import com.commerce.exchanger.domain.Client;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import lombok.Getter;

@Getter
@Entity(name = "Account")
@IdClass(AccountId.class)
public class AccountDao implements Serializable {

  @Id
  UUID client;
  @Id
  String currency;
  BigDecimal balance;

  protected AccountDao() {

  }

  private AccountDao(String currency, BigDecimal balance, UUID client) {
    this.client = client;
    this.currency = currency;
    this.balance = balance;
  }

  public static AccountDao mapFromDomain(Account account, Client client) {
    return new AccountDao(account.getCurrency(), account.getBalance(),
        ClientIdentifierMapper.mapFromDomain(client.getIdentifier()));
  }

  public static List<AccountDao> mapFromDomain(Collection<Account> accounts, Client client) {
    return accounts.stream()
        .map(account -> mapFromDomain(account, client))
        .collect(Collectors.toList());
  }

  public static Account mapToDomain(AccountDao accountDao) {
    return Account.readAccountData(accountDao.currency, accountDao.balance);
  }

  public static List<Account> mapToDomain(Collection<AccountDao> accountDaos) {
    return accountDaos.stream()
        .map(AccountDao::mapToDomain)
        .collect(Collectors.toList());
  }
}
