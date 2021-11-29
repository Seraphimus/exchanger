package com.commerce.exchanger.infrastructure.repository.client;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientJpaRepository extends JpaRepository<ClientDao, UUID> {

}
