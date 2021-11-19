package com.commerce.exchanger.repository;

import com.commerce.exchanger.model.Exchange;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<Exchange, UUID> {

}
