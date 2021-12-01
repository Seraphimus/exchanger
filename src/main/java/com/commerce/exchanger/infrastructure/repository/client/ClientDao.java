package com.commerce.exchanger.infrastructure.repository.client;

import static javax.persistence.CascadeType.ALL;

import com.commerce.exchanger.domain.Account;
import com.commerce.exchanger.domain.Client;
import com.commerce.exchanger.domain.ClientIdentifier;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "Client")
public class ClientDao implements Serializable {

  @Id
  UUID uuid;
  String firstName;
  String lastName;

  @OneToMany(cascade = ALL, mappedBy = "client")
  List<AccountDao> accounts;

  protected ClientDao() {

  }

  private ClientDao(UUID uuid, String firstName, String lastName, List<AccountDao> accounts) {
    this.uuid = uuid;
    this.firstName = firstName;
    this.lastName = lastName;
    this.accounts = accounts;
  }

  public static ClientDao mapFromDomain(Client client) {
    return new ClientDao(
        ClientIdentifierMapper.mapFromDomain(client.getIdentifier()),
        client.getFirstName(),
        client.getLastName(),
        AccountDao.mapFromDomain(client.getAccounts().values(), client));
  }

  public static Client mapToDomain(ClientDao clientDao) {
    ClientIdentifier identifier = ClientIdentifierMapper.mapToDomain(clientDao.uuid);
    Map<String, Account> accountMap = clientDao.accounts.stream()
        .map(AccountDao::mapToDomain)
        .collect(Collectors.toMap(Account::getCurrency, account -> account));

    return Client.buildExistingClient(identifier, clientDao.firstName, clientDao.lastName,
        accountMap);
  }
}
