package com.commerce.exchanger.repository;

import com.commerce.exchanger.model.Account;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, UUID> {

  Optional<Account> findByCurrencyAndUser(String currencyCode, UUID userUuid);
}
