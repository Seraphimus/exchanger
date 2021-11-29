package com.commerce.exchanger.infrastructure.repository.client;

import com.commerce.exchanger.app.ClientRepository;
import com.commerce.exchanger.domain.Client;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

  private final ClientJpaRepository clientJpaRepository;

  public ClientRepositoryImpl(
      ClientJpaRepository clientJpaRepository) {
    this.clientJpaRepository = clientJpaRepository;
  }

  public void save(Client client) {
    clientJpaRepository.save(ClientDao.mapFromDomain(client));
  }

  public Client findById(UUID clientUuid) {
    return clientJpaRepository.findById(clientUuid).map(ClientDao::mapToDomain)
        .orElse(null);
  }
}
